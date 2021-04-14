package com.search.pkgs.common.response;

import com.alibaba.fastjson.JSON;
import com.search.pkgs.common.enums.ResponseCodeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:31
 */
@Data
public class SysResp<T> {

    @ApiModelProperty(value = "请求编码,1:成功,-1:失败", required = true)
    private Integer code;

    @ApiModelProperty(value = "提示信息", required = true)
    private String message;

    @ApiModelProperty(value = "数据")
    private T value;

    public static <T> SysResp<T> success(T value) {
        SysResp<T> sysResp = new SysResp<>();
        sysResp.setCode(ResponseCodeEnum.SUCCESS.getValue());
        sysResp.setMessage(ResponseCodeEnum.SUCCESS.getLabel());
        sysResp.setValue(value);

        return sysResp;
    }

    public static <T> SysResp<T> failure(String message) {
        SysResp<T> sysResp = new SysResp<>();
        sysResp.setCode(ResponseCodeEnum.FAILURE.getValue());
        sysResp.setMessage(ResponseCodeEnum.FAILURE.getLabel());
        sysResp.setMessage(message);

        return sysResp;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
