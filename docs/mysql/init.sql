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
    visible         TINYINT     NOT NULL DEFAULT 1 COMMENT '是否可见：1是，0否',
    enabled          TINYINT     NOT NULL DEFAULT 1 COMMENT '是否已启用：1是，0否',
    remark          VARCHAR(255)         DEFAULT NULL COMMENT '备注',
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT     NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除，1已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_permission_code (permission_code),
    KEY idx_parent_id (parent_id),
    KEY idx_menu_type (menu_type),
    KEY idx_enabled (enabled),
    KEY idx_sort (sort)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='系统菜单权限表';