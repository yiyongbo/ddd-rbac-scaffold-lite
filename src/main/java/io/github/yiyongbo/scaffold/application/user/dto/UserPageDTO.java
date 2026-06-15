package io.github.yiyongbo.scaffold.application.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户分页 DTO
 *
 * @author kidd
 * @since 2026/6/14 22:46
 */
@Data
public class UserPageDTO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 邮箱
     */
    private String nickname;

    /**
     * 手机号
     */
    private String email;

    /**
     * 头像
     */
    private String phone;

    /**
     * 是否已启用：1是，0否
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Integer enabled;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
