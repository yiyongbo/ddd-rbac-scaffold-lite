package io.github.yiyongbo.scaffold.common.exception;

import io.github.yiyongbo.scaffold.common.response.ResponseCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author kidd
 * @since 2026/5/28 22:56
 */
@Getter
public class BizException extends RuntimeException {

    /**
     * 响应码
     */
    private final String code;

    public BizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public BizException(ResponseCode responseCode, String message) {
        super(message);
        this.code = responseCode.getCode();
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

}
