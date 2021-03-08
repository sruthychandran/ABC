package com.abinbev.admin.service;

import com.abinbev.admin.exception.UserAlreadyExistsException;
import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.requestDto.SignupDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.UserResponseDto;

public interface LoginService {

	BasicResponse<UserResponseDto> login(LoginDto loginDto);

	BasicResponse<UserResponseDto> signup(SignupDto signupDto) throws UserAlreadyExistsException;
	
}
