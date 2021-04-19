package com.search.pkgs.spider.douban;

import com.search.pkgs.constant.SysConstant;
import com.search.pkgs.model.entity.BookEntity;
import com.search.pkgs.spider.douban.parser.BookParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-15 17:28
 */
@Slf4j
@Component
public class BookSpider {

    @Resource
    private RestTemplate restTemplate;

    public List<BookEntity> execute() {
        List<BookEntity> books = new ArrayList<>();
        for (int index = 0; index < 11; index++) {
            books.addAll(execute(index));
        }

        return books;
    }

    private List<BookEntity> execute(int pageIndex) {
        long spiderStart = System.currentTimeMillis();
        List<BookEntity> books = getBooksFromWebsite(pageIndex);
        long spiderEnd = System.currentTimeMillis();
        log.info("Function[execute] pageIndex:{}, spend:{} ms", pageIndex, (spiderEnd - spiderStart));

        return books;
    }

    private List<BookEntity> getBooksFromWebsite(int pageIndex) {
        int startNum = pageIndex * 25;
        String url = "https://book.douban.com/top250?start=" + startNum;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, SysConstant.USER_AGENT);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> rpcResult = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        HttpStatus statusCode = rpcResult.getStatusCode();
        List<BookEntity> books = Collections.emptyList();
        if (HttpStatus.OK.equals(statusCode)) {
            String body = rpcResult.getBody();
            books = BookParser.parse(body);
        }
        return books;
    }

}
