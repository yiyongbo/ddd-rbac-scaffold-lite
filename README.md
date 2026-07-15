# ddd-rbac-scaffold-lite

> 基于 Spring Boot、DDD 四层架构、轻量 CQRS 与 RBAC 的后台管理系统后端脚手架。

`ddd-rbac-scaffold-lite` 面向希望在单体 Java 项目中实践清晰分层的开发者和中小团队。它以用户、角色、菜单权限为业务载体，提供一个可运行、可阅读、可继续扩展的 Spring Boot 工程起点。

项目采用 COLA 5 Light 风格的单体包结构：不引入复杂的多模块或微服务治理组件，但通过领域层接口与基础设施实现隔离业务规则和技术细节。它适合学习和项目初始化，不试图成为功能完备的中台。

## 核心能力

- DDD 四层：`adapter`、`application`、`domain`、`infrastructure` 与 `common` 分层。
- 轻量 CQRS：写操作由 Command 和应用服务编排，查询由 Query Service 处理。
- RBAC 基础功能：用户、角色、菜单的管理，以及用户角色、角色菜单关系分配。
- 认证与授权：用户名密码登录、JWT、Redis 登录会话、Spring Security 权限校验。
- 工程通用能力：统一响应与异常、参数校验、分页、MyBatis-Plus、MapStruct、TraceId、线程池和 OpenAPI。

## 当前完成度

| 模块 | 状态 | 说明 |
|---|---|---|
| 工程分层与对象转换 | 已完成 | 提供 DDD 四层及各业务上下文的示例组织方式 |
| 用户、角色、菜单管理 | 已完成 | 包含 CRUD、分页、状态切换和关系分配接口 |
| 登录、退出与接口鉴权 | 已完成 | 使用 JWT 与 Redis 登录会话 |
| 初始化演示数据 | 未提供 | 初始化 SQL 当前只创建表结构，不包含默认管理员 |
| 操作日志、登录日志 | 规划中 | 尚未实现 |
| 前端管理端 | 规划中 | 本仓库仅包含后端 |
| Docker Compose | 已提供 | 提供 MySQL、Redis 基础环境和应用容器部署模板 |

## 技术栈

| 分类 | 技术 | 用途 |
|---|---|---|
| 语言与框架 | Java 17、Spring Boot 3.5.14 | 应用运行与 Web 能力 |
| 安全 | Spring Security、Nimbus JOSE + JWT | 认证与授权 |
| 数据 | MySQL、MyBatis-Plus 3.5.15 | 数据持久化 |
| 缓存 | Redis | 登录会话与权限缓存 |
| 接口文档 | Springdoc OpenAPI 2.8.17 | API 文档 |
| 开发工具 | Maven、Lombok、MapStruct 1.6.3、Hutool | 构建与开发辅助 |

## 架构概览

```text
Adapter（HTTP、Job、消息等入口）
    ↓
Application（用例编排、Command / Query）
    ↓
Domain（业务模型、规则、Repository / Gateway 抽象）
    ↑
Infrastructure（数据库、缓存和外部能力的实现）
```

`domain` 不依赖 `infrastructure`；基础设施层负责实现领域层定义的接口。详见 [Project Structure](docs/project-structure.md)。

## 快速开始

1. 安装 Docker 与 Docker Compose；
2. 进入 `docs/environment`，执行 `cp .env.example .env`，并替换 `.env` 中的密码；
3. 执行 `docker compose -f docker-compose-environment.yml up -d` 启动 MySQL 与 Redis；
4. 按实际环境配置 `dev` profile 所需的数据库、Redis 和 JWT 参数，并启动 Spring Boot；
5. 访问 `http://localhost:8080/swagger-ui.html` 查看 API 文档。

完整命令及应用容器部署模板说明见 [Quick Start](docs/quick-start.md)。

## 文档

| 文档 | 说明 |
|---|---|
| [Quick Start](docs/quick-start.md) | 本地环境配置、启动与基础验证 |
| [Project Structure](docs/project-structure.md) | DDD 四层职责、依赖方向和新增模块位置 |
| [Security Policy](SECURITY.md) | 安全漏洞报告方式和支持范围 |

## 参与贡献

欢迎提交 Issue、改进建议或 Pull Request。提交前请至少确保项目可编译，并运行与改动相关的测试。

## License

本项目使用 [Apache License 2.0](LICENSE)。
