# 系统菜单权限表
CREATE TABLE `sys_menu`
(
    `id`              bigint      NOT NULL COMMENT '主键ID',
    `parent_id`       bigint      NOT NULL DEFAULT '0' COMMENT '父级菜单ID，0表示根节点',
    `menu_name`       varchar(64) NOT NULL COMMENT '菜单名称',
    `menu_type`       tinyint     NOT NULL COMMENT '菜单类型：1目录，2菜单，3按钮',
    `route_path`      varchar(255)         DEFAULT NULL COMMENT '路由路径，例如 /system/user',
    `component`       varchar(255)         DEFAULT NULL COMMENT '组件路径，例如 system/user/index',
    `permission_code` varchar(128)         DEFAULT NULL COMMENT '权限标识，例如 system:user:create',
    `icon`            varchar(64)          DEFAULT NULL COMMENT '菜单图标',
    `sort`            int         NOT NULL DEFAULT '0' COMMENT '排序值，越小越靠前',
    `enabled`         tinyint     NOT NULL DEFAULT '1' COMMENT '是否已启用：1是，0否',
    `remark`          varchar(255)         DEFAULT NULL COMMENT '备注',
    `created_by`      bigint      NOT NULL COMMENT '创建人ID',
    `created_at`      datetime    NOT NULL COMMENT '创建时间',
    `updated_by`      bigint      NOT NULL COMMENT '更新人ID',
    `updated_at`      datetime    NOT NULL COMMENT '更新时间',
    `deleted`         tinyint     NOT NULL DEFAULT '0' COMMENT '逻辑删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_permission_code` (`permission_code`),
    KEY `idx_parent_id` (`parent_id`),
    KEY `idx_menu_type` (`menu_type`),
    KEY `idx_enabled` (`enabled`),
    KEY `idx_sort` (`sort`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统菜单权限表';

# 系统角色表
CREATE TABLE `sys_role`
(
    `id`         bigint      NOT NULL COMMENT '主键ID',
    `role_code`  varchar(64) NOT NULL COMMENT '角色编码',
    `role_name`  varchar(64) NOT NULL COMMENT '角色名称',
    `enabled`    tinyint     NOT NULL DEFAULT '1' COMMENT '是否已启用：1是，0否',
    `sort`       int         NOT NULL DEFAULT '0' COMMENT '排序值，值越小越靠前',
    `remark`     varchar(255)         DEFAULT NULL COMMENT '备注',
    `created_by` bigint      NOT NULL COMMENT '创建人ID',
    `created_at` datetime    NOT NULL COMMENT '创建时间',
    `updated_by` bigint      NOT NULL COMMENT '更新人ID',
    `updated_at` datetime    NOT NULL COMMENT '更新时间',
    `deleted`    tinyint     NOT NULL DEFAULT '0' COMMENT '逻辑删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code_deleted` (`role_code`),
    KEY `idx_role_name` (`role_name`),
    KEY `idx_enabled` (`enabled`),
    KEY `idx_sort` (`sort`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统角色表';

# 角色菜单关联表
CREATE TABLE `sys_role_menu`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`    bigint   NOT NULL COMMENT '角色ID',
    `menu_id`    bigint   NOT NULL COMMENT '菜单ID',
    `created_by` bigint   NOT NULL COMMENT '创建人ID',
    `created_at` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`),
    KEY `idx_role_id` (`role_id`),
    KEY `idx_menu_id` (`menu_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单关联表';

# 系统用户表
CREATE TABLE `sys_user`
(
    `id`         bigint       NOT NULL COMMENT '主键ID',
    `username`   varchar(64)  NOT NULL COMMENT '用户名',
    `password`   varchar(255) NOT NULL COMMENT '密码',
    `nickname`   varchar(64)  NOT NULL COMMENT '用户昵称',
    `email`      varchar(128)          DEFAULT NULL COMMENT '邮箱',
    `phone`      varchar(32)           DEFAULT NULL COMMENT '手机号',
    `avatar`     varchar(255)          DEFAULT NULL COMMENT '头像',
    `enabled`    tinyint      NOT NULL DEFAULT '1' COMMENT '是否已启用：1是，0否',
    `created_by` bigint       NOT NULL COMMENT '创建人ID',
    `created_at` datetime     NOT NULL COMMENT '创建时间',
    `updated_by` bigint       NOT NULL COMMENT '更新人ID',
    `updated_at` datetime     NOT NULL COMMENT '更新时间',
    `deleted`    tinyint      NOT NULL DEFAULT '0' COMMENT '逻辑删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_nickname` (`nickname`),
    KEY `idx_enabled` (`enabled`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户表';

# 用户角色关联表
CREATE TABLE `sys_user_role`
(
    `id`         bigint   NOT NULL COMMENT '主键ID',
    `user_id`    bigint   NOT NULL COMMENT '用户ID',
    `role_id`    bigint   NOT NULL COMMENT '角色ID',
    `created_by` bigint   NOT NULL COMMENT '创建人ID',
    `created_at` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表';