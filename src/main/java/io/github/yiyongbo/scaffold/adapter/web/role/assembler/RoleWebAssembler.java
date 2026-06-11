package io.github.yiyongbo.scaffold.adapter.web.role.assembler;

import io.github.yiyongbo.scaffold.adapter.web.role.request.RolePageRequest;
import io.github.yiyongbo.scaffold.adapter.web.role.request.RoleCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.role.request.RoleUpdateRequest;
import io.github.yiyongbo.scaffold.adapter.web.role.response.RolePageResponse;
import io.github.yiyongbo.scaffold.adapter.web.role.response.RoleResponse;
import io.github.yiyongbo.scaffold.application.role.command.RoleCreateCommand;
import io.github.yiyongbo.scaffold.application.role.command.RoleUpdateCommand;
import io.github.yiyongbo.scaffold.application.role.dto.RoleDTO;
import io.github.yiyongbo.scaffold.application.role.dto.RolePageDTO;
import io.github.yiyongbo.scaffold.application.role.query.RolePageQuery;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 系统角色 Web 对象装配器
 *
 * @author kidd
 * @since 2026/6/10 19:53
 */
@Mapper(componentModel = "spring")
public interface RoleWebAssembler {

    /**
     * 创建请求转创建命令
     */
    RoleCreateCommand toCreateCommand(RoleCreateRequest request);

    /**
     * 更新请求转更新命令
     */
    @Mapping(target = "id", source = "id")
    RoleUpdateCommand toUpdateCommand(Long id, RoleUpdateRequest request);

    /**
     * 角色 DTO 转 响应
     */
    RoleResponse toResponse(RoleDTO dto);

    /**
     * 分页请求 转 分页查询
     */
    RolePageQuery toPageQuery(RolePageRequest request);

    /**
     * 角色分页 DTO 转 角色分页响应
     */
    List<RolePageResponse> toPageResponseList(List<RolePageDTO> dtoList);

    /**
     * 角色分页 DTO 转 角色分页响应
     */
    default PageResult<RolePageResponse> toPageResponse(PageResult<RolePageDTO> pageResult) {
        return PageResult.of(
                toPageResponseList(pageResult.getRecords()),
                pageResult.getTotal(),
                pageResult.getPageNum(),
                pageResult.getPageSize()
        );
    }
}
