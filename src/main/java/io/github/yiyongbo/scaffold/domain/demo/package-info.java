/**
 * demo 领域上下文。
 *
 * <p>用于演示 domain 层的标准组织方式。
 * 实际业务开发时，应将 demo 替换为真实业务上下文名称，例如 auth、user、role、permission 等。</p>
 *
 * <p>domain 层负责承载领域模型、领域服务、领域事件、仓储接口和外部能力抽象，用于表达核心业务规则、业务状态流转和业务不变量。</p>
 *
 * <p>常见包职责：</p>
 * <ul>
 *     <li>model：领域模型，包括 aggregate 聚合根、entity 实体、valueobject 值对象、enums 业务枚举；</li>
 *     <li>service：领域服务，统一命名为 XxxDomainService，用于承载不适合放在单个实体或聚合中的领域规则；</li>
 *     <li>repository：仓储接口，统一命名为 XxxRepository，用于定义领域对象的持久化抽象；</li>
 *     <li>gateway：外部能力接口，统一命名为 XxxGateway 或具体能力名，用于定义领域所需的外部系统或技术能力；</li>
 *     <li>event：领域事件，统一命名为 XxxEvent。</li>
 * </ul>
 *
 * <p>本层不依赖 adapter、application、infrastructure 中的具体实现；
 * 不允许出现 Controller、Request、Response、DTO、PO、Mapper、RedisTemplate、MQ Client、第三方 SDK 等外部技术细节。</p>
 */
package io.github.yiyongbo.scaffold.domain.demo;