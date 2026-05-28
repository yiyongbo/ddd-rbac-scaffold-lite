package io.github.yiyongbo.scaffold.common.response;

import lombok.*;

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

    public static <T> Result<T> success() {
        return new Result<>("00000", "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>("00000", "success", data);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, null);
    }

}
