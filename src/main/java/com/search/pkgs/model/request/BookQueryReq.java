package com.search.pkgs.model.request;

import com.alibaba.fastjson.JSON;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 13:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BookQueryReq extends PageReq {

    private String isbn;
    private String description;
    private String author;
    private List<String> tags;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
