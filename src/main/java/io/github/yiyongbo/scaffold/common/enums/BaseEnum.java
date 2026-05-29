package io.github.yiyongbo.scaffold.common.enums;

/**
 * 基础枚举接口
 *
 * @author kidd
 * @since 2026/5/29 17:50
 */
public interface BaseEnum<C> {

    /**
     * 获取枚举编码
     */
    C getCode();

    /**
     * 获取枚举名称
     */
    String getName();

}
