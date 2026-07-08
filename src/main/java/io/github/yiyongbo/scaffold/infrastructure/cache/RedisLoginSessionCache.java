package io.github.yiyongbo.scaffold.infrastructure.cache;

import cn.hutool.core.util.StrUtil;
import io.github.yiyongbo.scaffold.common.util.RedisUtils;
import io.github.yiyongbo.scaffold.domain.auth.cache.LoginSessionCache;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.LoginSessionValueObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 登录会话 Cache Redis 实现
 *
 * @author kidd
 * @since 2026/7/7 16:47
 */
@Component
@RequiredArgsConstructor
public class RedisLoginSessionCache implements LoginSessionCache {

    private final RedisUtils redisUtils;

    @Override
    public void save(LoginSessionValueObject session, Duration ttl) {
        String loginSessionKey = RedisKeys.loginSessionKey(session.getJti());
        String loginUserSessionsKey = RedisKeys.loginUserSessionsKey(session.getUserId());

        redisUtils.setObject(loginSessionKey, session, ttl);
        redisUtils.setAdd(loginUserSessionsKey, session.getJti());
        redisUtils.expire(loginUserSessionsKey, ttl);
    }

    @Override
    public boolean exists(String jti) {
        if (StrUtil.isBlank(jti)) {
            return false;
        }
        return redisUtils.hasKey(RedisKeys.loginSessionKey(jti));
    }

    @Override
    public LoginSessionValueObject get(String jti) {
        if (StrUtil.isBlank(jti)) {
            return null;
        }
        return redisUtils.getObject(RedisKeys.loginSessionKey(jti), LoginSessionValueObject.class);
    }

    @Override
    public void delete(String jti) {
        if (StrUtil.isBlank(jti)) {
            return;
        }

        LoginSessionValueObject loginSession = get(jti);
        redisUtils.delete(RedisKeys.loginSessionKey(jti));

        if (loginSession != null && loginSession.getUserId() != null) {
            String loginUserSessionsKey = RedisKeys.loginUserSessionsKey(loginSession.getUserId());
            redisUtils.setRemove(loginUserSessionsKey, jti);
        }
    }

    @Override
    public void deleteByUserId(Long userId) {
        if (userId == null) {
            return;
        }
        String loginUserSessionsKey = RedisKeys.loginUserSessionsKey(userId);
        redisUtils.delete(loginUserSessionsKey);
    }

    @Override
    public void refreshExpire(String jti, Duration ttl) {
        if (StrUtil.isBlank(jti)) {
            return;
        }
        redisUtils.expire(RedisKeys.loginSessionKey(jti), ttl);
    }

}
