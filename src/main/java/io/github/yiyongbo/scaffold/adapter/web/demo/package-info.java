/**
 * demo Web 入口适配。
 *
 * <p>用于演示单个业务上下文在 Web 入口层的标准组织方式。
 * 实际业务开发时，应将 demo 替换为真实业务上下文名称，例如 auth、user、role、permission 等。</p>
 *
 * <p>常见包职责：</p>
 * <ul>
 *     <li>request：HTTP 请求入参，统一命名为 XxxRequest；</li>
 *     <li>response：HTTP 响应出参，统一命名为 XxxResponse；</li>
 *     <li>assembler：Web 层对象转换，统一命名为 XxxWebAssembler，
 *     负责 Request 转 Command/Query，以及 DTO 转 Response。</li>
 * </ul>
 *
 * <p>本包不承载核心业务规则，不直接访问数据库、缓存、消息队列或第三方系统。
 * Web 入口应通过 application 层的应用服务完成业务用例。</p>
 */
package io.github.yiyongbo.scaffold.adapter.web.demo;