package io.github.yiyongbo.scaffold.application.user.command;

import lombok.Data;

/**
 * 更新系统用户命令
 *
 * @author kidd
 * @since 2026/6/14 18:23
 */
@Data
public class UserUpdateCommand {

    /**
     * 用户ID
     */
    private Long id;

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
