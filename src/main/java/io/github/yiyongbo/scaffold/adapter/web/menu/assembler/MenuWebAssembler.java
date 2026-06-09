package io.github.yiyongbo.scaffold.adapter.web.menu.assembler;

import io.github.yiyongbo.scaffold.adapter.web.menu.request.MenuCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.menu.request.MenuUpdateRequest;
import io.github.yiyongbo.scaffold.adapter.web.menu.response.MenuResponse;
import io.github.yiyongbo.scaffold.adapter.web.menu.response.MenuTreeResponse;
import io.github.yiyongbo.scaffold.application.menu.command.MenuCreateCmd;
import io.github.yiyongbo.scaffold.application.menu.command.MenuUpdateCmd;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuDTO;
import io.github.yiyongbo.scaffold.application.menu.dto.MenuTreeDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 系统菜单 Web 对象装配器
 *
 * @author kidd
 * @since 2026/6/9 14:01
 */
@Mapper(componentModel = "spring")
public interface MenuWebAssembler {

    /**
     * 创建请求转创建命令
     */
    MenuCreateCmd toCreateCmd(MenuCreateRequest request);

    /**
     * 更新请求转更新命令
     */
    MenuUpdateCmd toUpdateCmd(MenuUpdateRequest request);

    /**
     * 菜单 DTO 转响应
     */
    MenuResponse toResponse(MenuDTO dto);

    /**
     * 菜单树 DTO 转响应
     */
    MenuTreeResponse toTreeResponse(MenuTreeDTO dto);

    /**
     * 菜单树 DTO 列表转响应列表
     */
    List<MenuTreeResponse> toTreeResponseList(List<MenuTreeDTO> dtoList);
}
