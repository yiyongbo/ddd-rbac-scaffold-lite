package io.github.yiyongbo.scaffold.application.role.service;

import io.github.yiyongbo.scaffold.application.role.assembler.RoleAppAssembler;
import io.github.yiyongbo.scaffold.application.role.command.RoleCreateCommand;
import io.github.yiyongbo.scaffold.application.role.command.RoleUpdateCommand;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.menu.service.MenuDomainService;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.RoleRepository;
import io.github.yiyongbo.scaffold.domain.role.service.RoleDomainService;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统角色命令服务
 *
 * @author kidd
 * @since 2026/6/10 19:50
 */
@Service
@RequiredArgsConstructor
public class RoleCommandService {

    private final RoleAppAssembler roleAppAssembler;

    private final RoleDomainService roleDomainService;
    private final MenuDomainService menuDomainService;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    /**
     * 创建角色
     *
     * @param command 创建角色命令
     * @return 角色ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long create(RoleCreateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "创建角色命令不能为空");

        RoleEntity role = roleAppAssembler.toEntity(command);

        roleDomainService.validateCreate(role);

        return roleRepository.save(role);
    }

    /**
     * 更新角色
     *
     * @param command 更新角色命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleUpdateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "更新角色命令不能为空");

        RoleEntity role = roleAppAssembler.toEntity(command);

        roleDomainService.validateUpdate(role);

        roleRepository.updateById(role);
    }

    /**
     * 切换角色启用状态
     *
     * @param id 角色ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void toggleEnabled(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "角色ID不能为空");

        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "角色不存在"));

        role.toggleEnabled();

        roleRepository.updateEnabledById(id, role.getEnabled().getCode());
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "角色ID不能为空");

        roleDomainService.validateDelete(id);

        roleRepository.deleteById(id);

        roleRepository.deleteRoleMenuByRoleId(id);

        userRepository.deleteUserRoleByRoleId(id);
    }

    /**
     * 分配菜单
     *
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void assignMenus(Long roleId, List<Long> menuIds) {
        BizAssert.notNull(roleId, CommonResponseCode.PARAM_ERROR, "角色ID不能为空");
        BizAssert.notNull(menuIds, CommonResponseCode.PARAM_ERROR, "菜单ID列表不能为空");

        roleDomainService.validateAssignMenus(roleId, menuIds);

        menuDomainService.validateMenusExist(menuIds);

        roleRepository.replaceRoleMenus(roleId, menuIds);
    }

}
