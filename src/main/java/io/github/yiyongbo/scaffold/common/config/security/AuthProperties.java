package io.github.yiyongbo.scaffold.common.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 认证 配置属性
 *
 * @author kidd
 * @since 2026/7/8 13:52
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    /**
     * 授权头
     */
    private String authorizationHeader = "Authorization";

    /**
     * Token 前缀
     */
    private String tokenPrefix = "Bearer";

    /**
     * 登录会话 TTL
     */
    private Duration loginSessionTtl = Duration.ofHours(2);

    /**
     * 登录用户会话索引 TTL
     */
    private Duration loginUserSessionIndexTtl = Duration.ofDays(7);

    /**
     * 用户权限缓存 TTL
     */
    private Duration userPermissionCacheTtl = Duration.ofMinutes(30);
}
