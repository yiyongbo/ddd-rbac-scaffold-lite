package io.github.yiyongbo.scaffold.domain.user.model.entity;

import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户领域实体
 *
 * @author kidd
 * @since 2026/6/14 17:32
 */
@Data
public class UserEntity {

    /**
     * 用户ID
     */
    private Long id;

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
    private YesNoEnum enabled;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    public void toggleEnabled() {
        BizAssert.notNull(this.enabled, CommonResponseCode.USER_ERROR, "用户启用状态不能为空");

        this.enabled = YesNoEnum.YES.equals(this.enabled) ? YesNoEnum.NO : YesNoEnum.YES;
    }
}
