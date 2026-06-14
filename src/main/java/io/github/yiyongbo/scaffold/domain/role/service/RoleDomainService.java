package io.github.yiyongbo.scaffold.domain.role.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 系统角色领域服务
 *
 * @author kidd
 * @since 2026/6/10 20:09
 */
@Service
@RequiredArgsConstructor
public class RoleDomainService {

    private final RoleRepository roleRepository;

    /**
     * 校验创建角色
     *
     * @param role 角色实体
     */
    public void validateCreate(RoleEntity role) {
        BizAssert.notNull(role, CommonResponseCode.PARAM_ERROR, "角色不能为空");

        validateRoleCodeUnique(null, role.getRoleCode());
    }

    /**
     * 校验更新角色
     *
     * @param role 角色实体
     */
    public void validateUpdate(RoleEntity role) {
        BizAssert.notNull(role, CommonResponseCode.PARAM_ERROR, "角色不能为空");
        BizAssert.notNull(role.getId(), CommonResponseCode.PARAM_ERROR, "角色ID不能为空");

        boolean existRole = roleRepository.existsById(role.getId());
        BizAssert.isTrue(existRole, CommonResponseCode.NOT_FOUND, "角色不存在");

        validateRoleCodeUnique(role.getId(), role.getRoleCode());
    }

    /**
     * 校验删除角色
     *
     * @param id 角色ID
     */
    public void validateDelete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "角色ID不能为空");

        boolean existRole = roleRepository.existsById(id);
        BizAssert.isTrue(existRole, CommonResponseCode.NOT_FOUND, "角色不存在");
    }

    /**
     * 校验分配菜单
     *
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     */
    public void validateAssignMenus(Long roleId, List<Long> menuIds) {
        BizAssert.notNull(menuIds, CommonResponseCode.PARAM_ERROR, "菜单ID列表不能为空");

        long distinctCount = menuIds.stream().distinct().count();
        BizAssert.isTrue(distinctCount == menuIds.size(), CommonResponseCode.PARAM_ERROR, "菜单ID不能重复");

        validateRoleExists(roleId);
    }

    /**
     * 校验角色存在
     * @param roleId 角色ID
     */
    public void validateRoleExists(Long roleId) {
        BizAssert.notNull(roleId, CommonResponseCode.PARAM_ERROR, "角色ID不能为空");

        boolean existRole = roleRepository.existsById(roleId);
        BizAssert.isTrue(existRole, CommonResponseCode.NOT_FOUND, "角色不存在");
    }

    private void validateRoleCodeUnique(Long id, String roleCode) {
        BizAssert.isTrue(StrUtil.isNotBlank(roleCode), CommonResponseCode.PARAM_ERROR, "角色标识不能为空");

        boolean existsSameRoleCode = roleRepository.findByRoleCode(roleCode)
                .filter(role -> !Objects.equals(role.getId(), id))
                .isPresent();
        BizAssert.isTrue(!existsSameRoleCode, CommonResponseCode.PARAM_ERROR, "角色标识已存在");
    }


    public void validateRoles(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }

        boolean existsAll = roleRepository.existsAllByIds(roleIds);
        BizAssert.isTrue(existsAll, CommonResponseCode.NOT_FOUND, "存在无效角色");
    }
}
