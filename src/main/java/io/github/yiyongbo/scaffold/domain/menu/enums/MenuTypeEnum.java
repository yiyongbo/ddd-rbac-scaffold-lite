package io.github.yiyongbo.scaffold.domain.menu.enums;

import io.github.yiyongbo.scaffold.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 *
 * @author kidd
 * @since 2026/6/8 22:59
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements BaseEnum<Integer> {

    DIR(1, "目录"),

    MENU(2, "菜单"),

    BUTTON(3, "按钮");

    private final Integer code;
    private final String desc;

}
