/**
 * Web 入口适配层。
 *
 * <p>负责处理 HTTP/Web 请求入口，并按照业务上下文进行拆分。
 * 本层主要放置 Controller，以及与 Web 协议相关的请求入参、响应出参和对象转换。</p>
 *
 * <p>本层只负责参数校验、协议适配、调用 application 层服务和统一响应封装；
 * 不承载核心业务规则，不直接访问 domain repository、Mapper、Redis、MQ 或第三方 SDK。</p>
 */
package io.github.yiyongbo.scaffold.adapter.web;