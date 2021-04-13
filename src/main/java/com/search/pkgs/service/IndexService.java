package com.search.pkgs.service;

import com.alibaba.fastjson.JSON;
import com.search.pkgs.common.conig.ElasticSearchConfig;
import com.search.pkgs.common.exceptions.BizException;
import com.search.pkgs.model.entity.IndexInfoEntity;

import java.util.Map;

import javax.annotation.Resource;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2021-04-12 15:26
 */
@Slf4j
@Service
public class IndexService {
    @Resource
    private ElasticSearchConfig elasticSearchConfig;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    public IndexInfoEntity getMapping(String indexName) {
        IndexInfoEntity indexInfoEntity = new IndexInfoEntity();
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            GetIndexResponse getIndexResponse = restHighLevelClient.indices()
                .get(getIndexRequest, RequestOptions.DEFAULT);

            log.info("Function[getIndex] request:{}, response:{}", indexName, getIndexResponse);

            Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();
            Map<String, Settings> settings = getIndexResponse.getSettings();
            indexInfoEntity.setIndexName(indexName);
            indexInfoEntity.setMapping(mappings);
            indexInfoEntity.setSetting(settings);

            return indexInfoEntity;
        } catch (Exception e) {
            throw new BizException(e);
        }
    }
}