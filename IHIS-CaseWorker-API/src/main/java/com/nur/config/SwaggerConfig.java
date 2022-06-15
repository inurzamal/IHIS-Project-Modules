package com.nur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket apiDoc() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				   .groupName("Public APIs")
				   .apiInfo(getApiInfo())
				   .select()
				   .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))			   
				   .build();
	}

	public ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Case Worker APIs")
		.description("API is created by Nur").version("1").build();
	}
}