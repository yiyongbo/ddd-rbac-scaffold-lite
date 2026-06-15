package io.github.yiyongbo.scaffold.adapter.web.menu.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单树响应
 *
 * @author kidd
 * @since 2026/6/9 14:00
 */
@Data
@Schema(description = "系统菜单树响应")
public class MenuTreeResponse {

    @Schema(description = "菜单ID", example = "1")
    private Long id;

    @Schema(description = "父级菜单ID，0表示根节点", example = "0")
    private Long parentId;

    @Schema(description = "菜单名称", example = "系统管理")
    private String menuName;

    @Schema(description = "菜单类型：1目录，2菜单，3按钮", example = "1")
    private Integer menuType;

    @Schema(description = "菜单类型描述", example = "目录")
    private String menuTypeDesc;

    @Schema(description = "路由路径", example = "/system")
    private String routePath;

    @Schema(description = "组件路径", example = "Layout")
    private String component;

    @Schema(description = "权限标识", example = "system")
    private String permissionCode;

    @Schema(description = "菜单图标", example = "setting")
    private String icon;

    @Schema(description = "排序值，越小越靠前", example = "1")
    private Integer sort;

    @Schema(description = "是否已启用：1是，0否", example = "1")
    private Integer enabled;

    @Schema(description = "子菜单")
    private List<MenuTreeResponse> children = new ArrayList<>();
}
