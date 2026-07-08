package io.github.yiyongbo.scaffold.domain.auth.gateway;

import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayloadValueObject;

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
    String generateAccessToken(TokenPayloadValueObject payload);

    /**
     * 解析访问令牌
     *
     * @param accessToken 访问令牌
     * @return 令牌Payload
     */
    TokenPayloadValueObject parseAccessToken(String accessToken);

}
