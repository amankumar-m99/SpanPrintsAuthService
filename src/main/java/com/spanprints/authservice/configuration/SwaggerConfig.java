package com.spanprints.authservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI apiInfo() {
		return new OpenAPI().info(
				new Info().title("Auth Service").description("This is auth service for SpanPrints").version("1.0"));
	}
}
