/**
 * demo 应用上下文。
 *
 * <p>用于演示 application 层的标准组织方式。
 * 实际业务开发时，应将 demo 替换为真实业务上下文名称，例如 auth、user、role、permission 等。</p>
 *
 * <p>application 层负责业务用例编排、事务控制、Command/Query 处理和 DTO 返回。
 * 本层通过调用 domain 层的领域模型、领域服务、Repository 接口和 Gateway 接口完成业务流程。</p>
 *
 * <p>常见包职责：</p>
 * <ul>
 *     <li>command：写操作入参，统一命名为 XxxCommand；</li>
 *     <li>query：查询操作入参，统一命名为 XxxQuery；</li>
 *     <li>dto：应用层返回数据，统一命名为 XxxDTO；</li>
 *     <li>service：应用服务，统一命名为 XxxApplicationService、XxxQueryService；</li>
 *     <li>assembler：应用层对象转换，统一命名为 XxxApplicationAssembler，负责 Command 转领域模型、领域模型转 DTO。</li>
 * </ul>
 *
 * <p>本层不承载核心领域规则，不直接依赖 Mapper、PO、RedisTemplate、MQ Client 或第三方 SDK。
 * 核心业务规则应沉淀在 domain 层。</p>
 */
package io.github.yiyongbo.scaffold.application.demo;