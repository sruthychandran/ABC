package com.abinbev.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.service.EmailService;
import com.abinbev.admin.service.LoginService;

@Service

public class LoginServiceImpl implements LoginService {

	
	@Autowired
	EmailService emailService;
	
	
	@Override
	public void login(LoginDto loginDto) {
		
		emailService.sendWelcomeMail(loginDto.getEmailId(), loginDto.getFirstName());
	}

	
}
