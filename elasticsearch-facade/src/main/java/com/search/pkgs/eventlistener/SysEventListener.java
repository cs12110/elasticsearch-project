package com.search.pkgs.eventlistener;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;
import com.search.pkgs.events.SysEvent;
import com.search.pkgs.events.payload.DataChangePayload;
import com.search.pkgs.model.entity.BookEntity;
import com.search.pkgs.service.BookDocumentService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-20 09:58
 */
@Service
@Slf4j
public class SysEventListener {

    @Resource
    private BookDocumentService bookDocumentService;

    @SuppressWarnings("all")
    @Subscribe
    public void consume(SysEvent sysEvent) {
        log.info("Function[consume] event:{}", sysEvent);

        if ("UPDATE".equals(sysEvent.getBusinessType())) {
            update(sysEvent);
        }
        if ("DELETE".equals(sysEvent.getBusinessType())) {
            delete(sysEvent);
        }

        if ("INSERT".equals(sysEvent.getBusinessType())) {
            add(sysEvent);
        }
    }

    private void delete(SysEvent sysEvent) {
        BookEntity bookEntity = parseToEntity(sysEvent.getPayload());
        bookDocumentService.delete(bookEntity.getId());
    }

    private void add(SysEvent sysEvent) {
        BookEntity bookEntity = parseToEntity(sysEvent.getPayload());
        bookDocumentService.save(bookEntity);
    }

    private void update(SysEvent sysEvent) {
        BookEntity bookEntity = parseToEntity(sysEvent.getPayload());
        bookDocumentService.update(bookEntity);
    }

    private BookEntity parseToEntity(String payload) {
        DataChangePayload changePayload = JSON.parseObject(payload, DataChangePayload.class);
        String jsonString = JSON.toJSONString(changePayload.getValueMap());
        return JSON.parseObject(jsonString, BookEntity.class);
    }
}
