/**
 * 持久化实现层。
 *
 * <p>负责按照业务上下文拆分数据库持久化实现，隔离数据库表结构、ORM 框架、Mapper、PO 等持久化细节。</p>
 *
 * <p>本层用于实现 domain 层定义的 Repository 接口。
 * application 和 domain 不应直接依赖 Mapper、PO 或 SQL 实现细节。</p>
 */
package io.github.yiyongbo.scaffold.infrastructure.persistence;