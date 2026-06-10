package io.github.yiyongbo.scaffold.infrastructure.persistence.menu.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public void delete(Long id) {
        menuMapper.deleteById(id);
    }

    @Override
    public Optional<MenuEntity> findById(Long id) {
        MenuPO menuPO = menuMapper.selectById(id);
        return Optional.ofNullable(menuPersistenceAssembler.toEntity(menuPO));
    }

    @Override
    public Optional<MenuEntity> findByPermissionCode(String permissionCode) {
        LambdaQueryWrapper<MenuPO> wrapper = new LambdaQueryWrapper<MenuPO>()
                .eq(MenuPO::getPermissionCode, permissionCode);

        MenuPO menuPO = menuMapper.selectOne(wrapper);
        return Optional.ofNullable(menuPersistenceAssembler.toEntity(menuPO));
    }

    @Override
    public List<MenuEntity> findAll() {
        LambdaQueryWrapper<MenuPO> wrapper = new LambdaQueryWrapper<MenuPO>()
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
        return menuMapper.selectById(id) != null;
    }

    @Override
    public boolean existsByParentId(Long parentId) {
        if (parentId == null) {
            return false;
        }

        LambdaQueryWrapper<MenuPO> wrapper = new LambdaQueryWrapper<MenuPO>()
                .eq(MenuPO::getParentId, parentId)
                .last("LIMIT 1");

        return menuMapper.selectOne(wrapper) != null;
    }
}
