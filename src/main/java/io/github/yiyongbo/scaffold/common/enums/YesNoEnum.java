package io.github.yiyongbo.scaffold.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否枚举
 *
 * @author kidd
 * @since 2026/5/29 23:27
 */
@Getter
@AllArgsConstructor
public enum YesNoEnum implements BaseEnum<Integer> {

    YES(1, "是"),
    NO(0, "否");

    private final Integer code;
    private final String desc;
}
