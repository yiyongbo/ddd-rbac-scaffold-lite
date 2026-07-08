package io.github.yiyongbo.scaffold.common.security;

import io.github.yiyongbo.scaffold.common.exception.BizException;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 当前登录用户获取工具
 *
 * @author kidd
 * @since 2026/6/29 22:49
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SecurityUserHolder {

    /**
     * 获取当前登录用户。
     *
     * @return 当前登录用户
     */
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BizException(CommonResponseCode.UNAUTHORIZED, "当前用户未登录");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof LoginUser loginUser)) {
            throw new BizException(CommonResponseCode.UNAUTHORIZED, "当前用户未登录");
        }

        return loginUser;
    }

    /**
     * 获取当前登录用户ID。
     *
     * @return 当前登录用户ID
     */
    public static Long getUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取当前登录用户名。
     *
     * @return 当前登录用户名
     */
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 尝试获取当前登录用户。
     *
     * <p>未登录时返回 null，适合审计字段自动填充等弱依赖场景。</p>
     *
     * @return 当前登录用户，未登录返回 null
     */
    public static LoginUser getLoginUserOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof LoginUser loginUser)) {
            return null;
        }

        return loginUser;
    }

    /**
     * 尝试获取当前登录用户ID。
     *
     * @return 当前登录用户ID，未登录返回 null
     */
    public static Long getUserIdOrNull() {
        LoginUser loginUser = getLoginUserOrNull();
        return loginUser == null ? null : loginUser.getUserId();
    }
}
