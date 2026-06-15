package io.github.yiyongbo.scaffold.domain.menu.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.menu.enums.MenuTypeEnum;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import io.github.yiyongbo.scaffold.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


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
        BizAssert.notNull(menu.getParentId(), CommonResponseCode.PARAM_ERROR, "父级菜单ID不能为空");

        // 校验菜单类型字段
        menu.validateMenuTypeFields();
        // 校验父级菜单
        validateParent(menu);
        // 校验权限标识唯一性
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
        BizAssert.notNull(menu.getParentId(), CommonResponseCode.PARAM_ERROR, "父级菜单ID不能为空");

        // 校验菜单类型字段
        menu.validateMenuTypeFields();

        boolean existsMenu = menuRepository.existsById(menu.getId());
        BizAssert.isTrue(existsMenu, CommonResponseCode.NOT_FOUND, "菜单不存在");

        // 校验父级菜单
        validateParent(menu);
        // 校验权限标识唯一性
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

    private void validateParent(MenuEntity menu) {
        // 校验父级菜单不能是自己
        menu.validateParentNotSelf();

        if (menu.isRootParentMenu()) {
            BizAssert.isTrue(MenuTypeEnum.DIR.equals(menu.getMenuType()), CommonResponseCode.USER_ERROR, "只有根目录可以挂在根节点下");
            return;
        }

        MenuEntity parentMenu = menuRepository.findById(menu.getParentId())
                .orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "父级菜单不存在"));

        switch(menu.getMenuType()) {
            case DIR:
                BizAssert.isTrue(MenuTypeEnum.DIR.equals(parentMenu.getMenuType()), CommonResponseCode.USER_ERROR, "目录的父级只能是根节点或根目录");
                break;
            case MENU:
                BizAssert.isTrue(MenuTypeEnum.DIR.equals(parentMenu.getMenuType()), CommonResponseCode.USER_ERROR, "菜单的父级菜单只能是目录");
                break;
            case BUTTON:
                BizAssert.isTrue(MenuTypeEnum.MENU.equals(parentMenu.getMenuType()), CommonResponseCode.USER_ERROR, "按钮的父级菜单只能是菜单");
                break;
            default:
                throw BizAssert.newException(CommonResponseCode.PARAM_ERROR, "菜单类型不合法");
        }

        // 校验父级菜单不能是自己的子菜单
        validateParentNotDescendant(menu);
    }

    private void validateParentNotDescendant(MenuEntity menu) {
        if (menu.getId() == null || menu.isRootParentMenu()) {
            return;
        }

        List<MenuEntity> allMenus = menuRepository.findAll();

        Map<Long, List<MenuEntity>> parentMap = allMenus.stream().collect(Collectors.groupingBy(MenuEntity::getParentId));

        boolean descendant = isDescendant(menu.getId(), menu.getParentId(), parentMap);
        BizAssert.isTrue(!descendant, CommonResponseCode.USER_ERROR, "上级菜单不能选择自己的子孙菜单");
    }

    private boolean isDescendant(Long currentMenuId, Long targetParentId, Map<Long, List<MenuEntity>> parentMap) {
        List<MenuEntity> children = parentMap.getOrDefault(currentMenuId, Collections.emptyList());

        for (MenuEntity child : children) {
            if (child.getId().equals(targetParentId)) {
                return true;
            }

            if (isDescendant(child.getId(), targetParentId, parentMap)) {
                return true;
            }
        }

        return false;
    }

    private void validatePermissionCodeUnique(Long currentMenuId, String permissionCode) {
        if (StrUtil.isBlank(permissionCode)) {
            return;
        }

        boolean existsSamePermissionCode = menuRepository.findByPermissionCode(permissionCode)
                .filter(menu -> !Objects.equals(menu.getId(), currentMenuId))
                .isPresent();
        BizAssert.isTrue(!existsSamePermissionCode, CommonResponseCode.USER_ERROR, "权限标识已存在");
    }

}
