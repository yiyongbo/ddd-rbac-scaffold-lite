package io.github.yiyongbo.scaffold.domain.user.service;

import cn.hutool.core.util.StrUtil;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

        validateUsername(null, user.getUsername());
    }

    /**
     * 验证用户更新
     *
      * @param user 系统用户领域实体
     */
    public void validateUpdate(UserEntity user) {
        BizAssert.notNull(user, CommonResponseCode.PARAM_ERROR, "用户信息不能为空");

        boolean existUser = userRepository.existsById(user.getId());
        BizAssert.isTrue(existUser, CommonResponseCode.NOT_FOUND, "用户不存在");

        validateUsername(user.getId(), user.getUsername());
    }

    /**
     * 验证用户名
     *
      * @param id 用户ID
      * @param username 用户名
     */
    private void validateUsername(Long id, String username) {
        BizAssert.isTrue(StrUtil.isNotBlank(username), CommonResponseCode.PARAM_ERROR, "用户名不能为空");

        boolean existsSameUsername = userRepository.findByUsername(username)
                .filter(user -> !Objects.equals(user.getId(), id))
                .isPresent();
        BizAssert.isTrue(!existsSameUsername, CommonResponseCode.USER_ERROR, "用户名已存在");
    }

    /**
     * 验证用户删除
     *
     * @param id 用户ID
     */
    public void validateDelete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        boolean existUser = userRepository.existsById(id);
        BizAssert.isTrue(existUser, CommonResponseCode.NOT_FOUND, "用户不存在");
    }

    /**
     * 验证用户分配角色
     *
     * @param id 用户ID
     * @param roleIds 角色ID列表
     */
    public void validateAssignRoles(Long id, List<Long> roleIds) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        long distinctCount = roleIds.stream().distinct().count();
        BizAssert.isTrue(distinctCount == roleIds.size(), CommonResponseCode.PARAM_ERROR, "角色ID列表不能重复");

        boolean existUser = userRepository.existsById(id);
        BizAssert.isTrue(existUser, CommonResponseCode.NOT_FOUND, "用户不存在");

    }
}
