package io.github.yiyongbo.scaffold.adapter.web.auth;

import io.github.yiyongbo.scaffold.adapter.web.auth.assembler.AuthWebAssembler;
import io.github.yiyongbo.scaffold.adapter.web.auth.request.LoginRequest;
import io.github.yiyongbo.scaffold.adapter.web.auth.response.LoginResponse;
import io.github.yiyongbo.scaffold.application.auth.command.LoginCommand;
import io.github.yiyongbo.scaffold.application.auth.dto.LoginResultDTO;
import io.github.yiyongbo.scaffold.application.auth.service.AuthCommandService;
import io.github.yiyongbo.scaffold.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证管理 Controller
 *
 * @author kidd
 * @since 2026/6/16 22:47
 */
@Tag(name = "认证管理")
@Validated
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthWebAssembler authWebAssembler;

    private final AuthCommandService authCommandService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginCommand command = authWebAssembler.toLoginCommand(request);
        LoginResultDTO dto = authCommandService.login(command);
        return Result.success(authWebAssembler.toLoginResponse(dto));
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<Void> logout() {
        authCommandService.logout();
        return Result.success();
    }
}
