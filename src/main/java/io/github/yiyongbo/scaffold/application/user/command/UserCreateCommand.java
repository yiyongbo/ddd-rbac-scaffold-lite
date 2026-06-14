package io.github.yiyongbo.scaffold.application.user.command;

import lombok.Data;

/**
 * 创建系统用户命令
 *
 * @author kidd
 * @since 2026/6/14 18:22
 */
@Data
public class UserCreateCommand {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String rePassword;

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
