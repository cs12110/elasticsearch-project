package com.search.pkgs.controller.spider;

import com.search.pkgs.common.response.SysResp;
import com.search.pkgs.spider.douban.BookSpider;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-15 17:44
 */
@RestController
@RequestMapping("/spider")
public class SpiderController {
    @Resource
    private BookSpider bookSpider;

    @PostMapping("/exec")
    public SysResp<Boolean> execute() {
        bookSpider.execute();
        return SysResp.success(Boolean.TRUE);
    }
}
