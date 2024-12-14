package com.soa.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Stanislav Hlova
 */
@Configuration
public class OpenApiConfig {


    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .description("API documentation for the project")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("youremail@example.com")
                                .url("http://yourcompany.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}