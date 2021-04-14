package com.search.pkgs.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2021-04-12 15:49
 */
@Data
public class IndexInfoEntity {

    @ApiModelProperty("索引名称")
    private String indexName;

    @ApiModelProperty("索引mapping")
    private Object mapping;

    private Object setting;

}
