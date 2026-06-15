package io.github.yiyongbo.scaffold.application.menu.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统菜单 DTO
 *
 * @author kidd
 * @since 2026/6/9 11:18
 */
@Data
public class MenuDTO {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父级菜单ID，0表示根节点
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型：1目录，2菜单，3按钮
     */
    private Integer menuType;

    /**
     * 菜单类型描述
     */
    private String menuTypeDesc;

    /**
     * 路由路径，例如 /system/user
     */
    private String routePath;

    /**
     * 组件路径，例如 system/user/index
     */
    private String component;

    /**
     * 权限标识，例如 system:user:create
     */
    private String permissionCode;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序值，越小越靠前
     */
    private Integer sort;

    /**
     * 是否已启用：1是，0否
     */
    private Integer enabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
