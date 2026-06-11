package io.github.yiyongbo.scaffold.infrastructure.persistence.role.repository;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.domain.role.model.entity.RoleEntity;
import io.github.yiyongbo.scaffold.domain.role.repository.RoleRepository;
import io.github.yiyongbo.scaffold.domain.role.repository.query.RolePageCondition;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.assembler.RolePersistenceAssembler;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.mapper.RoleMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.role.po.RolePO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    private final RolePersistenceAssembler rolePersistenceAssembler;

    @Override
    public Long save(RoleEntity role) {
        RolePO rolePO = rolePersistenceAssembler.toPO(role);
        roleMapper.insert(rolePO);
        return rolePO.getId();
    }

    @Override
    public void updateById(RoleEntity role) {
        RolePO rolePO = rolePersistenceAssembler.toPO(role);
        roleMapper.updateById(rolePO);
    }

    @Override
    public void deleteById(Long id) {
        roleMapper.deleteById(id);
    }

    @Override
    public Optional<RoleEntity> findByRoleCode(String roleCode) {
        if (StrUtil.isBlank(roleCode)) {
            return Optional.empty();
        }

        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<RolePO>()
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
                new LambdaQueryWrapper<RolePO>().eq(RolePO::getId, id)
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

        LambdaQueryWrapper<RolePO> queryWrapper = new LambdaQueryWrapper<RolePO>()
                .like(StrUtil.isNotBlank(condition.getRoleName()), RolePO::getRoleName, condition.getRoleName())
                .orderByAsc(RolePO::getSort, RolePO::getId);

        Page<RolePO> resultPage = roleMapper.selectPage(page, queryWrapper);

        return rolePersistenceAssembler.toEntityPageResult(resultPage);
    }
}
