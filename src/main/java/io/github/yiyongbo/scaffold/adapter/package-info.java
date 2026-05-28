/**
 * 外部入口适配层。
 *
 * <p>负责处理外部请求进入系统的入口，并将外部协议、入参模型转换为 application 层可识别的 Command 或 Query。</p>
 *
 * <p>常见入口类型：</p>
 * <ul>
 *     <li>web：HTTP 接口入口，例如 Controller；</li>
 *     <li>job：定时任务入口；</li>
 *     <li>consumer：消息消费入口；</li>
 *     <li>callback：第三方系统回调入口。</li>
 * </ul>
 *
 * <p>本层只做入口适配、参数校验、协议转换和返回值封装；
 * 不承载核心业务规则，不直接访问 Mapper、Redis、MQ、第三方 SDK 等基础设施实现。</p>
 */
package io.github.yiyongbo.scaffold.adapter;