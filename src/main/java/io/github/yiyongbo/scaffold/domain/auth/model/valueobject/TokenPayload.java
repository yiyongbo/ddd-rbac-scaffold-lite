package io.github.yiyongbo.scaffold.domain.auth.model.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 令牌 Payload
 *
 * @author kidd
 * @since 2026/6/16 23:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenPayload {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;
}
