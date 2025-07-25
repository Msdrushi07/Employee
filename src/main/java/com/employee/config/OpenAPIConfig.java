package com.employee.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "User API", version = "1.0"), security = @SecurityRequirement(name = "bearerAuth"))
// enable the authorize button and security is applied to all end points
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@Configuration
public class OpenAPIConfig {

//	    @Bean
//	    public OpenAPI customOpenAPI() {
//	        return new OpenAPI()
//	            .components(new Components()
//	                .addSecuritySchemes("bearerAuth",
//	                    new SecurityScheme()
//	                        .type(SecurityScheme.Type.HTTP)
//	                        .scheme("bearer")
//	                        .bearerFormat("JWT")));
//	    }
}
