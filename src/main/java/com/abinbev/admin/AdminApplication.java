package com.abinbev.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@EnableDiscoveryClient
@SpringBootApplication
public class AdminApplication  {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
