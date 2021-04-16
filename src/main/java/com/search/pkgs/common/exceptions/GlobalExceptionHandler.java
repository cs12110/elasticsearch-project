package com.search.pkgs.common.exceptions;

import com.search.pkgs.common.response.SysResp;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-16 09:11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    public SysResp<String> handleBizException(BizException ex) {
        log.error("Function[handleBizException]", ex);

        return SysResp.failure(ex.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public SysResp<String> handleIllegalArgException(IllegalArgumentException ex) {
        if (log.isDebugEnabled()) {
            log.error("Function[handleIllegalArgException]", ex);
        }
        return SysResp.failure(ex.getMessage());
    }

}
