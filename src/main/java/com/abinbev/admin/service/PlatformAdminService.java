package com.abinbev.admin.service;

import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;

public interface PlatformAdminService {

	 public UserResponseDto saveUser(UserDto userDto);

	public UserResponseDto updateUser(UserDto userDto);

	

	
}
