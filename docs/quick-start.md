# Quick Start

本文说明本地启动方式，以及仓库提供的容器部署模板如何使用。

## 环境要求

| 工具 | 版本 | 说明 |
|---|---|---|
| JDK | 17 | 项目编译版本 |
| Maven | 3.9+（建议） | 仓库未提供 Maven Wrapper |
| MySQL | 8.0+ | 初始化 SQL 使用 `utf8mb4_0900_ai_ci` |
| Redis | 6.0+ | 保存登录会话与权限缓存 |
| Docker Compose | 近期版本 | 使用容器模板时需要 |

## 本地启动（推荐）

基础环境模板 [docker-compose-environment.yml](environment/docker-compose-environment.yml) 默认启动 MySQL 8.4 和 Redis 7.4，并在 MySQL 数据卷首次创建时执行 [初始化 SQL](environment/mysql/init.sql)。

```bash
cd docs/environment
cp .env.example .env
# 将 change-this-* 替换为本地使用的值
docker compose -f docker-compose-environment.yml up -d
cd ../..
```

使用 `dev` profile，并让下列变量与 `.env` 中的 MySQL、Redis 配置保持一致：

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=ddd_rbac_scaffold_lite
export DB_USERNAME=ddd_rbac_user
export DB_PASSWORD='<same-as-MYSQL_PASSWORD>'
export REDIS_HOST=localhost
export REDIS_PORT=6379
export REDIS_PASSWORD='<same-as-REDIS_PASSWORD>'
export JWT_SECRET='<a-long-random-secret>'

mvn clean package -DskipTests
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

也可在 IDE 中运行 `io.github.yiyongbo.scaffold.Application`，并将 Active Profiles 设置为 `dev`。默认端口为 `8080`，可通过 `SERVER_PORT` 覆盖。

> `.env`、个人环境配置和真实密钥不得提交到仓库。

### 不使用 Docker

自行准备 MySQL 和 Redis 时，创建 `ddd_rbac_scaffold_lite` 数据库并执行：

```bash
mysql -u root -p ddd_rbac_scaffold_lite < docs/environment/mysql/init.sql
```

数据库应使用 `utf8mb4` 字符集和 `utf8mb4_0900_ai_ci` 排序规则。

## 应用容器部署模板

`docs/app` 提供基于已构建 JAR 的应用镜像与 Compose 模板，用于展示部署目录和 profile 注入方式，并非针对某个环境预先配置好的部署方案。

使用前应根据目标环境补充当前 profile 所需的数据库、Redis、JWT 等配置。配置可以通过 Compose `environment`、`env_file`、配置中心或密钥管理服务注入；容器内的服务地址也应替换为目标环境可解析的地址。

先构建并准备 JAR：

```bash
mvn clean package -DskipTests
cp target/ddd-rbac-scaffold-lite.jar docs/app/app.jar
```

快速启动只跳过测试执行，测试源码仍会编译；提交改动前应在准备好所需依赖服务后运行 `mvn test`。

基础环境和应用模板可以分别启动：

```bash
cd docs/environment
cp .env.example .env
docker compose -f docker-compose-environment.yml up -d

cd ../app
docker compose -f docker-compose-app.yml up --build -d
```

模板默认使用 `dev`，也可以覆盖为目标环境约定的 profile：

```bash
SPRING_PROFILES_ACTIVE=test docker compose -f docker-compose-app.yml up --build -d
```

这里的 `test` 仅演示覆盖方式；对应配置可以包含在 JAR 中，也可以由外部配置来源提供。

## 验证

应用启动后访问：

| 地址 | 说明 |
|---|---|
| `http://localhost:8080/swagger-ui.html` | Swagger UI |
| `http://localhost:8080/v3/api-docs` | OpenAPI JSON |
| `POST /api/v1/auth/login` | 登录接口 |

除登录和接口文档外，`/api/v1/**` 接口均需要 Bearer Token。

初始化 SQL 只创建表结构，不包含默认管理员或演示数据。因此空库可以验证应用启动和接口文档，但完整登录与权限流程需要自行准备用户、角色、菜单及其关系数据，密码必须使用 BCrypt 编码。

## 项目特有说明

- `application.yml` 默认激活本地 `local` profile；开源环境建议显式选择 `dev` 或自己的 profile。
- MySQL 初始化脚本只在数据卷首次创建时执行；重新初始化前请自行备份并删除旧数据卷。
- 应用容器文件是部署模板，profile 名称及外部服务配置应由使用者根据实际环境补充。
