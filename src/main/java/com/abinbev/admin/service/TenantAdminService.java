package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface TenantAdminService {

	 public UserResponseDto saveUser(UserDto userDto) throws DuplicateEmailException;

	 public UserResponseDto updateUser(UserDto userDto) throws NotFoundException;
	 
	 public List<UserResponseDto> getAllUsers();

	 public void deleteUser(String uuid);
	 
	
}
