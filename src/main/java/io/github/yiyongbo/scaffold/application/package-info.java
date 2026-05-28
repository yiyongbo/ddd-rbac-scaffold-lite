/**
 * 应用层。
 *
 * <p>负责业务用例编排、事务控制、Command/Query 处理和 DTO 返回。
 * 本层通过调用 domain 层的领域模型、领域服务、Repository 接口和 Gateway 接口完成业务流程。</p>
 *
 * <p>本层适合放置：</p>
 * <ul>
 *     <li>service：应用服务，例如 XxxApplicationService、XxxQueryService；</li>
 *     <li>command：写操作入参；</li>
 *     <li>query：查询操作入参；</li>
 *     <li>dto：应用层返回数据；</li>
 *     <li>assembler：应用层对象转换，例如 Command 转领域对象、领域对象转 DTO。</li>
 * </ul>
 *
 * <p>本层不承载核心领域规则，不直接依赖 Mapper、PO、RedisTemplate、MQ Client 或第三方 SDK。
 * 核心业务规则应沉淀在 domain 层。</p>
 */
package io.github.yiyongbo.scaffold.application;