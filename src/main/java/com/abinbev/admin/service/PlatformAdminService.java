package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.exception.NotFoundException;

import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;

public interface PlatformAdminService {

	 public UserResponseDto saveUser(UserDto userDto) throws DuplicateEmailException;

	 public UserResponseDto updateUser(UserDto userDto) throws  NotFoundException;
	 
	 public List<UserResponseDto> getAllUsers();

	 public void deleteUser(String uuid);
     
	 public void test();

	UserResponseDto findByEmailId(String emailId);
	
}
