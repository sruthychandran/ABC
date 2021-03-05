package com.abinbev.admin.service;

import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.requestDto.SignupDto;

public interface LoginService {

	void login(LoginDto loginDto);

	void signup(SignupDto signupDto);
	
}
