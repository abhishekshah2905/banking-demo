package com.poc.banking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

@Configuration
public class OpenApiAutoConfiguration {

    @Bean
    public OpenAPI openApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .bearerFormat("JWT")
                .scheme("bearer")
                .name("Bearer")
                .type(HTTP)
                .in(HEADER);
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(securityScheme.getName(), securityScheme))
                .addSecurityItem(new SecurityRequirement().addList(securityScheme.getName()));
    }


}