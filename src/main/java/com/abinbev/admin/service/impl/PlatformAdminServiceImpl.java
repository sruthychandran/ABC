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
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.abinbev.admin.utility.MapperUtil;

@Service
public class PlatformAdminServiceImpl implements PlatformAdminService {

	@Autowired
	UserDAO userDAO;

	MapperUtil<UserDto, User> toUser = new MapperUtil<>();
	MapperUtil<User, UserResponseDto> toUserResponse = new MapperUtil<>();

	@Value("${message.create}")
	String creationMessage;
	@Value("${message.update}")
	String updationMessage;
	@Value("${message.delete}")
	String deletionMessage;

	@Override
	public UserResponseDto saveUser(UserDto userDto) {
		User user = toUser.transfer(userDto, User.class);
		user.setCreatedDate(new Date());
		user.setCreatedBy(user.getEmailId());
		user = userDAO.save(user);
		UserResponseDto response = toUserResponse.transfer(user, UserResponseDto.class);
		response.setMessage(creationMessage);
		return response;
	}

	@Override
	public UserResponseDto updateUser(UserDto userDto) {
		User user = toUser.transfer(userDto, User.class);
		user.setModifiedBy(userDto.getEmailId());

		user.setModifiedDate(new Date());
		UserResponseDto response = toUserResponse.transfer(user, UserResponseDto.class);
		user = userDAO.save(user);
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

}
