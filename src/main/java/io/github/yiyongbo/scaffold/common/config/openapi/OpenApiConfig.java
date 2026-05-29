package io.github.yiyongbo.scaffold.common.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 配置
 *
 * @author kidd
 * @since 2026/5/29 11:51
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(securityComponents())
                .externalDocs(new ExternalDocumentation()
                        .description("项目文档")
                        .url("https://github.com/yiyongbo/ddd-rbac-scaffold-lite"));
    }

    private Info apiInfo() {
        return new Info()
                .title("DDD RBAC Scaffold Lite API")
                .description("基于 Spring Boot、DDD、COLA 5 Light、轻量 CQRS 的 RBAC 后台脚手架接口文档")
                .version("1.0.0")
                .contact(new Contact()
                        .name("yiyongbo")
                        .email("1196736083@qq.com")
                        .url("https://yiyongbo.github.io"))
                .license(new License()
                        .name("Apache License 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"));
    }

    private Components securityComponents() {
        return new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }
}