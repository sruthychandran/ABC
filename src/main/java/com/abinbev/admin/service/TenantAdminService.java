package com.abinbev.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.UserResponseDto;

public interface TenantAdminService {

	public BasicResponse<UserResponseDto> saveUser(UserDto userDto) throws EmailExistException, UserCreationFailureException;

	public BasicResponse<UserResponseDto> updateUser(UserDto userDto) throws UserNotFoundException, UserUpdationFailureException;

	public void deleteUser(String uuid) throws UserNotFoundException;

	public BasicResponse<UserResponseDto> findByEmailId(String emailId) throws UserNotFoundException;

	public BasicResponse<Page<UserResponseDto>> getAllUsers(Pageable pageable);

}
