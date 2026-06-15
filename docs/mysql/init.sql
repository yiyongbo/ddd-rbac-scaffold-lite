# 系统菜单权限表
CREATE TABLE sys_menu (
    id              BIGINT      NOT NULL COMMENT '主键ID',
    parent_id       BIGINT      NOT NULL DEFAULT 0 COMMENT '父级菜单ID，0表示根节点',
    menu_name       VARCHAR(64) NOT NULL COMMENT '菜单名称',
    menu_type       TINYINT     NOT NULL COMMENT '菜单类型：1目录，2菜单，3按钮',
    route_path      VARCHAR(255)         DEFAULT NULL COMMENT '路由路径，例如 /system/user',
    component       VARCHAR(255)         DEFAULT NULL COMMENT '组件路径，例如 system/user/index',
    permission_code VARCHAR(128)         DEFAULT NULL COMMENT '权限标识，例如 system:user:create',
    icon            VARCHAR(64)          DEFAULT NULL COMMENT '菜单图标',
    sort            INT         NOT NULL DEFAULT 0 COMMENT '排序值，越小越靠前',
    enabled         TINYINT     NOT NULL DEFAULT 1 COMMENT '是否已启用：1是，0否',
    remark          VARCHAR(255)         DEFAULT NULL COMMENT '备注',
    created_at      DATETIME    NOT NULL COMMENT '创建时间',
    updated_at      DATETIME    NOT NULL COMMENT '更新时间',
    deleted         TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除，1已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_permission_code (permission_code),
    KEY idx_parent_id (parent_id),
    KEY idx_menu_type (menu_type),
    KEY idx_enabled (enabled),
    KEY idx_sort (sort)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统菜单权限表';

# 系统角色表
CREATE TABLE sys_role (
    id         BIGINT      NOT NULL COMMENT '主键ID',
    role_code  VARCHAR(64) NOT NULL COMMENT '角色编码',
    role_name  VARCHAR(64) NOT NULL COMMENT '角色名称',
    enabled    TINYINT     NOT NULL DEFAULT 1 COMMENT '是否已启用：1是，0否',
    sort       INT         NOT NULL DEFAULT 0 COMMENT '排序值，值越小越靠前',
    remark     VARCHAR(255)         DEFAULT NULL COMMENT '备注',
    created_at DATETIME    NOT NULL COMMENT '创建时间',
    updated_at DATETIME    NOT NULL COMMENT '更新时间',
    deleted    TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除，1已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code_deleted (role_code),
    KEY idx_role_name (role_name),
    KEY idx_enabled (enabled),
    KEY idx_sort (sort)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统角色表';

# 角色菜单关联表
CREATE TABLE sys_role_menu (
    id         BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    role_id    BIGINT NOT NULL COMMENT '角色ID',
    menu_id    BIGINT NOT NULL COMMENT '菜单ID',
    created_at DATETIME DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_menu (role_id, menu_id),
    KEY idx_role_id (role_id),
    KEY idx_menu_id (menu_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色菜单关联表';

# 系统用户表
CREATE TABLE `sys_user` (
    `id`         BIGINT       NOT NULL COMMENT '主键ID',
    `username`   VARCHAR(64)  NOT NULL COMMENT '用户名',
    `password`   VARCHAR(255) NOT NULL COMMENT '密码',
    `nickname`   VARCHAR(64)  NOT NULL COMMENT '用户昵称',
    `email`      VARCHAR(128)          DEFAULT NULL COMMENT '邮箱',
    `phone`      VARCHAR(32)           DEFAULT NULL COMMENT '手机号',
    `avatar`     VARCHAR(255)          DEFAULT NULL COMMENT '头像',
    `enabled`    TINYINT      NOT NULL DEFAULT 1 COMMENT '是否已启用：1是，0否',
    `created_at` DATETIME              DEFAULT NULL COMMENT '创建时间',
    `updated_at` DATETIME              DEFAULT NULL COMMENT '更新时间',
    `deleted`    TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除，1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_nickname` (`nickname`),
    KEY `idx_enabled` (`enabled`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统用户表';

# 用户角色关联表
CREATE TABLE `sys_user_role` (
    `id`         BIGINT NOT NULL COMMENT '主键ID',
    `user_id`    BIGINT NOT NULL COMMENT '用户ID',
    `role_id`    BIGINT NOT NULL COMMENT '角色ID',
    `created_at` DATETIME DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户角色关联表';