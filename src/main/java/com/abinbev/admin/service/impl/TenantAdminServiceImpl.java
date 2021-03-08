package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.TenantAdminService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TenantAdminServiceImpl implements TenantAdminService {

	@Autowired
	UserDAO userDAO;
	@Autowired
	MessageProperties messageProperties;

	MapperUtil<UserDto, User> userMapper = new MapperUtil<>();
	MapperUtil<User, UserResponseDto> userResponse = new MapperUtil<>();
	@Autowired
	  private ErrorCodes errorCodes;
	/**
	 * In this method tenant admin can create a user
	 */
	@Override
	public BasicResponse<UserResponseDto> saveUser(UserDto userDto) throws EmailExistException, UserCreationFailureException {
		UserResponseDto response = null;
		User user = userMapper.transfer(userDto, User.class);
		if (emailExist(user.getEmailId())) {
			throw new EmailExistException(errorCodes.getEmailExist());
		}
		user.setCreatedDate(new Date());
		user.setCreatedBy(user.getEmailId());
		user.setStatus(messageProperties.getActiveStatus());
		User userResponseObj = userDAO.save(user);
		if (userResponseObj != null) {
			response = userResponse.transfer(userResponseObj, UserResponseDto.class);

		} else {
			throw new UserCreationFailureException(errorCodes.getUserSaveFailure());
		}
		
		BasicResponse<UserResponseDto> basicResponse = new BasicResponse<UserResponseDto>();
		//basicResponse.setMessage(messageProperties.getSaveMessage());
		basicResponse.setData(response);

		return basicResponse;

	}

	private boolean emailExist(String email) {
		User user = userDAO.findByEmail(email);

		if (user != null) {
			return true;
		}

		return false;
	}

	/**
	 * In this method tenant admin can update a user
	 * 
	 * @throws UserUpdationFailureException
	 */
	@Override
	public BasicResponse<UserResponseDto> updateUser(UserDto userDto) throws UserNotFoundException, UserUpdationFailureException {
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

		} else {
			throw new UserUpdationFailureException(errorCodes.getUserUpdateFailure());
		}
		BasicResponse<UserResponseDto> basicResponse = new BasicResponse<UserResponseDto>();
		//basicResponse.setMessage(messageProperties.getSaveMessage());
		basicResponse.setData(response);

		return basicResponse;

	}

	/**
	 * In this method tenant admin can list all users
	 */
	@Override
	public BasicResponse<Page<UserResponseDto>> getAllUsers(Pageable pageable) {
		Page<UserResponseDto> userResponsePage = null;

		List<UserResponseDto> userResponses = new ArrayList<UserResponseDto>();

		Page<User> users = userDAO.getAllUsers(pageable);
		
		
		try {
			if (users != null && !users.isEmpty()) {
				for (User user : users.getContent()) {
					UserResponseDto response = userResponse.transfer(user, UserResponseDto.class);
					userResponses.add(response);

				}
				
				userResponsePage = new PageImpl<UserResponseDto>(userResponses, pageable, users.getContent().size());
			
			}
		
			//return userResponsePage;
		} catch (Exception ex) {

		} finally {
			userResponsePage = null;
		}

		return null;
	}

	/**
	 * In this method tenant admin can delete a user
	 */
	@Override
	public void deleteUser(String emailId) throws UserNotFoundException {
		User user = findUserByEmail(emailId);

		user.setStatus(messageProperties.getInactiveStatus());
		userDAO.save(user);

	}

	/**
	 * In this method tenant admin can find a user by mail id
	 * 
	 * @throws UserNotFoundException
	 */
	@Override
	public BasicResponse<UserResponseDto> findByEmailId(String emailId) throws UserNotFoundException {
		User user = findUserByEmail(emailId);
		UserResponseDto response = userResponse.transfer(user, UserResponseDto.class);
		//return response;
		return null;
	}

	private User findUserByEmail(String email) throws UserNotFoundException {
		User existingUser = userDAO.findByEmail(email);
		if (existingUser == null) {
			throw new UserNotFoundException(errorCodes.getUserNotFound());
		}
		return existingUser;
	}

}
