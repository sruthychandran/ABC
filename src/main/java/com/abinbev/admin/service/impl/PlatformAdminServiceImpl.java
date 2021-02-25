package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.PlatformAdminOnBoardingDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.PlatformAdminOnBoardingResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.abinbev.admin.utility.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlatformAdminServiceImpl implements PlatformAdminService {

	@Autowired
	UserDAO userDAO;
	@Autowired
	MessageProperties messageProperties;

	MapperUtil<UserDto, User> userMapper = new MapperUtil<>();

	MapperUtil<User, UserResponseDto> userResponse = new MapperUtil<>();

	MapperUtil<PlatformAdminOnBoardingDto, User> platformAdminMapper = new MapperUtil<>();

	MapperUtil<User, PlatformAdminOnBoardingResponseDto> platformAdminResponse = new MapperUtil<>();

	/**
	 * In this method platform admin can create a user
	 */
	@Override
	public UserResponseDto saveUser(UserDto userDto) throws EmailExistException, UserCreationFailureException {
		UserResponseDto response = null;
		User user = userMapper.transfer(userDto, User.class);
		if (emailExist(user.getEmailId())) {
			throw new EmailExistException(messageProperties.getUserEmailExistMessage());
		}
		user.setCreatedDate(new Date());
		user.setCreatedBy(user.getEmailId());
		user.setStatus(messageProperties.getActiveStatus());
		User userResponseObj = userDAO.save(user);
		if (userResponseObj != null) {
			response = userResponse.transfer(userResponseObj, UserResponseDto.class);

			response.setMessage(messageProperties.getSaveMessage());

		} else {
			throw new UserCreationFailureException(messageProperties.getUserSaveFailureMessage());
		}

		return response;

	}

	private boolean emailExist(String email) {
		User user = userDAO.findByEmail(email);

		if (user != null) {
			return true;
		}

		return false;
	}

	/**
	 * In this method platform admin can update a user
	 */
	@Override
	public UserResponseDto updateUser(UserDto userDto) throws UserNotFoundException, UserUpdationFailureException {
		UserResponseDto response = null;
		User user = userMapper.transfer(userDto, User.class);

		User existingUser = findUserByEmail(userDto.getEmailId());

		user.setId(existingUser.getId());
		user.setCreatedBy(existingUser.getCreatedBy());
		user.setCreatedDate(existingUser.getCreatedDate());
		user.setStatus(existingUser.getStatus());
		user.setModifiedBy(userDto.getEmailId());
		user.setModifiedDate(new Date());
		User userResponseObj = userDAO.save(user);
		if (userResponseObj != null) {
			response = userResponse.transfer(userResponseObj, UserResponseDto.class);

			response.setMessage(messageProperties.getUpdationMessage());
		} else {
			throw new UserUpdationFailureException(messageProperties.getUserUpdateFailureMessage());
		}

		return response;

	}

	/**
	 * In this method platform admin can list all users
	 */

	@Override
	public List<UserResponseDto> getAllUsers() {

		List<UserResponseDto> userResponses = new ArrayList<UserResponseDto>();

		List<User> users = userDAO.getAllUsers();
		try {
			if (users != null && !users.isEmpty()) {
				for (User user : users) {
					UserResponseDto response = userResponse.transfer(user, UserResponseDto.class);
					userResponses.add(response);

				}
			}
			return userResponses;
		} catch (Exception ex) {

		} finally {
			userResponses = null;
		}

		return null;
	}

	/**
	 * In this method platform admin can delete a user
	 */
	@Override
	public void deleteUser(String emailId) throws UserNotFoundException {
		User user = findUserByEmail(emailId);

		user.setStatus(messageProperties.getInactiveStatus());
		userDAO.save(user);

	}

	/**
	 * In this method platform admin can get a user by email
	 */
	@Override
	public UserResponseDto findByEmailId(String emailId) throws UserNotFoundException {
		User user = findUserByEmail(emailId);
		UserResponseDto response = userResponse.transfer(user, UserResponseDto.class);
		return response;
	}

	private User findUserByEmail(String email) throws UserNotFoundException {
		User existingUser = userDAO.findByEmail(email);
		if (existingUser == null) {
			throw new UserNotFoundException(messageProperties.getUserNotfoundMessage());
		}
		return existingUser;
	}

	@Override
	public PlatformAdminOnBoardingResponseDto savePlatformAdmin(PlatformAdminOnBoardingDto platformAdminOnBoardingDto)
			throws EmailExistException, UserCreationFailureException {

		PlatformAdminOnBoardingResponseDto response = null;
		User user = platformAdminMapper.transfer(platformAdminOnBoardingDto, User.class);
		if (emailExist(user.getEmailId())) {
			throw new EmailExistException(messageProperties.getUserEmailExistMessage());
		}
		user.setCreatedDate(new Date());
		user.setCreatedBy(user.getEmailId());
		user.setStatus(messageProperties.getActiveStatus());
		User userResponseObj = userDAO.save(user);

		if (userResponseObj != null) {
			response = platformAdminResponse.transfer(userResponseObj, PlatformAdminOnBoardingResponseDto.class);

			response.setMessage(messageProperties.getSaveMessage());

		} else {
			throw new UserCreationFailureException(messageProperties.getUserSaveFailureMessage());
		}

		return response;
	}

}
