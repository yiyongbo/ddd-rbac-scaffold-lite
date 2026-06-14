package io.github.yiyongbo.scaffold.adapter.web.user;

import io.github.yiyongbo.scaffold.adapter.web.user.assembler.UserWebAssembler;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserAssignRolesRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserCreateRequest;
import io.github.yiyongbo.scaffold.adapter.web.user.request.UserUpdateRequest;
import io.github.yiyongbo.scaffold.application.user.command.UserCreateCommand;
import io.github.yiyongbo.scaffold.application.user.command.UserUpdateCommand;
import io.github.yiyongbo.scaffold.application.user.service.UserCommandService;
import io.github.yiyongbo.scaffold.common.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户管理 Controller
 *
 * @author kidd
 * @since 2026/6/14 17:36
 */
@Tag(name = "系统用户管理")
@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;

    private final UserWebAssembler userWebAssembler;

    @Operation(summary = "创建用户")
    @PostMapping
    public Result<Long> create(@Valid @RequestBody UserCreateRequest request) {
        UserCreateCommand createCommand = userWebAssembler.toCreateCommand(request);
        Long id = userCommandService.create(createCommand);
        return Result.success(id);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public Result<Void> update(
            @Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {
        UserUpdateCommand updateCommand = userWebAssembler.toUpdateCommand(id, request);
        userCommandService.update(updateCommand);
        return Result.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long id) {
        userCommandService.delete(id);
        return Result.success();
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public void detail(@Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long id) {
    }

    @Operation(summary = "获取用户分页")
    @GetMapping("/page")
    public void page() {
    }

    @Operation(summary = "查询用户角色 ID 列表")
    @GetMapping("/{id}/roleIds")
    public void listRoleIds() {
    }

    @Operation(summary = "分配用户角色")
    @PostMapping("/{userId}/roles")
    public Result<Void> assignRoles(
            @Parameter(description = "用户ID", required = true, example = "1") @PathVariable Long userId,
            @Valid @RequestBody UserAssignRolesRequest request) {

        userCommandService.assignRoles(userId, request.getRoleIds());
        return Result.success();
    }

}
