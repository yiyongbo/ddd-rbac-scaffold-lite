package io.github.yiyongbo.scaffold.domain.common.gateway;

import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayload;

/**
 * Token Gateway
 *
 * @author kidd
 * @since 2026/6/16 23:05
 */
public interface TokenGateway {

    /**
     * 生成访问令牌
     *
     * @param payload 令牌Payload
     * @return 访问令牌
     */
    String generateAccessToken(TokenPayload payload);

    /**
     * 解析访问令牌
     *
     * @param accessToken 访问令牌
     * @return 令牌Payload
     */
    TokenPayload parseAccessToken(String accessToken);

    /**
     * 获取访问令牌过期时间
     *
     * @return 过期时间（秒）
     */
    Long getAccessTokenExpiresIn();

}
