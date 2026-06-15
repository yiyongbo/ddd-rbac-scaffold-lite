# scaffold-server-light

> 面向中小团队的轻量 DDD / COLA 5 Light 风格 RBAC 后台脚手架。  
> 以 RBAC 权限系统为业务载体，沉淀一套清晰、可学习、可复用、可扩展的 Spring Boot 企业级工程模板。

## 项目简介

`scaffold-server-light` 是一个基于 **Java 17 + Spring Boot 3** 的后台管理 RBAC 脚手架项目。

它不是单纯的 CRUD 后台模板，也不是重型微服务平台，而是希望解决一个更具体的问题：

> Java 后端团队如何在单体项目中，以较低成本落地 DDD、COLA 5 Light、轻量 CQRS 和端口适配器思想？

项目当前处于早期阶段，已完成基础工程分层模板设计，后续会逐步完善登录认证、用户管理、角色管理、菜单权限、操作日志等 RBAC 核心能力。

## 项目特点

- **轻量 DDD 落地**：用领域模型、领域服务、仓储接口和外部能力接口组织核心业务。
- **COLA 5 Light 风格**：采用单体 package 分层，避免一开始就引入过重的多模块结构。
- **轻量 CQRS**：写操作走 Command + ApplicationService + Domain，查询操作走 Query + QueryService。
- **端口与适配器思想**：通过 Repository、Gateway、ACL 隔离数据库、缓存、消息队列和第三方系统。
- **RBAC 业务示例**：以后台权限系统为载体，逐步演示 DDD 工程结构如何在真实业务中落地。
- **适合中小团队**：不追求复杂架构堆叠，优先保证结构清晰、职责明确、易于维护和扩展。

## 技术栈

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- MySQL + MyBatis-Plus
- Redis
- Springdoc OpenAPI
- Docker / Docker Compose

## 架构概览

项目采用：

```text
COLA 5 Light + DDD 四层思想 + 六边形端口适配器思想 + 轻量 CQRS
```

核心 package 结构：

```text
src/main/java/cn/seepeak/scaffold
├── adapter          # 外部入口适配层：Web、Job、Consumer、Callback
├── application      # 应用层：用例编排、事务控制、Command / Query / DTO
├── domain           # 领域层：聚合、实体、值对象、领域服务、仓储接口、外部能力接口、领域事件
├── infrastructure   # 基础设施层：数据库、缓存、MQ、第三方系统、防腐层实现
├── common           # 通用基础层：响应、异常、分页、常量、工具类
└── Application.java # 启动类
```

### 分层职责

| 分层 | 职责 |
|---|---|
| `adapter` | 处理外部入口，例如 HTTP Controller、消息消费、定时任务、第三方回调 |
| `application` | 编排业务用例、控制事务、处理 Command / Query、返回 DTO |
| `domain` | 承载核心业务规则、领域模型、领域服务、Repository 接口、Gateway 接口和领域事件 |
| `infrastructure` | 实现数据库、缓存、消息队列、第三方系统访问、防腐层等技术细节 |
| `common` | 提供跨层通用能力，不包含具体业务语义 |

### 典型调用链

写操作：

```text
adapter.web.Controller
  -> application.XxxCommandService
  -> domain.model / domain.service
  -> domain.repository / domain.gateway
  -> infrastructure.persistence / cache / gateway / acl
```

查询操作：

```text
adapter.web.Controller
  -> application.XxxQueryService
  -> infrastructure.persistence.mapper
  -> application.dto
```

## 当前状态

当前项目处于早期迭代阶段。

已完成：

- 工程 package 分层设计；
- `adapter / application / domain / infrastructure / common` 基础结构；
- 完整模板结构 `template-full`；
- 关键 package 的职责说明；
- DDD / COLA 5 Light 风格的基础工程约束。

后续将逐步完善：

- 基础通用能力；
- 用户、角色、菜单、权限管理；
- Spring Security + JWT 登录认证；
- 操作日志、登录日志；
- Docker Compose 与初始化 SQL；
- 项目文档和开发指南。

## 模板说明

当前模板为完整结构版本：

```text
template-full
```

包含：

- `adapter.web` 下的 `request / response / assembler`
- `application` 下的 `command / query / dto / service / assembler`
- `domain` 下的 `model / service / repository / gateway / event`
- `infrastructure` 下的 `persistence / cache / gateway / mq / acl`

后续可根据项目复杂度裁剪出轻量模板：

```text
template-light
```

轻量模板可让 `adapter` 直接使用 `application` 中的 `Command / Query / DTO`，减少样板代码。

## 文档

后续将补充以下文档：

- `docs/architecture.md`：架构设计说明
- `docs/package-convention.md`：包结构与编码规范
- `docs/rbac-design.md`：RBAC 领域设计
- `docs/development-guide.md`：功能开发指南

## 参与贡献

当前项目处于早期阶段，欢迎提出建议、Issue 或 Pull Request。

参与方式：

1. Fork 本仓库；
2. 创建功能分支；
3. 提交代码或文档；
4. 创建 Pull Request；
5. 等待 Review。

## 项目愿景

本项目希望沉淀一套：

```text
能学习、能运行、能扩展、能在中小团队落地的轻量 DDD RBAC 后台脚手架。
```

它不是为了追求复杂架构，而是为了让：

- 外部入口归 adapter；
- 用例编排归 application；
- 业务规则归 domain；
- 技术实现归 infrastructure；
- 通用能力归 common。

如果你也在寻找适合国内 Java 团队的 DDD 工程落地方式，欢迎一起交流和完善。
