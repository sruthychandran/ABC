package com.abinbev.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@EnableDiscoveryClient
@SpringBootApplication
public class AdminApplication /* extends SpringBootServletInitializer */ {
	/*
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder application) { return
	 * application.sources(AdminApplication.class); }
	 */
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
