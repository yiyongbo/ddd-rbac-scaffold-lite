package io.github.yiyongbo.scaffold.infrastructure.persistence.menu.repository;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import io.github.yiyongbo.scaffold.domain.menu.repository.MenuRepository;
import io.github.yiyongbo.scaffold.infrastructure.persistence.menu.assembler.MenuPersistenceAssembler;
import io.github.yiyongbo.scaffold.infrastructure.persistence.menu.mapper.MenuMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.menu.po.MenuPO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 系统菜单仓储实现
 *
 * @author kidd
 * @since 2026/6/8 23:34
 */
@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuMapper menuMapper;

    private final MenuPersistenceAssembler menuPersistenceAssembler;

    @Override
    public Long save(MenuEntity menu) {
        MenuPO menuPO = menuPersistenceAssembler.toPO(menu);
        menuMapper.insert(menuPO);
        return menuPO.getId();
    }

    @Override
    public void update(MenuEntity menu) {
        MenuPO menuPO = menuPersistenceAssembler.toPO(menu);
        menuMapper.updateById(menuPO);
    }

    @Override
    public void updateEnabledById(Long id, Integer changeEnabled) {
        menuMapper.update(
                Wrappers.lambdaUpdate(MenuPO.class).set(MenuPO::getEnabled, changeEnabled).eq(MenuPO::getId, id)
        );
    }

    @Override
    public void delete(Long id) {
        menuMapper.deleteById(id);
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        MenuPO menuPO = menuMapper.selectById(id);
        return Optional.ofNullable(menuPersistenceAssembler.toEntity(menuPO));
    }

    @Override
    public Optional<MenuEntity> findByPermissionCode(String permissionCode) {
        if (permissionCode == null) {
            return Optional.empty();
        }

        MenuPO menuPO = menuMapper.selectOne(
                Wrappers.lambdaQuery(MenuPO.class).eq(MenuPO::getPermissionCode, permissionCode)
        );
        return Optional.ofNullable(menuPersistenceAssembler.toEntity(menuPO));
    }

    @Override
    public List<MenuEntity> findAll() {
        LambdaQueryWrapper<MenuPO> wrapper = Wrappers.lambdaQuery(MenuPO.class)
                .orderByAsc(MenuPO::getSort)
                .orderByAsc(MenuPO::getId);

        return menuMapper.selectList(wrapper)
                .stream()
                .map(menuPersistenceAssembler::toEntity)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }

        Long count = menuMapper.selectCount(
                Wrappers.lambdaQuery(MenuPO.class).eq(MenuPO::getId, id)
        );
        return count != null && count > 0;
    }

    @Override
    public boolean existsByParentId(Long parentId) {
        if (parentId == null) {
            return false;
        }

        Long count = menuMapper.selectCount(
                Wrappers.lambdaQuery(MenuPO.class).eq(MenuPO::getParentId, parentId)
        );

        return count != null && count > 0;
    }

    @Override
    public boolean existsAllByIds(List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return true;
        }

        Long count = menuMapper.selectCount(
                Wrappers.lambdaQuery(MenuPO.class).in(MenuPO::getId, menuIds)
        );

        return count != null && count == menuIds.size();
    }
}
