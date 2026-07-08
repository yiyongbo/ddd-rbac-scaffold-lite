package io.github.yiyongbo.scaffold.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * JSON 工具类
 *
 * @author kidd
 * @since 2026/6/21 15:51
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    private static ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    /**
     * 对象转 JSON 字符串
     *
     * @param object 对象
     * @return JSON 字符串
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw BizAssert.newException(CommonResponseCode.INTERNAL_ERROR, "JSON序列化失败");
        }
    }

    /**
     * JSON 字符串转对象
     *
     * @param json  JSON 字符串
     * @param clazz 目标类型
     * @param <T>   目标泛型
     * @return 目标对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw BizAssert.newException(CommonResponseCode.INTERNAL_ERROR, "JSON反序列化失败");
        }
    }

    /**
     * JSON 字符串转 List
     *
     * @param json         JSON 字符串
     * @param elementClass 元素类型
     * @param <T>          元素泛型
     * @return List 对象
     */
    public static <T> List<T> parseList(String json, Class<T> elementClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass));
        } catch (Exception e) {
            throw BizAssert.newException(CommonResponseCode.INTERNAL_ERROR, "JSON反序列化失败");
        }
    }

}
