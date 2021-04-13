package com.search.pkgs.controller.document;

import com.search.pkgs.common.response.SysResp;
import com.search.pkgs.model.entity.BookEntity;
import com.search.pkgs.model.request.BookQueryReq;
import com.search.pkgs.model.response.PageResp;
import com.search.pkgs.service.BookDocumentService;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:27
 */
@RestController
@RequestMapping("/books")
public class BookController {
    @Resource
    private BookDocumentService bookDocumentService;

    @PostMapping("")
    public SysResp<Object> save(@RequestBody BookEntity book) {
        BookEntity save = bookDocumentService.save(book);
        return SysResp.success(save);
    }

    @PutMapping("")
    public SysResp<Boolean> update(@RequestBody BookEntity book) {
        boolean result = bookDocumentService.update(book);
        return SysResp.success(result);
    }

    @DeleteMapping("/{id}")
    public SysResp<Boolean> delete(@PathVariable("id") String id) {
        boolean result = bookDocumentService.delete(id);
        return SysResp.success(result);
    }

    @GetMapping("")
    public SysResp<PageResp<BookEntity>> page(BookQueryReq pageRequest) {
        PageResp<BookEntity> page = bookDocumentService.page(pageRequest);
        return SysResp.success(page);
    }

    @GetMapping("/{id}")
    public SysResp<Object> detail(@PathVariable("id") String id) {
        BookEntity detail = bookDocumentService.detail(id);
        return SysResp.success(detail);
    }

}
