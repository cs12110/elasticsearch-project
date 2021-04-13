package com.search.pkgs.model.request;

import java.util.Objects;

import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 11:19
 */
@Data
public class PageReq {
    private Integer pageNumber;
    private Integer pageSize;

    public Integer getPageNumber() {
        return Objects.nonNull(pageNumber) ? pageNumber : 1;
    }

    public Integer getPageSize() {
        return Objects.nonNull(pageSize) ? pageSize : 10;
    }

}
