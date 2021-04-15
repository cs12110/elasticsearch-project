package com.search.pkgs.model.request;

import com.alibaba.fastjson.JSON;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("isbn")
    private String isbn;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("tags")
    private List<String> tags;

    @ApiModelProperty("排序字段")
    private String sortField;

    @ApiModelProperty("排序类型,1:升序,-1:降序")
    private Integer sortWay;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
