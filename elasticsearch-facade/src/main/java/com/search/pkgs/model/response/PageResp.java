package com.search.pkgs.model.response;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 13:31
 */
@Data
public class PageResp<T> {

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPageNumber;
    private Integer totalRecordNumber;
    private List<T> list;

    public static <T> PageResp<T> create(Integer pageNumber, Integer pageSize, Integer totalPageNumber,
        Integer totalRecordNumber, List<T> list) {
        PageResp<T> pageValue = new PageResp<>();
        pageValue.setPageNumber(pageNumber);
        pageValue.setPageSize(pageSize);
        pageValue.setTotalPageNumber(totalPageNumber);
        pageValue.setTotalRecordNumber(totalRecordNumber);
        if (Objects.nonNull(list)) {
            pageValue.setList(list);
        } else {
            pageValue.setList(Collections.emptyList());
        }

        return pageValue;
    }
}
