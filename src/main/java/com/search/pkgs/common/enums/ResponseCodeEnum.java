package com.search.pkgs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 09:30
 */
@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {
    /**
     * 1: 成功
     */
    SUCCESS(1, "成功"),
    /**
     * -1:失败
     */
    FAILURE(-1, "失败");

    private final Integer value;
    private final String label;
}
