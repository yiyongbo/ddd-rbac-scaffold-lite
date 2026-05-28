/**
 * 领域层。
 *
 * <p>负责承载领域模型、领域服务、领域事件、仓储接口和外部能力抽象，用于表达核心业务规则、业务状态流转和业务不变量。</p>
 *
 * <p>本层适合放置：</p>
 * <ul>
 *     <li>model：领域模型，包括聚合根、实体、值对象和领域枚举；</li>
 *     <li>service：领域服务，用于承载不适合放在单个实体或聚合中的领域规则；</li>
 *     <li>repository：仓储接口，用于定义领域对象的持久化抽象；</li>
 *     <li>gateway：外部能力接口，用于定义领域所需的外部系统或技术能力；</li>
 *     <li>event：领域事件。</li>
 * </ul>
 *
 * <p>本层不依赖 adapter、application、infrastructure 中的具体实现；
 * 不允许出现 Controller、Request、Response、DTO、PO、Mapper、RedisTemplate、MQ Client、第三方 SDK 等外部技术细节。</p>
 */
package io.github.yiyongbo.scaffold.domain;