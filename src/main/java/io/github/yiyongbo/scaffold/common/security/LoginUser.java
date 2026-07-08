package io.github.yiyongbo.scaffold.common.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录用户
 *
 * @author kidd
 * @since 2026/6/21 15:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {

    /**
     * Token 唯一标识
     */
    private String jti;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 权限标识列表
     */
    @Builder.Default
    private List<String> permissions = List.of();
}
