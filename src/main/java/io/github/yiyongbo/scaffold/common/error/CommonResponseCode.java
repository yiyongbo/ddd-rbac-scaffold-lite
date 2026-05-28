package io.github.yiyongbo.scaffold.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通用响应码
 *
 * @author kidd
 * @since 2026/5/28 22:31
 */
@Getter
@RequiredArgsConstructor
public enum CommonResponseCode implements ResponseCode {

    /**
     * 成功
     */
    SUCCESS("00000", "请求成功"),

    // 用户端错误
    USER_ERROR("A00001", "用户端错误"),
    PARAM_ERROR("A00002", "请求参数错误"),
    UNAUTHORIZED("A00003", "认证失败"),
    FORBIDDEN("A00004", "权限不足"),
    NOT_FOUND("A00005", "资源不存在"),

    // 系统执行错误
    SYSTEM_ERROR("B00001", "系统异常"),
    BUSINESS_ERROR("B00002", "业务处理失败"),

    // 第三方服务错误
    THIRD_PARTY_ERROR("C00001", "第三方服务异常"),
    THIRD_PARTY_API_ERROR("C00002", "第三方接口调用失败"),
    ;

    private final String code;

    private final String message;
}
