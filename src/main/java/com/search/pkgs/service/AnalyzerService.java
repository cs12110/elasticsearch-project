package com.search.pkgs.service;

import com.google.common.base.Preconditions;
import com.search.pkgs.common.constant.SysConstant;
import com.search.pkgs.common.exceptions.BizException;
import com.search.pkgs.model.request.AnalyzerReq;
import com.search.pkgs.model.response.AnalyzerResp;
import com.search.pkgs.util.StrUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 15:26
 */
@Slf4j
@Service
public class AnalyzerService {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 分词
     *
     * @param req 分词请求
     * @return List
     */
    public List<AnalyzerResp> analyzer(AnalyzerReq req) {
        checkArgs(req);
        try {
            String analyzer = StrUtil.isEmpty(req.getAnalyzer()) ? SysConstant.DEFAULT_ANALYZER : req.getAnalyzer();

            AnalyzeRequest analyzeRequest = AnalyzeRequest.withGlobalAnalyzer(analyzer, req.getText());
            AnalyzeResponse analyzeResponse = restHighLevelClient.indices()
                .analyze(analyzeRequest, RequestOptions.DEFAULT);

            List<AnalyzeResponse.AnalyzeToken> tokens = analyzeResponse.getTokens();
            List<AnalyzerResp> words = new ArrayList<>(tokens.size());
            for (AnalyzeResponse.AnalyzeToken token : tokens) {
                AnalyzerResp resp = new AnalyzerResp();
                resp.setWord(token.getTerm());
                resp.setType(token.getType());
                words.add(resp);
            }
            return words;
        } catch (Exception e) {
            throw new BizException("分词异常," + e.getMessage());
        }
    }

    private void checkArgs(AnalyzerReq req) {
        Preconditions.checkArgument(StrUtil.isNotEmpty(req.getText()), "分词内容不能为空");
        //        if(StrUtil.isEmpty(req.getText())){
        //            throw  new BizException("分词内容不能为空");
        //        }
    }
}
