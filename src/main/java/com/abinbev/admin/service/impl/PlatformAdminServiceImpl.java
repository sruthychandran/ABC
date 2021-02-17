package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abinbev.admin.dao.UserDAO;

import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.exception.NotFoundException;

import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.abinbev.admin.utility.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlatformAdminServiceImpl implements PlatformAdminService {

	@Autowired
	UserDAO userDAO;

	@Value("${message.create}")
	String creationMessage;
	@Value("${message.update}")
	String updationMessage;
	@Value("${message.delete}")
	String deletionMessage;

	MapperUtil<UserDto, User> toUser = new MapperUtil<>();
	MapperUtil<User, UserResponseDto> toUserResponse = new MapperUtil<>();

	@Override
	public UserResponseDto saveUser(UserDto userDto) throws DuplicateEmailException {
		User user = toUser.transfer(userDto, User.class);
		if (emailExist(user.getEmailId())) {
			throw new DuplicateEmailException("The email address: " + user.getEmailId() + " is already in use.");
		}
		user.setCreatedDate(new Date());
		user.setCreatedBy(user.getEmailId());
		user.setStatus("enabled");
		user = userDAO.save(user);
		UserResponseDto response = toUserResponse.transfer(user, UserResponseDto.class);
		response.setMessage(creationMessage);
		return response;

	}

	private boolean emailExist(String email) {
		User user = userDAO.findByEmail(email);

		if (user != null) {
			return true;
		}

		return false;
	}

	@Override
	public UserResponseDto updateUser(UserDto userDto) throws NotFoundException {
		User user = toUser.transfer(userDto, User.class);
	
		User existingUser = userDAO.findByEmail(userDto.getEmailId());
		if (existingUser == null) {
			throw new NotFoundException("user not found");
		}
		user.setCreatedBy(existingUser.getCreatedBy());
		user.setCreatedDate(existingUser.getCreatedDate());
		user.setStatus(existingUser.getStatus());
		user.setModifiedBy(userDto.getEmailId());
		user.setModifiedDate(new Date());
		user = userDAO.save(user);
		UserResponseDto response = toUserResponse.transfer(user, UserResponseDto.class);
		response.setMessage(updationMessage);
		return response;

	}

	@Override
	public List<UserResponseDto> getAllUsers() {

		List<UserResponseDto> userResponses = new ArrayList<UserResponseDto>();

		List<User> users = userDAO.getAllUsers();

		for (User user : users) {
			UserResponseDto response = toUserResponse.transfer(user, UserResponseDto.class);
			userResponses.add(response);

			
		}

		return userResponses;
	}

	@Override
	public void deleteUser(String uuid) {

		userDAO.deleteUser(uuid);
	}
	
	public void test() {
		userDAO.deleteAll();
	}

}
