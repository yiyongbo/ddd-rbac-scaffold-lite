package io.github.yiyongbo.scaffold.domain.role.repository.query;

import io.github.yiyongbo.scaffold.common.page.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色分页条件
 *
 * @author kidd
 * @since 2026/6/11 00:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageCondition extends PageParam {

    /**
     * 角色名称
     */
    private String roleName;
}
