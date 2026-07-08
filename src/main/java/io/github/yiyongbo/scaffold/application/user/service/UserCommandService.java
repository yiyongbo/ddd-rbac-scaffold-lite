package io.github.yiyongbo.scaffold.application.user.service;

import io.github.yiyongbo.scaffold.application.user.assembler.UserAppAssembler;
import io.github.yiyongbo.scaffold.application.user.command.UserChangePasswordCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.auth.cache.LoginSessionCache;
import io.github.yiyongbo.scaffold.domain.user.cache.UserPermissionCache;
import io.github.yiyongbo.scaffold.domain.user.gateway.PasswordGateway;
import io.github.yiyongbo.scaffold.domain.role.service.RoleDomainService;
import io.github.yiyongbo.scaffold.domain.user.constants.UserConstants;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import io.github.yiyongbo.scaffold.domain.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户命令服务
 *
 * @author kidd
 * @since 2026/6/14 18:21
 */
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserAppAssembler userAppAssembler;

    private final UserDomainService userDomainService;
    private final RoleDomainService roleDomainService;

    private final UserRepository userRepository;

    private final PasswordGateway passwordGateway;
    private final LoginSessionCache loginSessionCache;
    private final UserPermissionCache userPermissionCache;

    /**
     * 创建用户
     *
     * @param command 创建用户命令
     * @return 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long create(UserCreateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "创建用户命令不能为空");

        UserEntity user = userAppAssembler.toEntity(command);

        userDomainService.validateCreate(user);

        // 设置默认密码
        String encodePassword = passwordGateway.encode(UserConstants.USER_DEFAULT_PASSWORD);
        user.setPassword(encodePassword);

        return userRepository.save(user);
    }

    /**
     * 更新用户
     *
     * @param command 更新用户命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "更新用户命令不能为空");

        UserEntity user = userAppAssembler.toEntity(command);

        userDomainService.validateUpdate(user);

        userRepository.updateById(user);
    }

    /**
     * 重置密码
     *
     * @param id 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        boolean existsUser = userRepository.existsById(id);
        BizAssert.isTrue(existsUser, CommonResponseCode.NOT_FOUND, "用户不存在");

        // 默认密码
        String changedPassword = passwordGateway.encode(UserConstants.USER_DEFAULT_PASSWORD);
        userRepository.updatePasswordById(id, changedPassword);

        // 删除用户登录会话
        loginSessionCache.deleteByUserId(id);
    }

    /**
     * 修改密码
     *
     * @param command 修改密码命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(UserChangePasswordCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "修改密码命令不能为空");

        UserEntity user = userRepository.findById(command.getId())
                .orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "用户不存在"));

        boolean isOldPasswordMatch = passwordGateway.matches(command.getOldPassword(), user.getPassword());
        BizAssert.isTrue(isOldPasswordMatch, CommonResponseCode.USER_ERROR, "原密码错误");

        userDomainService.validateChangePassword(command.getOldPassword(), command.getNewPassword(), command.getConfirmPassword());

        String changedPassword = passwordGateway.encode(command.getNewPassword());

        userRepository.updatePasswordById(command.getId(), changedPassword);

        // 删除用户登录会话
        loginSessionCache.deleteByUserId(user.getId());
    }

    /**
     * 切换用户启用状态
     *
     * @param id 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void toggleEnabled(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "用户不存在"));

        user.toggleEnabled();

        userRepository.updateEnabledById(id, user.getEnabled().getCode());

        if (user.isDisabled()) {
            // 删除用户登录会话
            loginSessionCache.deleteByUserId(user.getId());
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        userDomainService.validateDelete(id);

        userRepository.deleteById(id);

        userRepository.deleteUserRoleByUserId(id);

        // 删除用户登录会话
        loginSessionCache.deleteByUserId(id);
    }

    /**
     * 分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Long> roleIds) {
        BizAssert.notNull(userId, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");
        BizAssert.notNull(roleIds, CommonResponseCode.PARAM_ERROR, "角色ID列表不能为空");

        userDomainService.validateAssignRoles(userId, roleIds);

        roleDomainService.validateRoles(roleIds);

        userRepository.replaceUserRoles(userId, roleIds);

        userPermissionCache.delete(userId);
    }

}
