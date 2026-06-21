package io.github.yiyongbo.scaffold.domain.menu.model.entity;

import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.menu.constants.MenuConstants;
import io.github.yiyongbo.scaffold.domain.menu.enums.MenuTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 系统菜单领域实体
 *
 * @author kidd
 * @since 2026/6/8 23:28
 */
@Data
public class MenuEntity {

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
    private MenuTypeEnum menuType;

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
    private YesNoEnum enabled;

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

    public void validateMenuTypeFields() {
        BizAssert.notNull(menuType, CommonResponseCode.PARAM_ERROR, "菜单类型不能为空");

        switch (menuType) {
            case DIR:
                validateDirectoryFields(permissionCode);
                break;
            case MENU:
                validateMenuFields(routePath, component);
                break;
            case BUTTON:
                validateButtonFields(routePath, component, permissionCode);
                break;
            default:
                throw BizAssert.newException(CommonResponseCode.PARAM_ERROR, "菜单类型不合法");
        }
    }

    public boolean isRootParentMenu() {
        return MenuConstants.ROOT_PARENT_ID.equals(this.parentId);
    }

    public void validateParentNotSelf() {
        BizAssert.isTrue(!Objects.equals(this.id, this.parentId), CommonResponseCode.USER_ERROR, "父级菜单不能是当前菜单");
    }

    public void toggleEnabled() {
        BizAssert.notNull(this.enabled, CommonResponseCode.INTERNAL_ERROR, "菜单启用状态不能为空");

        this.enabled = YesNoEnum.YES.equals(this.enabled) ? YesNoEnum.NO : YesNoEnum.YES;
    }

    private void validateDirectoryFields(String permissionCode) {
        BizAssert.isBlank(permissionCode, CommonResponseCode.PARAM_ERROR, "目录类型菜单不能配置权限标识");
    }

    private void validateMenuFields(String routePath, String component) {
        BizAssert.isNotBlank(routePath, CommonResponseCode.PARAM_ERROR, "菜单类型必须配置路由地址");
        BizAssert.isNotBlank(component, CommonResponseCode.PARAM_ERROR, "菜单类型必须配置组件路径");
    }

    private void validateButtonFields(String routePath, String component, String permissionCode) {
        BizAssert.isBlank(routePath, CommonResponseCode.PARAM_ERROR, "按钮类型不能配置路由地址");
        BizAssert.isBlank(component, CommonResponseCode.PARAM_ERROR, "按钮类型不能配置组件路径");
        BizAssert.isNotBlank(permissionCode, CommonResponseCode.PARAM_ERROR, "按钮类型必须配置权限标识");
    }

}
