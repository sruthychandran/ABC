package com.abinbev.admin.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.UserAlreadyExistsException;
import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.requestDto.SignupDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.EmailService;
import com.abinbev.admin.service.LoginService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	EmailService emailService;

	@Autowired
	UserDAO userDAO;

	MapperUtil<User, UserResponseDto> userResponse = new MapperUtil<>();

	@Autowired
	MessageProperties messageProperties;
	@Autowired
	ErrorCodes errorCodes;

	@Override
	public BasicResponse<UserResponseDto> login(LoginDto loginDto) {
		UserResponseDto response = null;

		User user = userDAO.findByEmail(loginDto.getUsername());

		if (user != null && user.getPassword().contentEquals(loginDto.getPassword())) {

			response = userResponse.transfer(user, UserResponseDto.class);
			BasicResponse<UserResponseDto> basicResponse = new BasicResponse<UserResponseDto>();
			basicResponse.setMessage(messageProperties.getLoginSuccessMessage());
			basicResponse.setData(response);
			return basicResponse;
		}
		return null;

	}

	@Override
	public BasicResponse<UserResponseDto> signup(SignupDto signupDto) throws UserAlreadyExistsException {

		UserResponseDto response = null;

		BasicResponse<UserResponseDto> basicResponse = null;

		User user = userDAO.findByEmail(signupDto.getUsername());

		if (user.getPassword() == null) {

			user.setId(user.getId());
			user.setCreatedBy(user.getCreatedBy());
			user.setCreatedDate(user.getCreatedDate());
			user.setStatus(user.getStatus());
			user.setModifiedBy(user.getEmailId());
			user.setModifiedDate(new Date());

			user.setPassword(signupDto.getPassword());
			userDAO.save(user);

			response = userResponse.transfer(user, UserResponseDto.class);

			basicResponse = new BasicResponse<UserResponseDto>();
			basicResponse.setMessage(messageProperties.getSignupSuccessMessage());
			basicResponse.setData(response);

		}

		else
			throw new UserAlreadyExistsException(errorCodes.getEmailExist());

		return basicResponse;
	}

}
