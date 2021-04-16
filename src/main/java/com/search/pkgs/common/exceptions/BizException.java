package com.search.pkgs.common.exceptions;

/**
 * @author cs12110
 * @version V1.0
 * @since 2021-04-12 10:14
 */
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(Throwable cause, String message) {
        super(message, cause);
    }
}
