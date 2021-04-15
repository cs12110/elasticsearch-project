package com.search.pkgs.service;

import com.alibaba.fastjson.JSON;
import com.search.pkgs.common.conig.ElasticSearchConfig;
import com.search.pkgs.common.exceptions.BizException;
import com.search.pkgs.model.entity.BookEntity;
import com.search.pkgs.model.request.BookQueryReq;
import com.search.pkgs.model.response.PageResp;
import com.search.pkgs.util.ListUtil;
import com.search.pkgs.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:28
 */
@Slf4j
@Service
public class BookDocumentService {
    @Resource
    private ElasticSearchConfig elasticSearchConfig;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    public BookEntity save(BookEntity info) {
        try {
            IndexRequest indexRequest = new IndexRequest(elasticSearchConfig.getBookIndices());
            indexRequest.id(info.getId());
            indexRequest.timeout(TimeValue.timeValueSeconds(3));
            indexRequest.source(JSON.toJSONString(info), XContentType.JSON);

            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            log.info("Function[save]request:{},response:{}", indexRequest, index);
        } catch (Exception e) {
            throw new BizException(e);
        }

        return info;
    }

    public boolean batchSave(List<BookEntity> infos) {
        if (ListUtil.isEmpty(infos)) {
            return Boolean.FALSE;
        }
        try {
            BulkRequest bulkRequest = new BulkRequest();

            for (BookEntity info : infos) {
                IndexRequest indexRequest = new IndexRequest(elasticSearchConfig.getBookIndices());
                indexRequest.id(info.getId());
                indexRequest.timeout(TimeValue.timeValueSeconds(3));
                indexRequest.source(JSON.toJSONString(info), XContentType.JSON);

                bulkRequest.add(indexRequest);
            }
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("Function[save]request:{},response:{}", bulkRequest, bulk);
        } catch (Exception e) {
            throw new BizException(e);
        }

        return Boolean.TRUE;
    }

    public boolean update(BookEntity info) {
        try {
            UpdateRequest indexRequest = new UpdateRequest(elasticSearchConfig.getBookIndices(), info.getId());
            indexRequest.doc(JSON.toJSONString(info), XContentType.JSON);
            UpdateResponse update = restHighLevelClient.update(indexRequest, RequestOptions.DEFAULT);

            RestStatus status = update.status();
            return Objects.equals(RestStatus.OK, status);
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    public BookEntity detail(String id) {
        try {
            GetRequest getRequest = new GetRequest(elasticSearchConfig.getBookIndices(), id);

            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            String jsonStr = getResponse.getSourceAsString();

            BookEntity info = JSON.parseObject(jsonStr, BookEntity.class);
            if (Objects.nonNull(info)) {
                info.setId(getResponse.getId());
            }
            return info;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    public PageResp<BookEntity> page(BookQueryReq pageRequest) {
        try {
            SearchRequest searchRequest = new SearchRequest(elasticSearchConfig.getBookIndices());
            //构建搜索条件
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (!StrUtil.isEmpty(pageRequest.getSortField())) {
                SortOrder sortOrder = Objects.equals(1, pageRequest.getSortWay()) ? SortOrder.ASC : SortOrder.DESC;
                searchSourceBuilder.sort(pageRequest.getSortField(), sortOrder);
            }

            if (!StrUtil.isEmpty(pageRequest.getIsbn())) {
                //相当于term查询，通过QueryBuilders完成查询条件的构造
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("isbn", pageRequest.getIsbn());
                boolQueryBuilder.must(termQueryBuilder);
            }

            if (!StrUtil.isEmpty(pageRequest.getAuthor())) {
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("author", pageRequest.getAuthor());
                boolQueryBuilder.must(termQueryBuilder);
            }

            if (!StrUtil.isEmpty(pageRequest.getDescription())) {
                WildcardQueryBuilder wildcardQuery = QueryBuilders
                    .wildcardQuery("description", pageRequest.getDescription());
                boolQueryBuilder.must(wildcardQuery);
            }

            if (!ListUtil.isEmpty(pageRequest.getTags())) {
                BoolQueryBuilder tagBoolQueryBuilder = QueryBuilders.boolQuery();
                for (String tag : pageRequest.getTags()) {
                    tagBoolQueryBuilder.should(QueryBuilders.termQuery("tags", tag));
                }
                boolQueryBuilder.must(tagBoolQueryBuilder);
            }

            searchSourceBuilder.query(boolQueryBuilder);
            int fromIndex = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();
            searchSourceBuilder.from(fromIndex);
            searchSourceBuilder.size(pageRequest.getPageSize());
            // 设置超时时间 1s
            searchSourceBuilder.timeout(TimeValue.timeValueSeconds(1));
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            long totalRecordNumber = hits.getTotalHits().value;

            List<BookEntity> matchRecords = new ArrayList<>();
            for (SearchHit hit : hits) {
                BookEntity entity = JSON.parseObject(hit.getSourceAsString(), BookEntity.class);
                matchRecords.add(entity);
            }

            boolean isWhole = totalRecordNumber % pageRequest.getPageSize() == 0;
            int pageWatchNum = (int) totalRecordNumber / pageRequest.getPageSize();
            int totalPageNumber = isWhole ? pageWatchNum : pageWatchNum + 1;

            return PageResp.create(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalPageNumber,
                (int) totalRecordNumber, matchRecords);
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    public boolean delete(String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(elasticSearchConfig.getBookIndices(), id);
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            RestStatus status = deleteResponse.status();
            return Objects.equals(RestStatus.OK, status);
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

}
