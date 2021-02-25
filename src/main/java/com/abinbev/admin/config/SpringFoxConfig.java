package com.abinbev.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class SpringFoxConfig extends WebMvcConfigurationSupport {
	
	
	@Bean
	public Docket api10() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .groupName("Admin v1")
	        .select()
	            .apis(RequestHandlerSelectors.basePackage("com.abinbev.admin.controller"))
	            .paths(PathSelectors.ant("/*/v1/*"))
	        .build()
	        .apiInfo(new ApiInfoBuilder().version("1.0").title("PlatformAdminController API").description("Documentation Admin Service API v1.0").build());
	}
	@Bean
	public Docket api12() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .groupName("Admin v2")
	        .select()
	            .apis(RequestHandlerSelectors.basePackage("com.abinbev.admin.controller"))
	            .paths(PathSelectors.ant("/*/v2/*"))
	        .build()
	        .apiInfo(new ApiInfoBuilder().version("2.0").title("PlatformAdminController API").description("Documentation Admin Service API v2.0").build());
	}
	
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
}

