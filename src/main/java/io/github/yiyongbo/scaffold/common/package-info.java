/**
 * 通用基础层。
 *
 * <p>负责提供跨层通用的基础能力，例如统一响应、分页对象、通用异常、通用枚举、通用常量和工具类。</p>
 *
 * <p>本层不允许包含具体业务语义，不应出现 User、Role、Order、FollowUp 等业务概念。
 * 业务枚举、业务常量和业务规则应放在对应的 domain 包中。</p>
 */
package io.github.yiyongbo.scaffold.common;