/**
 * demo 持久化实现。
 *
 * <p>用于演示 infrastructure.persistence 层中单个业务上下文的标准组织方式。
 * 实际业务开发时，应将 demo 替换为真实业务上下文名称。</p>
 *
 * <p>常见包职责：</p>
 * <ul>
 *     <li>po：数据库持久化对象，统一命名为 XxxPO；</li>
 *     <li>mapper：数据库访问接口，统一命名为 XxxMapper；</li>
 *     <li>repository：仓储接口实现，统一命名为 XxxRepositoryImpl，用于实现 domain 层定义的 XxxRepository；</li>
 *     <li>assembler：持久化对象转换器，统一命名为 XxxPersistenceAssembler，负责 PO 与领域对象之间的转换。</li>
 * </ul>
 *
 * <p>PO、Mapper、SQL、ORM 查询条件等持久化细节不得泄露到 domain 或 application 层。</p>
 */
package io.github.yiyongbo.scaffold.infrastructure.persistence.demo;