package io.github.yiyongbo.scaffold.application.auth.command;

import lombok.Data;

/**
 * 登录命令
 *
 * @author kidd
 * @since 2026/6/16 22:57
 */
@Data
public class LoginCommand {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
