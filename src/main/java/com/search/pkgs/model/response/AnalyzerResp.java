package com.search.pkgs.model.response;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-15 22:36
 */
@Data
public class AnalyzerResp {

    @ApiModelProperty("词语")
    private String word;
    @ApiModelProperty("类型")
    private String type;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
