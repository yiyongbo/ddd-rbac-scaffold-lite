package io.github.yiyongbo.scaffold.domain.auth.model.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 登录会话 valueObject
 *
 * @author kidd
 * @since 2026/7/7 16:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSessionValueObject {

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
     * 登录时间
     */
    private LocalDateTime loginTime;
}
