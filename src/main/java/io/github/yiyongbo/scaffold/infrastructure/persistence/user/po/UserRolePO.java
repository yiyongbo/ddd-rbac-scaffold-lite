package io.github.yiyongbo.scaffold.infrastructure.persistence.user.po;

import io.github.yiyongbo.scaffold.common.base.BaseRelationPO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色持久化对象
 *
 * @author kidd
 * @since 2026/6/14 17:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRolePO extends BaseRelationPO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
