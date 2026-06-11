package io.github.yiyongbo.scaffold.application.role.service;

import io.github.yiyongbo.scaffold.application.role.assembler.RoleAppAssembler;
import io.github.yiyongbo.scaffold.application.role.command.RoleCreateCommand;
import io.github.yiyongbo.scaffold.application.role.command.RoleUpdateCommand;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.RoleRepository;
import io.github.yiyongbo.scaffold.domain.role.service.RoleDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统角色命令服务
 *
 * @author kidd
 * @since 2026/6/10 19:50
 */
@Service
@RequiredArgsConstructor
public class RoleCommandService {

    private final RoleRepository roleRepository;

    private final RoleDomainService roleDomainService;

    private final RoleAppAssembler roleAppAssembler;

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
     * 删除角色
     *
     * @param id 角色ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "角色ID不能为空");

        roleDomainService.validateDelete(id);
        roleRepository.deleteById(id);
    }

}
