package io.github.yiyongbo.scaffold.infrastructure.persistence.role.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yiyongbo.scaffold.common.base.BaseRelationPO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色菜单关联持久化对象
 *
 * @author kidd
 * @since 2026/06/11 23:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role_menu")
public class RoleMenuPO extends BaseRelationPO {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}
