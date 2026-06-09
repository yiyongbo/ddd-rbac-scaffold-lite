package io.github.yiyongbo.scaffold.application.menu.service;

import cn.hutool.core.collection.CollUtil;
import io.github.yiyongbo.scaffold.application.menu.assembler.MenuAppAssembler;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuDTO;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuTreeDTO;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.menu.constants.MenuConstants;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import io.github.yiyongbo.scaffold.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统菜单查询服务
 *
 * @author kidd
 * @since 2026/6/9 11:26
 */
@Service
@RequiredArgsConstructor
public class MenuQueryService {

    private final MenuRepository menuRepository;

    private final MenuAppAssembler menuAppAssembler;

    /**
     * 根据ID查询菜单详情
     *
     * @param id 菜单ID
     * @return 菜单详情
     */
    @Transactional(readOnly = true)
    public MenuDTO getById(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "菜单ID不能为空");

        MenuEntity menu = menuRepository.findById(id)
                .orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "菜单不存在"));

        return menuAppAssembler.toDTO(menu);
    }

    /**
     * 查询菜单树
     *
     * @return 菜单树
     */
    @Transactional(readOnly = true)
    public List<MenuTreeDTO> tree() {
        List<MenuEntity> menuList = menuRepository.findAll();

        List<MenuTreeDTO> treeNodeList = menuList.stream()
                .map(menuAppAssembler::toTreeDTO)
                .sorted(Comparator.comparing(MenuTreeDTO::getSort, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(MenuTreeDTO::getId, Comparator.nullsLast(Long::compareTo)))
                .toList();

        return buildTree(treeNodeList);
    }

    private List<MenuTreeDTO> buildTree(List<MenuTreeDTO> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return List.of();
        }

        Map<Long, List<MenuTreeDTO>> parentIdMap = menuList.stream().collect(Collectors.groupingBy(MenuTreeDTO::getParentId));

        List<MenuTreeDTO> rootMenuList = parentIdMap.get(MenuConstants.ROOT_PARENT_ID);
        if (CollUtil.isEmpty(rootMenuList)) {
            return List.of();
        }

        rootMenuList.forEach(root -> fillChildren(root, parentIdMap));

        menuList.forEach(menu -> {
            List<MenuTreeDTO> children = parentIdMap.get(menu.getId());
            if (children != null) {
                menu.setChildren(children);
            }
        });

        return Optional.ofNullable(parentIdMap.get(MenuConstants.ROOT_PARENT_ID)).orElse(List.of());
    }

    private void fillChildren(MenuTreeDTO parent, Map<Long, List<MenuTreeDTO>> parentIdMap) {
        List<MenuTreeDTO> children = parentIdMap.get(parent.getId());

        if  (CollUtil.isEmpty(children)) {
            return;
        }

        parent.setChildren(children);

        children.forEach(child -> fillChildren(child, parentIdMap));
    }

}
