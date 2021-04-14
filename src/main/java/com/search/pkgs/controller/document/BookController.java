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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:27
 */
@Api(tags = "书籍功能")
@RestController
@RequestMapping("/books")
public class BookController {
    @Resource
    private BookDocumentService bookDocumentService;

    @ApiOperation(value = "书籍-保存数据", notes = "author: cs12110")
    @PostMapping("")
    public SysResp<BookEntity> save(@RequestBody BookEntity book) {
        BookEntity save = bookDocumentService.save(book);
        return SysResp.success(save);
    }

    @ApiOperation(value = "书籍-更新数据", notes = "author: cs12110")
    @PutMapping("")
    public SysResp<Boolean> update(@RequestBody BookEntity book) {
        boolean result = bookDocumentService.update(book);
        return SysResp.success(result);
    }

    @ApiOperation(value = "书籍-删除数据", notes = "author: cs12110")
    @DeleteMapping("/{id}")
    public SysResp<Boolean> delete(@PathVariable("id") String id) {
        boolean result = bookDocumentService.delete(id);
        return SysResp.success(result);
    }

    @ApiOperation(value = "书籍-分页查询", notes = "author: cs12110")
    @GetMapping("")
    public SysResp<PageResp<BookEntity>> page(BookQueryReq pageRequest) {
        PageResp<BookEntity> page = bookDocumentService.page(pageRequest);
        return SysResp.success(page);
    }

    @ApiOperation(value = "书籍-查询详情", notes = "author: cs12110")
    @GetMapping("/{id}")
    public SysResp<BookEntity> detail(@PathVariable("id") String id) {
        BookEntity detail = bookDocumentService.detail(id);
        return SysResp.success(detail);
    }

}
