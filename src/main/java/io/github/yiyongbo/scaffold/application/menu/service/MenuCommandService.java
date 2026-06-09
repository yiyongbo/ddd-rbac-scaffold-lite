package io.github.yiyongbo.scaffold.application.menu.service;

import io.github.yiyongbo.scaffold.application.menu.assembler.MenuAssembler;
import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCmd;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCmd;
import io.github.yiyongbo.scaffold.common.exception.BizAssert;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import io.github.yiyongbo.scaffold.domain.menu.repository.MenuRepository;
import io.github.yiyongbo.scaffold.domain.menu.service.MenuDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统菜单命令服务
 *
 * @author kidd
 * @since 2026/6/9 10:49
 */
@Service
@RequiredArgsConstructor
public class MenuCommandService {

    private final MenuRepository menuRepository;

    private final MenuDomainService menuDomainService;

    private final MenuAssembler menuAssembler;

    /**
     * 创建菜单
     *
     * @param cmd 创建菜单命令
     * @return 菜单ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long create(MenuCreateCmd cmd) {
        BizAssert.notNull(cmd, CommonResponseCode.PARAM_ERROR, "创建菜单命令不能为空");

        MenuEntity menu = menuAssembler.toEntity(cmd);

        menuDomainService.validateCreate(menu);

        return menuRepository.save(menu);
    }

    /**
     * 更新菜单
     *
     * @param cmd 更新菜单命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(MenuUpdateCmd cmd) {
        BizAssert.notNull(cmd, CommonResponseCode.PARAM_ERROR, "更新菜单命令不能为空");

        MenuEntity menu = menuAssembler.toEntity(cmd);

        menuDomainService.validateUpdate(menu);

        menuRepository.updateById(menu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "菜单ID不能为空");

        menuDomainService.validateDelete(id);
        menuRepository.deleteById(id);
    }
}
