package io.github.yiyongbo.scaffold.common.error;

/**
 * 响应码接口
 *
 * @author kidd
 * @since 2026/5/28 22:27
 */
public interface ResponseCode {

    /**
     * 响应码
     */
    String getCode();

    /**
     * 响应消息
     */
    String getMessage();

}
