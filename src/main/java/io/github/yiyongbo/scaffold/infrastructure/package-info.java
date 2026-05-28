/**
 * 基础设施层。
 *
 * <p>负责数据库、缓存、消息队列、文件存储、第三方系统等外部技术实现，并实现 domain 层定义的 Repository 和 Gateway 接口。</p>
 *
 * <p>常见包职责：</p>
 * <ul>
 *     <li>persistence：数据库持久化实现，包括 Mapper、PO、RepositoryImpl、Converter；</li>
 *     <li>cache：缓存访问实现，例如 Redis、本地缓存等；</li>
 *     <li>gateway：普通外部能力实现，例如 JWT、密码加密、短信、文件存储等；</li>
 *     <li>mq：消息发送、事件发布和消息基础设施封装；</li>
 *     <li>acl：复杂第三方业务系统的防腐层，例如 HIS、企业微信、ERP、支付平台等。</li>
 * </ul>
 *
 * <p>本层可以依赖 domain 和 common，但不应反向影响领域模型设计。
 * 数据库 PO、Mapper、第三方 SDK DTO、外部系统返回对象等不得泄露到 domain 层。</p>
 */
package io.github.yiyongbo.scaffold.infrastructure;