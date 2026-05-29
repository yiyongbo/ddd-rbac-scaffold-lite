package io.github.yiyongbo.scaffold.common.response;

import io.github.yiyongbo.scaffold.common.constants.TraceConstants;
import lombok.*;
import org.slf4j.MDC;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应对象
 *
 * @author kidd
 * @since 2026/5/28 22:09
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应码，00000 表示成功，其他表示失败
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 链路追踪 ID
     */
    private String traceId;

    public static <T> Result<T> success() {
        return new Result<>(CommonResponseCode.SUCCESS.getCode(), CommonResponseCode.SUCCESS.getMessage(), null, getTraceId());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CommonResponseCode.SUCCESS.getCode(), CommonResponseCode.SUCCESS.getMessage(), data, getTraceId());
    }

    public static <T> Result<T> fail(ResponseCode code) {
        return new Result<>(code.getCode(), code.getMessage(), null, getTraceId());
    }

    public static <T> Result<T> fail(ResponseCode code, String message) {
        return new Result<>(code.getCode(), message, null, getTraceId());
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, null, getTraceId());
    }

    private static String getTraceId() {
        return MDC.get(TraceConstants.TRACE_ID);
    }

}
