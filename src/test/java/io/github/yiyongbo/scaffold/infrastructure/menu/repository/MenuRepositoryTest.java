package io.github.yiyongbo.scaffold.infrastructure.menu.repository;

import io.github.yiyongbo.scaffold.common.enums.YesNoEnum;
import io.github.yiyongbo.scaffold.domain.menu.enums.MenuTypeEnum;
import io.github.yiyongbo.scaffold.domain.menu.model.entity.MenuEntity;
import io.github.yiyongbo.scaffold.domain.menu.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * 系统菜单仓储测试
 *
 * @author kidd
 * @since 2026/6/8 23:52
 */
@SpringBootTest
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void testSaveAndFindById() {
        MenuEntity menu = new MenuEntity();
        menu.setParentId(0L);
        menu.setMenuName("系统管理");
        menu.setMenuType(MenuTypeEnum.DIR);
        menu.setRoutePath("/system");
        menu.setComponent("Layout");
        menu.setPermissionCode("system");
        menu.setIcon("setting");
        menu.setSort(1);
        menu.setVisible(YesNoEnum.YES);
        menu.setEnable(YesNoEnum.YES);
        menu.setRemark("系统管理目录");

        Long id = menuRepository.save(menu);

        Optional<MenuEntity> result = menuRepository.findById(id);

        System.out.println(result.orElse(null));
    }
}
