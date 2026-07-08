package io.github.yiyongbo.scaffold.common.exception;

import cn.hutool.core.util.StrUtil;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.common.response.ResponseCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 业务断言工具
 *
 * @author kidd
 * @since 2026/5/29 22:25
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BizAssert {

    public static BizException newException(ResponseCode responseCode, String message) {
        return new BizException(responseCode, message);
    }

    public static void isTrue(boolean expression, ResponseCode responseCode, String message) {
        if (!expression) {
            throw new BizException(responseCode, message);
        }
    }

    public static void notNull(Object object, ResponseCode responseCode, String message) {
        if (object == null) {
            throw new BizException(responseCode, message);
        }
    }

    public static void isBlank(String str, CommonResponseCode responseCode, String message) {
        if (StrUtil.isNotBlank(str)) {
            throw new BizException(responseCode, message);
        }
    }

    public static void isNotBlank(String str, CommonResponseCode responseCode, String message) {
        if (StrUtil.isBlank(str)) {
            throw new BizException(responseCode, message);
        }
    }

}
