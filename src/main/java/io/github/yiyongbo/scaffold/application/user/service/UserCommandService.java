package io.github.yiyongbo.scaffold.application.user.service;

import io.github.yiyongbo.scaffold.application.user.assembler.UserAppAssembler;
import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.role.service.RoleDomainService;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import io.github.yiyongbo.scaffold.domain.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    /**
     * 创建用户
     *
     * @param command 创建用户命令
     * @return 用户ID
     */
    public Long create(UserCreateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "创建用户命令不能为空");
        BizAssert.isTrue(Objects.equals(command.getPassword(), command.getRePassword()), CommonResponseCode.PARAM_ERROR, "两次密码不一致");

        UserEntity user = userAppAssembler.toEntity(command);

        userDomainService.validateCreate(user);

        return userRepository.save(user);
    }

    /**
     * 更新用户
     *
     * @param command 更新用户命令
     */
    public void update(UserUpdateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "更新用户命令不能为空");

        UserEntity user = userAppAssembler.toEntity(command);

        userDomainService.validateUpdate(user);

        userRepository.updateById(user);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    public void delete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");

        userDomainService.validateDelete(id);

        userRepository.deleteById(id);
    }

    /**
     * 分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    public void assignRoles(Long userId, List<Long> roleIds) {
        BizAssert.notNull(userId, CommonResponseCode.PARAM_ERROR, "用户ID不能为空");
        BizAssert.notNull(roleIds, CommonResponseCode.PARAM_ERROR, "角色ID列表不能为空");

        userDomainService.validateAssignRoles(userId, roleIds);

        roleDomainService.validateRoles(roleIds);

        userRepository.replaceUserRoles(userId, roleIds);
    }
}
