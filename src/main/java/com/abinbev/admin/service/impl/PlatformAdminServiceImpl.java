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
import com.abinbev.admin.requestDto.PlatformAdminOnBoardingDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.ErrorResponse;
import com.abinbev.admin.responseDto.PlatformAdminOnBoardingResponseDto;
import com.abinbev.admin.responseDto.SuccessResponse;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.EmailService;
import com.abinbev.admin.service.PlatformAdminService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlatformAdminServiceImpl implements PlatformAdminService {

	@Autowired
	UserDAO userDAO;

	@Autowired
	EmailService emailService;

	@Autowired
	MessageProperties messageProperties;

	MapperUtil<UserDto, User> userMapper = new MapperUtil<>();

	MapperUtil<User, UserResponseDto> userResponse = new MapperUtil<>();

	MapperUtil<PlatformAdminOnBoardingDto, User> platformAdminMapper = new MapperUtil<>();

	MapperUtil<User, PlatformAdminOnBoardingResponseDto> platformAdminResponse = new MapperUtil<>();

	@Autowired
	private ErrorCodes errorCodes;

	/**
	 * In this method platform admin can create a user
	 */
	@Override
	public BasicResponse<UserResponseDto> saveUser(UserDto userDto)
			throws EmailExistException, UserCreationFailureException {
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

			emailService.sendWelcomeMail(userResponseObj.getEmailId(), userResponseObj.getFirstName());
			response = userResponse.transfer(userResponseObj, UserResponseDto.class);

		} else {
			throw new UserCreationFailureException(errorCodes.getUserSaveFailure());
		}
		BasicResponse<UserResponseDto> basicResponse = new BasicResponse<UserResponseDto>();

		basicResponse.setData(response);

		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getPlatformAdminSaveSuccessMessage(),
				messageProperties.getPlatformAdminSaveSuccesCode());
		basicResponse.setMessage(message);

		return basicResponse;

	}

	private boolean emailExist(String email) {
		User user = userDAO.findByEmail(email);

		if (user != null) {
			return true;
		}

		return false;
	}

	@Override
	public BasicResponse<UserResponseDto> updateUser(UserDto userDto)
			throws UserNotFoundException, UserUpdationFailureException {
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
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getPlatformAdminUpdateSuccessMessage(),
				messageProperties.getPlatformAdminUpdateSuccesCode());
		basicResponse.setMessage(message);

		basicResponse.setData(response);
		return basicResponse;

	}

	/**
	 * In this method platform admin can list all users
	 */

	@Override
	public BasicResponse<Page<UserResponseDto>> getAllUsers(Pageable pageable) {
		BasicResponse<Page<UserResponseDto>> basicResponse = new BasicResponse<Page<UserResponseDto>>();
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

			} else {
				ErrorResponse error = new ErrorResponse(messageProperties.getNoContentErrorCode(),
						messageProperties.getNoContentErrorMessage());
				basicResponse.setError(error);
				return basicResponse;
			}
			ErrorResponse error = new ErrorResponse(null, null);
			basicResponse.setError(error);
			SuccessResponse message = new SuccessResponse(messageProperties.getPlatformAdminRetrieveSuccessMessage(),
					messageProperties.getPlatformAdminRetrieveSuccesCode());
			basicResponse.setMessage(message);

			basicResponse.setData(userResponsePage);
			return basicResponse;
		} catch (Exception ex) {

		} finally {
			userResponsePage = null;
		}

		return null;
	}

	/**
	 * In this method platform admin can delete a user
	 */
	@Override
	public BasicResponse<UserResponseDto> deleteUser(String emailId) throws UserNotFoundException {
		User user = findUserByEmail(emailId);

		user.setStatus(messageProperties.getInactiveStatus());
		user.setModifiedDate(new Date());
		User userObj = userDAO.save(user);
		UserResponseDto response = userResponse.transfer(userObj, UserResponseDto.class);
		ErrorResponse error = new ErrorResponse(null, null);
		BasicResponse<UserResponseDto> basicResponse = new BasicResponse<UserResponseDto>();
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getPlatformAdminDeleteSuccessMessage(),
				messageProperties.getPlatformAdminDeleteSuccesCode());
		basicResponse.setMessage(message);

		basicResponse.setData(response);
		return basicResponse;
	}

	/**
	 * In this method platform admin can get a user by email
	 */
	@Override
	public BasicResponse<UserResponseDto> findByEmailId(String emailId) throws UserNotFoundException {
		User user = findUserByEmail(emailId);
		UserResponseDto response = userResponse.transfer(user, UserResponseDto.class);
		BasicResponse<UserResponseDto> basicResponse = new BasicResponse<UserResponseDto>();
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getPlatformAdminRetrieveSuccessMessage(),
				messageProperties.getPlatformAdminRetrieveSuccesCode());
		basicResponse.setMessage(message);

		basicResponse.setData(response);

		return basicResponse;

	}

	private User findUserByEmail(String email) throws UserNotFoundException {
		User existingUser = userDAO.findByEmail(email);
		if (existingUser == null) {
			throw new UserNotFoundException(errorCodes.getUserNotFound());
		}
		return existingUser;
	}

	@Override
	public BasicResponse<PlatformAdminOnBoardingResponseDto> savePlatformAdmin(
			PlatformAdminOnBoardingDto platformAdminOnBoardingDto)
			throws EmailExistException, UserCreationFailureException {

		PlatformAdminOnBoardingResponseDto response = null;
		User user = platformAdminMapper.transfer(platformAdminOnBoardingDto, User.class);
		if (emailExist(user.getEmailId())) {
			throw new EmailExistException(errorCodes.getEmailExist());
		}
		user.setCreatedDate(new Date());
		user.setCreatedBy(user.getEmailId());
		user.setStatus(messageProperties.getActiveStatus());
		User userResponseObj = userDAO.save(user);

		if (userResponseObj != null) {
			response = platformAdminResponse.transfer(userResponseObj, PlatformAdminOnBoardingResponseDto.class);

		} else {
			throw new UserCreationFailureException(errorCodes.getUserSaveFailure());
		}
		BasicResponse<PlatformAdminOnBoardingResponseDto> basicResponse = new BasicResponse<PlatformAdminOnBoardingResponseDto>();

		basicResponse.setData(response);

		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getPlatformAdminSaveSuccessMessage(),
				messageProperties.getPlatformAdminSaveSuccesCode());
		basicResponse.setMessage(message);

		return basicResponse;

	}

}
