package io.github.yiyongbo.scaffold.adapter.web.user.assembler;

import io.github.yiyongbo.scaffold.adapter.web.user.request.UserCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserUpdateRequest;
import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
