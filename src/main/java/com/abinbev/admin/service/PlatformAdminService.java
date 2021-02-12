package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;

public interface PlatformAdminService {

	 public UserResponseDto saveUser(UserDto userDto);

	 public UserResponseDto updateUser(UserDto userDto);
	 
	 public List<UserResponseDto> getAllUsers();

	 public void deleteUser(String uuid);

	
}
