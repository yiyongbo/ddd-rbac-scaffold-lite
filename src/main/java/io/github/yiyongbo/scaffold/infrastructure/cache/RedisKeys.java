package io.github.yiyongbo.scaffold.infrastructure.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Redis Key 常量
 *
 * @author kidd
 * @since 2026/7/7 15:36
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisKeys {

    /**
     * 登录会话前缀
     */
    public static final String LOGIN_SESSION_PREFIX = "login:session:";

    /**
     * 用户权限缓存 Key 前缀
     */
    public static final String USER_PERMISSION_PREFIX = "auth:permission:user:";

    public static String loginSessionKey(String jti) {
        return LOGIN_SESSION_PREFIX + jti;
    }

    public static String userPermissionKey(Long userId) {
        return USER_PERMISSION_PREFIX + userId;
    }
}
