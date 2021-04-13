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

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2021-04-12 14:38
 */
@RestController
@RequestMapping("/indexes")
public class IndexesController {
    @Resource
    private IndexService indexService;

    @PostMapping
    public SysResp<Boolean> createIndices() {
        return SysResp.success(Boolean.TRUE);
    }

    @DeleteMapping
    public SysResp<Boolean> deleteIndices() {
        return SysResp.success(Boolean.TRUE);
    }

    @GetMapping("/{indexName}")
    public SysResp<IndexInfoEntity> getIndex(@PathVariable("indexName") String indexName) {
        IndexInfoEntity index = indexService.getMapping(indexName);
        return SysResp.success(index);
    }
}
