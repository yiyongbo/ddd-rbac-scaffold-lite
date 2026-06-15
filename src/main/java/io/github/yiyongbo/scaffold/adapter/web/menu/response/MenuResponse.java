package io.github.yiyongbo.scaffold.adapter.web.menu.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统菜单响应
 *
 * @author kidd
 * @since 2026/6/9 13:58
 */
@Data
@Schema
public class MenuResponse {

    @Schema(description = "菜单ID", example = "1")
    private Long id;

    @Schema(description = "父级菜单ID，0表示根节点", example = "0")
    private Long parentId;

    @Schema(description = "菜单名称", example = "用户管理")
    private String menuName;

    @Schema(description = "菜单类型：1目录，2菜单，3按钮", example = "2")
    private Integer menuType;

    @Schema(description = "路由路径", example = "/system/user")
    private String routePath;

    @Schema(description = "组件路径", example = "system/user/index")
    private String component;

    @Schema(description = "权限标识", example = "system:user:list")
    private String permissionCode;

    @Schema(description = "菜单图标", example = "user")
    private String icon;

    @Schema(description = "排序值，越小越靠前", example = "1")
    private Integer sort;

    @Schema(description = "是否已启用：1是，0否", example = "1")
    private Integer enabled;

    @Schema(description = "备注", example = "用户管理菜单")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
}
