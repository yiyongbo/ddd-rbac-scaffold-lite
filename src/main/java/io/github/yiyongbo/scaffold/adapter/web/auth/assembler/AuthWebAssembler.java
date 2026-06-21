package io.github.yiyongbo.scaffold.adapter.web.auth.assembler;

import io.github.yiyongbo.scaffold.adapter.web.auth.request.LoginRequest;
import io.github.yiyongbo.scaffold.adapter.web.auth.response.LoginResponse;
import io.github.yiyongbo.scaffold.application.auth.command.LoginCommand;
import io.github.yiyongbo.scaffold.application.auth.dto.LoginResultDTO;
import org.mapstruct.Mapper;

/**
 * 认证 Web 对象装配器
 *
 * @author kidd
 * @since 2026/6/16 22:54
 */
@Mapper(componentModel = "spring")
public interface AuthWebAssembler {

    /**
     * 登录请求 转 登录命令
     */
    LoginCommand toLoginCommand(LoginRequest request);

    /**
     * 登录结果DTO 转 登录响应
     */
    LoginResponse toLoginResponse(LoginResultDTO dto);
}
