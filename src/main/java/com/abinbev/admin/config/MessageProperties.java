package com.abinbev.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:message.properties")
public class MessageProperties {
	@Value("${message.create}")
	String saveMessage;
	@Value("${message.update}")
	String updationMessage;
	@Value("${message.delete}")
	String deletionMessage;
	@Value("${user.status.active}")
	String activeStatus;
	@Value("${user.status.inactive}")
	String inactiveStatus;
	@Value("${user.save.failure}")
	String userSaveFailureMessage;
	@Value("${user.email.exist}")
	String userEmailExistMessage;
	@Value("${user.notfound}")
	String userNotfoundMessage;

	@Value("${role.notfound}")
	String roleNotfoundMessage;
	@Value("${role.save.failure}")
	String roleSaveFailureMessage;
	@Value("${categoryService.save.failure}")
	String categoryServiceSaveFailureMessage;
	@Value("${role.notfound}")
	String CategoryServiceNotFoundMessage;
}
