package io.github.yiyongbo.scaffold.application.menu.service;

import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCmd;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCmd;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 系统菜单命令服务测试
 *
 * @author kidd
 * @since 2026/6/9 11:05
 */
@SpringBootTest
public class MenuCommandServiceTest {

    @Autowired
    private MenuCommandService menuCommandService;

    @Test
    void testCreateAndUpdateAndDelete() {
        MenuCreateCmd createCmd = new MenuCreateCmd();
        createCmd.setParentId(0L);
        createCmd.setMenuName("系统管理");
        createCmd.setMenuType(1);
        createCmd.setRoutePath("/system");
        createCmd.setComponent("Layout");
        createCmd.setPermissionCode("system:test:" + System.currentTimeMillis());
        createCmd.setIcon("setting");
        createCmd.setSort(1);
        createCmd.setVisible(1);
        createCmd.setEnable(1);
        createCmd.setRemark("系统管理目录");

        Long id = menuCommandService.create(createCmd);

        MenuUpdateCmd updateCmd = new MenuUpdateCmd();
        updateCmd.setId(id);
        updateCmd.setParentId(0L);
        updateCmd.setMenuName("系统管理-修改");
        updateCmd.setMenuType(1);
        updateCmd.setRoutePath("/system");
        updateCmd.setComponent("Layout");
        updateCmd.setPermissionCode(createCmd.getPermissionCode());
        updateCmd.setIcon("setting");
        updateCmd.setSort(2);
        updateCmd.setVisible(1);
        updateCmd.setEnable(1);
        updateCmd.setRemark("系统管理目录-修改");

        menuCommandService.update(updateCmd);

        menuCommandService.delete(id);
    }

}
