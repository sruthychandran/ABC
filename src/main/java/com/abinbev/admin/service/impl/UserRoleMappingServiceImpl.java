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
import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.dao.UserRoleMappingDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.UserRoleMapping;
//import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserRoleMappingDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.ErrorResponse;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.service.UserRoleMappingService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

@Service

public class UserRoleMappingServiceImpl implements UserRoleMappingService {

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	UserRoleMappingDAO userRoleMappingDAO;

	MapperUtil<RoleDto, Role> roleMapper = new MapperUtil<>();
	MapperUtil<Role, RoleResponseDto> roleResponse = new MapperUtil<>();

	@Autowired
	private ErrorCodes errorCodes;

	/**
	 * In this method we can create a role
	 */
	@Override
	public UserRoleMapping save(UserRoleMapping userRoleMapping)  {
		RoleResponseDto response = null;
		


		UserRoleMapping userRoleMappingObj = userRoleMappingDAO.save(userRoleMapping);
	
		return userRoleMappingObj;

	}

	
	

}
