package com.abinbev.admin.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.UserAlreadyExistsException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.requestDto.SignupDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.ErrorResponse;
import com.abinbev.admin.responseDto.LoginResponseDto;
import com.abinbev.admin.responseDto.SignupResponseDto;
import com.abinbev.admin.responseDto.SuccessResponse;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.EmailService;
import com.abinbev.admin.service.LoginService;
import com.abinbev.admin.utility.EncryptionUtil;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	EmailService emailService;

	@Autowired
	EncryptionUtil encryptionUtil;

	@Autowired
	UserDAO userDAO;

	MapperUtil<User, UserResponseDto> userResponse = new MapperUtil<>();

	@Autowired
	MessageProperties messageProperties;
	@Autowired
	ErrorCodes errorCodes;

	@Override
	public BasicResponse<LoginResponseDto> login(LoginDto loginDto) {
		UserResponseDto response = null;

		User user = userDAO.findByEmail(loginDto.getUsername());

		user.setPassword(encryptionUtil.decrypt(user.getPassword()));

		if (user != null && user.getPassword().contentEquals(loginDto.getPassword())) {

			LoginResponseDto loginResponseDto = new LoginResponseDto(user.getEmailId(),user.getPassword());
			BasicResponse<LoginResponseDto> basicResponse = new BasicResponse<LoginResponseDto>();
			ErrorResponse error = new ErrorResponse(null, null);
			basicResponse.setError(error);
			SuccessResponse message = new SuccessResponse(messageProperties.getLoginSuccessMessage(),null);
			basicResponse.setMessage(message);
		
			basicResponse.setData(loginResponseDto);
			return basicResponse;
		}
		return null;

	}

	@Override
	public BasicResponse<SignupResponseDto> signup(SignupDto signupDto) throws UserAlreadyExistsException {

		UserResponseDto response = null;

		BasicResponse<SignupResponseDto> basicResponse = null;

		User user = userDAO.findByEmail(signupDto.getUsername());

		if (user != null) {

			if (user.getPassword() == null) {

				user.setId(user.getId());
				user.setCreatedBy(user.getCreatedBy());
				user.setCreatedDate(user.getCreatedDate());
				user.setStatus(user.getStatus());
				user.setModifiedBy(user.getEmailId());
				user.setModifiedDate(new Date());

				user.setPassword(encryptionUtil.encrypt(signupDto.getPassword()));
				User userObj = userDAO.save(user);

				SignupResponseDto signupResponseDto = new SignupResponseDto(userObj.getEmailId(),userObj.getPassword());

				basicResponse = new BasicResponse<SignupResponseDto>();
				SuccessResponse message = new SuccessResponse(messageProperties.getSignupSuccessMessage(),null);
				basicResponse.setMessage(message);
				
				basicResponse.setData(signupResponseDto);

			}

			else
				throw new UserAlreadyExistsException(errorCodes.getEmailExist());
		} else
			throw new UserNotFoundException(errorCodes.getUserNotFound());
		return basicResponse;
	}

}
