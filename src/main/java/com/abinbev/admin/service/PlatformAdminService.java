package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.PlatformAdminOnBoardingDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.PlatformAdminOnBoardingResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;

public interface PlatformAdminService {

	public UserResponseDto saveUser(UserDto userDto) throws EmailExistException, UserCreationFailureException;

	public UserResponseDto updateUser(UserDto userDto) throws UserNotFoundException, UserUpdationFailureException;

	public List<UserResponseDto> getAllUsers();

	public void deleteUser(String uuid) throws UserNotFoundException;

	UserResponseDto findByEmailId(String emailId) throws UserNotFoundException;

	public PlatformAdminOnBoardingResponseDto savePlatformAdmin(PlatformAdminOnBoardingDto platformAdminOnBoardingDto)
			throws EmailExistException, UserCreationFailureException;

}
