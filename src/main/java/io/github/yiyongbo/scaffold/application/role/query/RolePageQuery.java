package io.github.yiyongbo.scaffold.application.role.query;

import io.github.yiyongbo.scaffold.common.page.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色分页查询
 *
 * @author kidd
 * @since 2026/6/10 19:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RolePageQuery extends PageParam {

    /**
     * 角色名称
     */
    private String roleName;

}
