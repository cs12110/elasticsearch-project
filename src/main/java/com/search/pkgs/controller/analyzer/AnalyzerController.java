package com.search.pkgs.controller.analyzer;

import com.search.pkgs.common.response.SysResp;
import com.search.pkgs.model.request.AnalyzerReq;
import com.search.pkgs.model.response.AnalyzerResp;
import com.search.pkgs.service.AnalyzerService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-15 22:28
 */
@Api(tags = "分词功能")
@RestController
@RequestMapping("/analyzer")
public class AnalyzerController {

    @Resource
    private AnalyzerService analyzerService;

    @ApiOperation(value = "分词-分词接口", notes = "author: cs12110")
    @PostMapping
    public SysResp<List<AnalyzerResp>> analyzer(@RequestBody AnalyzerReq analyzerReq) {
        List<AnalyzerResp> values = analyzerService.analyzer(analyzerReq);
        return SysResp.success(values);
    }

}
