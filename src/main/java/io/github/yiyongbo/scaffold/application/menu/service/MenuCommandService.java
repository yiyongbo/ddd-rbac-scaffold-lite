package io.github.yiyongbo.scaffold.application.menu.service;

import io.github.yiyongbo.scaffold.application.menu.assembler.MenuAppAssembler;
import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCommand;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCommand;
import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
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

    private final MenuAppAssembler menuAppAssembler;

    /**
     * 创建菜单
     *
     * @param command 创建菜单命令
     * @return 菜单ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Long create(MenuCreateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "创建菜单命令不能为空");

        MenuEntity menu = menuAppAssembler.toEntity(command);

        menuDomainService.validateCreate(menu);

        return menuRepository.save(menu);
    }

    /**
     * 更新菜单
     *
     * @param command 更新菜单命令
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(MenuUpdateCommand command) {
        BizAssert.notNull(command, CommonResponseCode.PARAM_ERROR, "更新菜单命令不能为空");

        MenuEntity menu = menuAppAssembler.toEntity(command);

        menuDomainService.validateUpdate(menu);

        menuRepository.update(menu);
    }

    /**
     * 切换菜单启用状态
     *
     * @param id 菜单ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void toggleEnabled(Long id) {
        BizAssert.notNull(id, CommonResponseCode.PARAM_ERROR, "菜单ID不能为空");

        MenuEntity menu = menuRepository.findById(id).orElseThrow(() -> BizAssert.newException(CommonResponseCode.NOT_FOUND, "菜单不存在"));

        YesNoEnum changedEnabled = menu.toggleEnabled();

        menuRepository.updateEnabledById(id, changedEnabled.getCode());
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
        menuRepository.delete(id);
    }
}
