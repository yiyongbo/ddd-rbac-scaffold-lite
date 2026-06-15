package io.github.yiyongbo.scaffold.domain.user.service;

import cn.hutool.core.util.StrUtil;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户领域服务
 *
 * @author kidd
 * @since 2026/6/14 17:35
 */
@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserRepository userRepository;

    /**
     * 验证用户创建
     *
     * @param user 用户领域实体
     */
    public void validateCreate(UserEntity user) {
        BizAssert.notNull(user, CommonResponseCode.PARAM_ERROR, "用户信息不能为空");
        BizAssert.isTrue(StrUtil.isNotBlank(user.getUsername()), CommonResponseCode.PARAM_ERROR, "用户名不能为空");

        boolean existUser = userRepository.existsByUsername(user.getUsername());
        BizAssert.isTrue(!existUser, CommonResponseCode.USER_ERROR, "用户名已存在");
    }

    /**
     * 验证用户更新
     *
      * @param user 系统用户领域实体
     */
    public void validateUpdate(UserEntity user) {
        BizAssert.notNull(user, CommonResponseCode.PARAM_ERROR, "用户信息不能为空");

        validateUserExists(user.getId());
    }

    public void validateChangePassword(String oldPassword, String newPassword, String confirmPassword) {
        BizAssert.isTrue(StrUtil.isNotBlank(oldPassword), CommonResponseCode.PARAM_ERROR, "旧密码不能为空");
        BizAssert.isTrue(StrUtil.isNotBlank(newPassword), CommonResponseCode.PARAM_ERROR, "新密码不能为空");
        BizAssert.isTrue(StrUtil.isNotBlank(confirmPassword), CommonResponseCode.PARAM_ERROR, "确认密码不能为空");

        BizAssert.isTrue(!newPassword.equals(oldPassword), CommonResponseCode.USER_ERROR, "新密码不能与旧密码相同");
        BizAssert.isTrue(newPassword.equals(confirmPassword), CommonResponseCode.USER_ERROR, "新密码和确认密码不一致");
    }

    /**
     * 验证用户删除
     *
     * @param id 用户ID
     */
    public void validateDelete(Long id) {
        validateUserExists(id);
    }

    /**
     * 验证用户分配角色
     *
     * @param id 用户ID
     * @param roleIds 角色ID列表
     */
    public void validateAssignRoles(Long id, List<Long> roleIds) {
        long distinctCount = roleIds.stream().distinct().count();
        BizAssert.isTrue(distinctCount == roleIds.size(), CommonResponseCode.PARAM_ERROR, "角色ID列表不能重复");

        validateUserExists(id);
    }

    private void validateUserExists(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        boolean existUser = userRepository.existsById(id);
        BizAssert.isTrue(existUser, CommonResponseCode.NOT_FOUND, "用户不存在");
    }

}
