package io.github.yiyongbo.scaffold.adapter.web.user.assembler;

import io.github.yiyongbo.scaffold.adapter.web.user.request.UserCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserPageRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserUpdateRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.response.UserPageResponse;
import io.github.yiyongbo.scaffold.adapter.web.user.response.UserResponse;
import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import io.github.yiyongbo.scaffold.application.user.dto.UserDTO;
import io.github.yiyongbo.scaffold.application.user.dto.UserPageDTO;
import io.github.yiyongbo.scaffold.application.user.query.UserPageQuery;
import io.github.yiyongbo.scaffold.common.page.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 系统用户 Web 对象装配器
 *
 * @author kidd
 * @since 2026/6/14 18:16
 */
@Mapper(componentModel = "spring")
public interface UserWebAssembler {

    /**
     * 创建系统用户请求 转 创建系统用户命令
     */
    UserCreateCommand toCreateCommand(UserCreateRequest request);

    /**
     * 更新系统用户请求 转 更新系统用户命令
     */
    @Mapping(target = "id", source = "id")
    UserUpdateCommand toUpdateCommand(Long id, UserUpdateRequest request);

    /**
     * 系统用户DTO 转 系统用户响应
     */
    UserResponse toResponse(UserDTO user);

    /**
     * 系统用户分页查询请求 转 系统用户分页查询
     */
    UserPageQuery toPageQuery(UserPageRequest request);

    /**
     * 系统用户分页查询结果 转 系统用户分页响应
     */
    List<UserPageResponse> toPageResponse(List<UserPageDTO> list);

    /**
     * 系统用户分页查询结果 转 系统用户分页响应
     */
    default PageResult<UserPageResponse> toPageResponse(PageResult<UserPageDTO> page) {
        return PageResult.of(
                toPageResponse(page.getRecords()),
                page.getTotal(),
                page.getPageNum(),
                page.getPageSize()
        );
    }
}
