package io.github.yiyongbo.scaffold.infrastructure.persistence.user.po;

import io.github.yiyongbo.scaffold.common.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户持久化对象
 *
 * @author kidd
 * @since 2026/6/14 17:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPO extends BasePO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否已启用：1是，0否
     */
    private Integer enabled;
}
