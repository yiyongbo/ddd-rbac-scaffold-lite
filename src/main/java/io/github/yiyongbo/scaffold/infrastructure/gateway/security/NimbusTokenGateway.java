package io.github.yiyongbo.scaffold.infrastructure.gateway.security;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.exception.BizException;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayloadValueObject;
import io.github.yiyongbo.scaffold.domain.common.gateway.TokenGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

/**
 * Nimbus Token Gateway
 *
 * @author kidd
 * @since 2026/6/18 22:51
 */
@Component
@RequiredArgsConstructor
public class NimbusTokenGateway implements TokenGateway {

    private static final String CLAIM_USERNAME = "username";

    private final JwtProperties jwtProperties;

    @Override
    public String generateAccessToken(TokenPayloadValueObject payload) {
        validatePayload(payload);

        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(jwtProperties.getAccessTokenExpireSeconds());

        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).build();

        String jti = StrUtil.isNotBlank(payload.getJti()) ? payload.getJti() : IdUtil.fastUUID();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(jwtProperties.getIssuer())
                .subject(String.valueOf(payload.getUserId()))
                .jwtID(jti)
                .claim(CLAIM_USERNAME, payload.getUsername())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expireAt))
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);

        try {
            signedJWT.sign(new MACSigner(getSecretBytes()));
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new BizException(CommonResponseCode.INTERNAL_ERROR, "生成访问令牌失败");
        }
    }

    @Override
    public TokenPayloadValueObject parseAccessToken(String accessToken) {
        BizAssert.isNotBlank(accessToken, CommonResponseCode.UNAUTHORIZED, "访问令牌不能为空");
        try {
            SignedJWT signedJWT = SignedJWT.parse(accessToken);

            boolean verifiedSignature = verifySignature(signedJWT);
            BizAssert.isTrue(verifiedSignature, CommonResponseCode.UNAUTHORIZED, "访问令牌无效");

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            validateClaims(claimsSet);

            return TokenPayloadValueObject.builder()
                    .jti(claimsSet.getJWTID())
                    .userId(Long.valueOf(claimsSet.getSubject()))
                    .username(claimsSet.getStringClaim(CLAIM_USERNAME))
                    .build();
        } catch (Exception e) {
            throw new BizException(CommonResponseCode.UNAUTHORIZED, "访问令牌无效");
        }
    }

    @Override
    public Long getAccessTokenExpiresIn() {
        return jwtProperties.getAccessTokenExpireSeconds();
    }

    private void validatePayload(TokenPayloadValueObject payload) {
        BizAssert.notNull(payload, CommonResponseCode.INTERNAL_ERROR, "Token载荷不能为空");
        BizAssert.notNull(payload.getUserId(), CommonResponseCode.INTERNAL_ERROR, "Token用户ID不能为空");
        BizAssert.isNotBlank(payload.getUsername(), CommonResponseCode.INTERNAL_ERROR, "Token用户名不能为空");
    }

    private byte[] getSecretBytes() {
        String secret = jwtProperties.getSecret();
        BizAssert.isNotBlank(secret, CommonResponseCode.INTERNAL_ERROR, "JWT密钥不能为空");

        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        BizAssert.isTrue(secretBytes.length >= 32, CommonResponseCode.INTERNAL_ERROR, "JWT密钥长度不能小于32字节");

        return secretBytes;
    }

    private boolean verifySignature(SignedJWT signedJWT) {
        try {
            JWSVerifier verifier = new MACVerifier(getSecretBytes());
            return signedJWT.verify(verifier);
        } catch (Exception e) {
            throw BizAssert.newException(CommonResponseCode.UNAUTHORIZED, "访问令牌无效");
        }
    }

    private void validateClaims(JWTClaimsSet claimsSet) throws ParseException {
        BizAssert.isNotBlank(claimsSet.getJWTID(), CommonResponseCode.UNAUTHORIZED, "访问令牌无效");
        BizAssert.isTrue(jwtProperties.getIssuer().equals(claimsSet.getIssuer()), CommonResponseCode.UNAUTHORIZED, "访问令牌无效");
        BizAssert.isNotBlank(claimsSet.getSubject(), CommonResponseCode.UNAUTHORIZED, "访问令牌无效");
        BizAssert.isNotBlank(claimsSet.getStringClaim(CLAIM_USERNAME), CommonResponseCode.UNAUTHORIZED, "访问令牌无效");

        Date expirationTime = claimsSet.getExpirationTime();
        BizAssert.isTrue(expirationTime != null && expirationTime.after(new Date()), CommonResponseCode.UNAUTHORIZED, "访问令牌已过期");
    }

}
