package io.github.yiyongbo.scaffold.domain.menu.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import io.github.yiyongbo.scaffold.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * 系统菜单领域服务
 *
 * @author kidd
 * @since 2026/6/9 09:27
 */
@Service
@RequiredArgsConstructor
public class MenuDomainService {

    private final MenuRepository menuRepository;

    /**
     * 校验创建菜单
     *
     * @param menu 菜单实体
     */
    public void validateCreate(MenuEntity menu) {
        BizAssert.notNull(menu, CommonResponseCode.PARAM_ERROR, "菜单不能为空");

        validateParent(menu);
        validatePermissionCodeUnique(null, menu.getPermissionCode());
    }

    /**
     * 校验更新菜单
     *
     * @param menu 菜单实体
     */
    public void validateUpdate(MenuEntity menu) {
        BizAssert.notNull(menu, CommonResponseCode.PARAM_ERROR, "菜单不能为空");
        BizAssert.notNull(menu.getId(), CommonResponseCode.PARAM_ERROR, "菜单ID不能为空");

        boolean existsMenu = menuRepository.existsById(menu.getId());
        BizAssert.isTrue(existsMenu, CommonResponseCode.NOT_FOUND, "菜单不存在");

        menu.validateParentNotSelf();

        validateParent(menu);
        validatePermissionCodeUnique(menu.getId(), menu.getPermissionCode());
    }

    /**
     * 校验删除菜单
     *
     * @param id 菜单ID
     */
    public void validateDelete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "菜单ID不能为空");

        boolean existsMenu = menuRepository.existsById(id);
        BizAssert.isTrue(existsMenu, CommonResponseCode.NOT_FOUND, "菜单不存在");

        boolean existsChildMenu = menuRepository.existsByParentId(id);
        BizAssert.isTrue(!existsChildMenu, CommonResponseCode.USER_ERROR, "存在子菜单，不能删除");
    }

    private void validateParent(MenuEntity menu) {
        Long parentId = menu.getParentId();
        BizAssert.notNull(parentId, CommonResponseCode.PARAM_ERROR, "父级菜单ID不能为空");

        if (menu.isRootMenu()) {
            return;
        }

        boolean existsParentMenu = menuRepository.existsById(parentId);
        BizAssert.isTrue(existsParentMenu, CommonResponseCode.NOT_FOUND, "父级菜单不存在");
    }

    /**
     * 校验权限标识唯一性。
     * 权限标识为空时不参与唯一性校验。
     */
    private void validatePermissionCodeUnique(Long currentMenuId, String permissionCode) {
        if (StrUtil.isBlank(permissionCode)) {
            return;
        }

        boolean existsSamePermissionCode = menuRepository.findByPermissionCode(permissionCode)
                .filter(menu -> !Objects.equals(menu.getId(), currentMenuId))
                .isPresent();
        BizAssert.isTrue(!existsSamePermissionCode, CommonResponseCode.USER_ERROR, "权限标识已存在");
    }

    /**
     * 校验菜单存在
     *
     * @param menuIds 菜单ID列表
     */
    public void validateMenusExist(List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }

        boolean existsAll = menuRepository.existsAllByIds(menuIds);
        BizAssert.isTrue(existsAll, CommonResponseCode.NOT_FOUND, "存在无效菜单");
    }
}
