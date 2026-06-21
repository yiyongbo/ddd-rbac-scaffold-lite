package io.github.yiyongbo.scaffold.common.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置属性
 *
 * @author kidd
 * @since 2026/6/18 22:49
 */
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * 签发者
     */
    private String issuer = "ddd-rbac-scaffold-lite";

    /**
     * 加密密钥
     */
    private String secret;

    /**
     * 访问令牌过期时间（秒）
     */
    private Long accessTokenExpireSeconds = 7200L;
}
