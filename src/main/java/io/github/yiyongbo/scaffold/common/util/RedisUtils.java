package io.github.yiyongbo.scaffold.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Redis 工具类
 *
 * @author kidd
 * @since 2026/7/7 15:28
 */
@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 设置字符串值。
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置字符串值并指定过期时间。
     *
     * @param key     key
     * @param value   value
     * @param timeout 过期时间
     */
    public void set(String key, String value, Duration timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 设置对象值，使用 JSON 序列化。
     *
     * @param key   key
     * @param value 对象值
     */
    public void setObject(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toJsonString(value));
    }

    /**
     * 设置对象值并指定过期时间，使用 JSON 序列化。
     *
     * @param key     key
     * @param value   对象值
     * @param timeout 过期时间
     */
    public void setObject(String key, Object value, Duration timeout) {
        stringRedisTemplate.opsForValue().set(key, JsonUtils.toJsonString(value), timeout);
    }

    /**
     * 获取字符串值。
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取对象值。
     *
     * @param key   key
     * @param clazz 目标类型
     * @param <T>   目标泛型
     * @return 对象值，key 不存在时返回 null
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            return null;
        }
        return JsonUtils.toObject(value, clazz);
    }

    /**
     * 删除 key。
     *
     * @param key key
     * @return 是否删除成功
     */
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除 key。
     *
     * @param keys key 集合
     * @return 删除数量
     */
    public Long delete(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return 0L;
        }

        return stringRedisTemplate.delete(keys);
    }

    /**
     * 判断 key 是否存在。
     *
     * @param key key
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间。
     *
     * @param key     key
     * @param timeout 过期时间
     * @return 是否设置成功
     */
    public Boolean expire(String key, Duration timeout) {
        return Boolean.TRUE.equals(stringRedisTemplate.expire(key, timeout));
    }

    /**
     * 获取剩余过期时间，单位秒。
     *
     * @param key key
     * @return 剩余秒数；-1 表示永久有效；-2 表示 key 不存在
     */
    public Long getExpireSeconds(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 递增。
     *
     * @param key key
     * @return 递增后的值
     */
    public Long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    /**
     * 递增指定步长。
     *
     * @param key   key
     * @param delta 步长
     * @return 递增后的值
     */
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 设置 Set 集合。
     *
     * @param key    key
     * @param values values
     * @return 新增数量
     */
    public Long setAdd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    /**
     * 获取 Set 集合。
     *
     * @param key key
     * @return Set 集合
     */
    public Set<String> setMembers(String key) {
        Set<String> members = stringRedisTemplate.opsForSet().members(key);
        return members == null ? Collections.emptySet() : members;
    }

    /**
     * 判断 Set 中是否存在指定值。
     *
     * @param key   key
     * @param value value
     * @return 是否存在
     */
    public Boolean setContains(String key, String value) {
        return Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 移除 Set 中的值。
     *
     * @param key    key
     * @param values values
     * @return 移除数量
     */
    public Long setRemove(String key, String... values) {
        return stringRedisTemplate.opsForSet().remove(key, (Object[]) values);
    }

    /**
     * 设置 List。
     *
     * @param key    key
     * @param values values
     * @return list 长度
     */
    public Long rightPushAll(String key, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return 0L;
        }
        return stringRedisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 获取 List。
     *
     * @param key key
     * @return list
     */
    public List<String> listRangeAll(String key) {
        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * 获取 List。
     *
     * @param key          key
     * @param elementClass 元素类型
     * @param <T>          元素泛型
     * @return list，value == null 时返回 null
     */
    public <T> List<T> getList(String key, Class<T> elementClass) {
        String value = get(key);
        if (value == null) {
            return null;
        }
        return JsonUtils.parseList(value, elementClass);
    }

}
