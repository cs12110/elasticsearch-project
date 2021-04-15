package com.search.pkgs.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-15 22:30
 */
@Data
public class AnalyzerReq {

    @ApiModelProperty(value = "字符串", required = true)
    private String text;

    @ApiModelProperty("分词器,默认为: ik_smart")
    private String analyzer;
}
