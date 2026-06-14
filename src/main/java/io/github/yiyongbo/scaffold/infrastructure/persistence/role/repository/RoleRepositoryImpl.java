package io.github.yiyongbo.scaffold.infrastructure.persistence.role.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.common.util.BatchUtils;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.RoleRepository;
import io.github.yiyongbo.scaffold.domain.role.repository.query.RolePageCondition;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.assembler.RolePersistenceAssembler;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.mapper.RoleMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.mapper.RoleMenuMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.po.RoleMenuPO;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.po.RolePO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 系统角色仓储实现
 *
 * @author kidd
 * @since 2026/6/10 20:11
 */
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleMapper roleMapper;
    private final RoleMenuMapper roleMenuMapper;

    private final RolePersistenceAssembler rolePersistenceAssembler;

    @Override
    public Long save(RoleEntity role) {
        if (role == null) {
            return null;
        }

        RolePO rolePO = rolePersistenceAssembler.toPO(role);
        roleMapper.insert(rolePO);
        return rolePO.getId();
    }

    @Override
    public void updateById(RoleEntity role) {
        if (role == null || role.getId() == null) {
            return;
        }

        RolePO rolePO = rolePersistenceAssembler.toPO(role);
        roleMapper.updateById(rolePO);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }

        roleMapper.deleteById(id);
    }

    @Override
    public Optional<RoleEntity> findByRoleCode(String roleCode) {
        if (StrUtil.isBlank(roleCode)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class)
                .eq(RolePO::getRoleCode, roleCode);

        RolePO rolePO = roleMapper.selectOne(queryWrapper);
        return Optional.ofNullable(rolePersistenceAssembler.toEntity(rolePO));
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }

        Long count = roleMapper.selectCount(
                Wrappers.lambdaQuery(RolePO.class).eq(RolePO::getId, id)
        );
        return count != null && count > 0;
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        RolePO rolePO = roleMapper.selectById(id);
        return Optional.ofNullable(rolePersistenceAssembler.toEntity(rolePO));
    }

    @Override
    public PageResult<RoleEntity> page(RolePageCondition condition) {
        Page<RolePO> page = new Page<>(condition.getPageNo(), condition.getPageSize());

        LambdaQueryWrapper<RolePO> queryWrapper = Wrappers.lambdaQuery(RolePO.class)
                .like(StrUtil.isNotBlank(condition.getRoleName()), RolePO::getRoleName, condition.getRoleName())
                .orderByAsc(RolePO::getSort)
                .orderByAsc(RolePO::getId);

        Page<RolePO> resultPage = roleMapper.selectPage(page, queryWrapper);

        return rolePersistenceAssembler.toEntityPageResult(resultPage);
    }

    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        if (roleId == null) {
            return List.of();
        }
        List<RoleMenuPO> roleMenus = roleMenuMapper.selectList(Wrappers.lambdaQuery(RoleMenuPO.class).eq(RoleMenuPO::getRoleId, roleId));
        return roleMenus.stream().map(RoleMenuPO::getMenuId).toList();
    }

    @Override
    public void replaceRoleMenus(Long roleId, List<Long> menuIds) {
        if (roleId == null) {
            return;
        }

        // 删除角色关联的菜单
        roleMenuMapper.delete(Wrappers.lambdaQuery(RoleMenuPO.class).eq(RoleMenuPO::getRoleId, roleId));

        if (CollUtil.isEmpty(menuIds)) {
            return;
        }

        // 添加角色关联的菜单
        LocalDateTime now = LocalDateTime.now();
        BatchUtils.execute(menuIds, menuIdList -> roleMenuMapper.insertBatch(roleId, menuIdList, now));
    }

    @Override
    public boolean existsAllByIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return true;
        }

        Long count = roleMapper.selectCount(
                Wrappers.lambdaQuery(RolePO.class).in(RolePO::getId, roleIds)
        );

        return count != null && count == roleIds.size();
    }
}
