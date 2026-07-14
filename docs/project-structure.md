# Project Structure

`ddd-rbac-scaffold-lite` 在单个 Spring Boot 模块内采用 DDD 四层结构。目标是让业务规则、用例流程和技术实现各归其位，而不是为了分层而分层。

```text
src/main/java/io/github/yiyongbo/scaffold
├── adapter          # HTTP、Job、消息消费、回调等外部入口
├── application      # 用例编排、事务、Command / Query、DTO
├── domain           # 领域模型、规则与外部能力抽象
├── infrastructure   # 数据库、缓存与外部能力实现
├── common           # 无业务语义的通用能力
└── Application.java # 启动类
```

## 分层与依赖方向

```text
adapter  ──────> application ──────> domain
                                          ^
                                          │
                                 infrastructure

common 可被各层复用，但不包含用户、角色等具体业务语义。
```

`domain` 只声明业务所需的 `Repository`、`Gateway`、缓存等抽象，不能依赖 `infrastructure` 的 Mapper、PO、Redis 或第三方 SDK。`infrastructure` 实现这些抽象，因此依赖方向始终指向领域层。

## 各层职责

| 层 | 负责什么 | 不应放什么 |
|---|---|---|
| `adapter` | HTTP Controller、请求校验、协议对象转换、统一响应 | 领域规则、Mapper、RedisTemplate、第三方 SDK |
| `application` | 用例编排、事务控制、Command/Query 处理、DTO 输出 | 持久化细节、PO、核心业务不变量 |
| `domain` | 聚合/实体/值对象、领域服务、领域事件、Repository/Gateway 抽象 | Controller、Request/Response、DTO、PO、Mapper 和框架实现 |
| `infrastructure` | Repository/Gateway 的实现、Mapper、PO、Redis、JWT、外部系统接入 | 改变领域模型边界的业务规则 |
| `common` | 统一响应、异常、分页、通用配置、无业务语义工具 | `User`、`Role` 等业务概念 |

## 按业务上下文组织

除通用层外，代码按业务上下文拆分。当前已包含 `auth`、`user`、`role`、`menu`：

```text
adapter/web/user
application/user
domain/user
infrastructure/persistence/user
```

新增模块时优先沿用这个路径，例如新增 `tenant`：

```text
adapter/web/tenant
application/tenant
domain/tenant
infrastructure/persistence/tenant
```

`demo` 包保留完整目录形态，仅作为结构参考；新业务应使用真实的业务上下文名称，而不是继续扩展 `demo`。

## Command 与 Query

写操作与查询操作分开组织，但不强制引入独立读库。

### 写操作

```text
HTTP Request
  → XxxWebAssembler
  → XxxCommand
  → XxxCommandService
  → 领域实体 / XxxDomainService
  → XxxRepository 或 XxxGateway（领域接口）
  → XxxRepositoryImpl / 基础设施实现
```

- `Command` 表达一次写用例的输入；
- `XxxCommandService` 编排用例并控制事务；
- 业务规则应在领域实体或 `XxxDomainService` 中；
- `Repository` 和 `Gateway` 接口定义在 `domain`，实现放在 `infrastructure`。

### 查询操作

```text
HTTP Request
  → XxxWebAssembler
  → XxxQuery
  → XxxQueryService
  → 持久化查询
  → XxxDTO
  → XxxWebAssembler
  → HTTP Response
```

查询服务返回应用层 DTO。当前项目的查询实现可通过持久化组件读取数据；不要让 PO 或 Mapper 类型越过 application/adapter 边界。

## 对象职责

| 对象 | 所在位置 | 用途 |
|---|---|---|
| `XxxRequest` | `adapter.web.<context>.request` | HTTP 入参，包含协议和参数校验语义 |
| `XxxResponse` | `adapter.web.<context>.response` | HTTP 出参 |
| `XxxCommand` | `application.<context>.command` | 写操作用例输入 |
| `XxxQuery` | `application.<context>.query` | 查询用例输入 |
| `XxxDTO` | `application.<context>.dto` | 应用层对外的数据结果 |
| 领域实体/值对象 | `domain.<context>.model` | 表达业务状态、行为与不变量 |
| `XxxPO` | `infrastructure.persistence.<context>.po` | 数据库持久化对象 |

对象转换由对应层的 Assembler 完成：

- `XxxWebAssembler`：`Request ↔ Command/Query`、`DTO → Response`；
- `XxxAppAssembler`：`Command ↔` 领域对象、领域对象 `↔ DTO`；
- `XxxPersistenceAssembler`：领域对象 `↔ PO`。

## Repository、Gateway 与 ACL

- `Repository`：以领域对象为中心的持久化抽象，例如 `UserRepository`；实现在 `infrastructure.persistence`。
- `Gateway`：领域所需的外部能力抽象，例如密码编码、Token 签发；实现在 `infrastructure.gateway`。
- `ACL`：面向复杂外部业务系统的防腐层；当前目录已预留，尚无具体外部系统实现。

缓存属于基础设施能力：领域层可定义缓存抽象，Redis 实现位于 `infrastructure.cache`。不要在领域或应用服务中直接依赖 `RedisTemplate`。

## 新增业务的落位建议

以“创建租户”为例：

1. 在 `domain/tenant` 定义租户实体、规则和 `TenantRepository`；
2. 在 `infrastructure/persistence/tenant` 新增 PO、Mapper、Assembler 及 `TenantRepositoryImpl`；
3. 在 `application/tenant` 新增 `TenantCreateCommand`、应用服务、DTO 和 App Assembler；
4. 在 `adapter/web/tenant` 新增 Request、Response、Web Assembler 与 Controller；
5. 只有跨业务通用的无业务语义能力才放入 `common`。

这样既能从入口追踪到具体实现，也能确保数据库和框架细节不会侵入领域模型。
