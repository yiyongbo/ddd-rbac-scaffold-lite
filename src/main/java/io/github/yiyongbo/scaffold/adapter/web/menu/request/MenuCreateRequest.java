package io.github.yiyongbo.scaffold.adapter.web.menu.request;

import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.validation.EnumValue;
import io.github.yiyongbo.scaffold.domain.menu.enums.MenuTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建系统菜单请求
 *
 * @author kidd
 * @since 2026/6/9 13:44
 */
@Data
@Schema(description = "创建系统菜单请求")
public class MenuCreateRequest {

    @NotNull(message = "父级菜单ID不能为空")
    @Schema(description = "父级菜单ID，0表示根节点", example = "0")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 64, message = "菜单名称长度不能超过64")
    @Schema(description = "菜单名称", example = "用户管理")
    private String menuName;

    @NotNull(message = "菜单类型不能为空")
    @EnumValue(enumClass = MenuTypeEnum.class, message = "菜单类型不合法")
    @Schema(description = "菜单类型：1目录，2菜单，3按钮", example = "2")
    private Integer menuType;

    @Size(max = 255, message = "路由路径长度不能超过255")
    @Schema(description = "路由路径，例如 /system/user", example = "/system/user")
    private String routePath;

    @Size(max = 255, message = "组件路径长度不能超过255")
    @Schema(description = "组件路径，例如 system/user/index", example = "system/user/index")
    private String component;

    @Size(max = 128, message = "权限标识长度不能超过128")
    @Schema(description = "权限标识，例如 system:user:create", example = "system:user:list")
    private String permissionCode;

    @Size(max = 64, message = "菜单图标长度不能超过64")
    @Schema(description = "菜单图标", example = "user")
    private String icon;

    @NotNull(message = "排序值不能为空")
    @Schema(description = "排序值，越小越靠前", example = "1")
    private Integer sort;

    @NotNull(message = "是否可见不能为空")
    @EnumValue(enumClass = YesNoEnum.class, message = "是否可见不合法")
    @Schema(description = "是否可见：1是，0否", example = "1")
    private Integer visible;

    @NotNull(message = "状态不能为空")
    @EnumValue(enumClass = YesNoEnum.class, message = "是否已启用不合法")
    @Schema(description = "是否已启用：1是，0否", example = "1")
    private Integer enabled;

    @Size(max = 255, message = "备注长度不能超过255")
    @Schema(description = "备注", example = "用户管理菜单")
    private String remark;
}
