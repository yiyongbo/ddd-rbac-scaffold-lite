package io.github.yiyongbo.scaffold.application.user.query;

import io.github.yiyongbo.scaffold.common.page.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户查询
 *
 * @author kidd
 * @since 2026/6/14 22:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageQuery extends PageParam {

    /**
     * 用户名
     */
    private String username;
}
