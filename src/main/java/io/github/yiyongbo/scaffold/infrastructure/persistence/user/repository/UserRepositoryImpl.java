package io.github.yiyongbo.scaffold.infrastructure.persistence.user.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import io.github.yiyongbo.scaffold.common.util.BatchUtils;
import io.github.yiyongbo.scaffold.domain.user.model.entity.UserEntity;
import io.github.yiyongbo.scaffold.domain.user.repository.UserRepository;
import io.github.yiyongbo.scaffold.domain.user.repository.query.UserPageCondition;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.assembler.UserPersistenceAssembler;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.mapper.UserMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.mapper.UserRoleMapper;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.po.UserPO;
import io.github.yiyongbo.scaffold.infrastructure.persistence.user.po.UserRolePO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 系统用户仓储实现
 *
 * @author kidd
 * @since 2026/6/14 17:34
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceAssembler userPersistenceAssembler;

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Override
    public boolean existsByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return false;
        }

        Long count = userMapper.selectCount(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getUsername, username));

        return count != null && count > 0;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return Optional.empty();
        }

        UserPO userPO = userMapper.selectOne(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getUsername, username));

        return Optional.ofNullable(userPO).map(userPersistenceAssembler::toEntity);
    }

    @Override
    public Long save(UserEntity user) {
        UserPO userPO = userPersistenceAssembler.toPO(user);
        userMapper.insert(userPO);
        return userPO.getId();
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }

        Long count = userMapper.selectCount(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getId, id));

        return count != null && count > 0;
    }

    @Override
    public void updateById(UserEntity user) {
        UserPO userPO = userPersistenceAssembler.toPO(user);
        userMapper.updateById(userPO);
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void replaceUserRoles(Long userId, List<Long> roleIds) {
        // 删除用户关联的角色
        userRoleMapper.delete(Wrappers.lambdaQuery(UserRolePO.class).eq(UserRolePO::getUserId, userId));

        if (CollUtil.isEmpty(roleIds)) {
            return;
        }

        // 添加用户关联的角色
        LocalDateTime now = LocalDateTime.now();
        BatchUtils.execute(roleIds, roleIdList -> userRoleMapper.insertBatch(userId, roleIdList, now));
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        UserPO userPO = userMapper.selectById(id);
        return Optional.ofNullable(userPO).map(userPersistenceAssembler::toEntity);
    }

    @Override
    public PageResult<UserEntity> page(UserPageCondition condition) {
        Page<UserPO> page = new Page<>(condition.getPageNo(), condition.getPageSize());

        LambdaQueryWrapper<UserPO> queryWrapper = Wrappers.lambdaQuery(UserPO.class)
                .eq(UserPO::getUsername, condition.getUsername())
                .orderByAsc(UserPO::getId);

        Page<UserPO> resultPage = userMapper.selectPage(page, queryWrapper);

        return userPersistenceAssembler.toEntityPageResult(resultPage);
    }

    @Override
    public List<Long> listRoleIds(Long userId) {
        if (userId == null) {
            return List.of();
        }

        List<UserRolePO> userRoles = userRoleMapper.selectList(Wrappers.lambdaQuery(UserRolePO.class).eq(UserRolePO::getUserId, userId));
        return userRoles.stream().map(UserRolePO::getRoleId).toList();
    }

    @Override
    public void deleteUserRoleByRoleId(Long roleId) {
        userRoleMapper.delete(
                Wrappers.lambdaQuery(UserRolePO.class).eq(UserRolePO::getRoleId, roleId)
        );
    }

    @Override
    public void deleteUserRoleByUserId(Long userId) {
        userRoleMapper.delete(
                Wrappers.lambdaQuery(UserRolePO.class).eq(UserRolePO::getUserId, userId)
        );
    }

    @Override
    public void updatePasswordById(Long id, String changedPassword) {
        userMapper.update(
                Wrappers.lambdaUpdate(UserPO.class).set(UserPO::getPassword, changedPassword).eq(UserPO::getId, id)
        );
    }

    @Override
    public void updateEnabledById(Long id, Integer enabled) {
        userMapper.update(
                Wrappers.lambdaUpdate(UserPO.class).set(UserPO::getEnabled, enabled).eq(UserPO::getId, id)
        );
    }

    @Override
    public List<Long> listUserIdsByRoleId(Long roleId) {
        List<UserRolePO> userRoles = userRoleMapper.selectList(
                Wrappers.lambdaQuery(UserRolePO.class).eq(UserRolePO::getRoleId, roleId)
        );

        return userRoles.stream().map(UserRolePO::getUserId).toList();
    }
}
