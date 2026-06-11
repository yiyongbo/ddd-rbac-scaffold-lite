package io.github.yiyongbo.scaffold.infrastructure.persistence.role.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.yiyongbo.scaffold.common.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色持久化对象
 *
 * @author kidd
 * @since 2026/6/9 22:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_role")
public class RolePO extends BasePO {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否已启用：1是，0否
     */
    private Integer enabled;

    /**
     * 排序值，值越小越靠前
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;
}
