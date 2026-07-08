package io.github.yiyongbo.scaffold.infrastructure.cache;

import cn.hutool.core.collection.CollUtil;
import io.github.yiyongbo.scaffold.common.util.RedisUtils;
import io.github.yiyongbo.scaffold.domain.user.cache.UserPermissionCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户权限 Cache Redis 实现
 *
 * @author kidd
 * @since 2026/7/8 10:08
 */
@Component
@RequiredArgsConstructor
public class RedisUserPermissionCache implements UserPermissionCache {

    private final RedisUtils redisUtils;

    @Override
    public List<String> get(Long userId) {
        if (userId == null) {
            return null;
        }

        String userPermissionKey = RedisKeys.userPermissionKey(userId);
        if (!redisUtils.hasKey(userPermissionKey)) {
            return null;
        }

        List<String> userPermissions = redisUtils.getList(userPermissionKey, String.class);
        return userPermissions == null ? List.of() : userPermissions;
    }

    @Override
    public void save(Long userId, Collection<String> permissions, Duration ttl) {
        if (userId == null) {
            return;
        }

        List<String> userPermissions = CollUtil.isEmpty(permissions) ? List.of() : permissions.stream().filter(Objects::nonNull).distinct().toList();

        String userPermissionKey = RedisKeys.userPermissionKey(userId);
        redisUtils.setObject(userPermissionKey, userPermissions, ttl);
    }

    @Override
    public void delete(Long userId) {
        if (userId == null) {
            return;
        }

        String userPermissionKey = RedisKeys.userPermissionKey(userId);
        redisUtils.delete(userPermissionKey);
    }

    @Override
    public void deleteBatch(Collection<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return;
        }

        List<String> userPermissionKeys = userIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .map(RedisKeys::userPermissionKey)
                .toList();

        redisUtils.delete(userPermissionKeys);
    }
}
