package com.search.pkgs.controller.index;

import com.search.pkgs.common.response.SysResp;
import com.search.pkgs.model.entity.IndexInfoEntity;
import com.search.pkgs.service.IndexService;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 14:38
 */
@Api(tags = "索引功能")
@RestController
@RequestMapping("/indexes")
public class IndexesController {
    @Resource
    private IndexService indexService;

    @ApiOperation("索引-创建索引")
    @PostMapping
    public SysResp<Boolean> createIndex() {
        return SysResp.success(Boolean.TRUE);
    }

    @ApiOperation("索引-删除索引")
    @DeleteMapping("/{indexName}")
    public SysResp<Boolean> deleteIndex(@PathVariable("indexName") String indexName) {
        return SysResp.success(Boolean.TRUE);
    }

    @ApiOperation("索引-获取索引mapping")
    @GetMapping("/mapping/{indexName}")
    public SysResp<IndexInfoEntity> getMapping(@PathVariable("indexName") String indexName) {
        IndexInfoEntity index = indexService.getMapping(indexName);
        return SysResp.success(index);
    }
}
