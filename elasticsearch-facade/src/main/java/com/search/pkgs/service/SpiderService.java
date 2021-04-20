package com.search.pkgs.service;

import com.search.pkgs.model.entity.BookEntity;
import com.search.pkgs.spider.douban.BookSpider;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-19 15:37
 */
@Service
public class SpiderService {
    @Resource
    private BookSpider bookSpider;
    @Resource
    private BookDocumentService bookDocumentService;

    public void exec() {
        List<BookEntity> values = bookSpider.execute();
        bookDocumentService.batchSave(values);
    }
}
