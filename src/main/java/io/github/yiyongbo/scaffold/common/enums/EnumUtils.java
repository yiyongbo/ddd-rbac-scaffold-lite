package io.github.yiyongbo.scaffold.common.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 枚举工具类
 *
 * @author kidd
 * @since 2026/5/29 18:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnumUtils {

    /**
     * 根据编码获取枚举
     */
    static <C, E extends Enum<E> & BaseEnum<C>> E getByCode(Class<E> enumClass, C code) {
        if (enumClass == null || code == null) {
            return null;
        }

        E[] enumConstants = enumClass.getEnumConstants();
        if (enumConstants == null) {
            return null;
        }

        for (E item : enumConstants) {
            if (Objects.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 根据编码获取枚举名称
     */
    static <C, E extends Enum<E> & BaseEnum<C>> String getNameByCode(Class<E> enumClass, C code) {
        E item = getByCode(enumClass, code);
        return item == null ? null : item.getName();
    }

    /**
     * 判断编码是否合法
     */
    static <C, E extends Enum<E> & BaseEnum<C>> boolean isValidCode(Class<E> enumClass, C code) {
        return getByCode(enumClass, code) != null;
    }
}
