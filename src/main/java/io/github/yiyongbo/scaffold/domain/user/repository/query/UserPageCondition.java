package io.github.yiyongbo.scaffold.domain.user.repository.query;

import io.github.yiyongbo.scaffold.common.page.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户分页条件
 *
 * @author kidd
 * @since 2026/6/14 22:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageCondition extends PageParam {

    /**
     * 用户名称
     */
    private String username;
}
