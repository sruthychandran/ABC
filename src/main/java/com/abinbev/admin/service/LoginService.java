package com.abinbev.admin.service;

import com.abinbev.admin.exception.UserAlreadyExistsException;
import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.requestDto.SignupDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.LoginResponseDto;
import com.abinbev.admin.responseDto.SignupResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;

public interface LoginService {

	BasicResponse<LoginResponseDto> login(LoginDto loginDto);

	BasicResponse<SignupResponseDto> signup(SignupDto signupDto) throws UserAlreadyExistsException;
	
}
