package io.github.yiyongbo.scaffold.common.response;

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
    USER_ERROR("A0001", "用户端错误"),
    PARAM_ERROR("A0400", "请求参数错误"),
    UNAUTHORIZED("A0401", "认证失败"),
    FORBIDDEN("A0403", "权限不足"),
    NOT_FOUND("A0404", "资源不存在"),
    METHOD_NOT_ALLOWED("A0405", "请求方法不支持"),
    UNSUPPORTED_MEDIA_TYPE("A0415", "请求媒体类型不支持"),



    // 系统执行错误
    SYSTEM_ERROR("B0001", "系统执行错误"),
    INTERNAL_ERROR("B0500", "系统内部错误"),
    SERVICE_UNAVAILABLE("B0503", "服务不可用"),



    // 第三方服务错误
    THIRD_PARTY_ERROR("C0001", "第三方服务异常"),
    THIRD_PARTY_API_ERROR("C0002", "第三方接口调用失败"),
    ;

    private final String code;

    private final String message;
}
