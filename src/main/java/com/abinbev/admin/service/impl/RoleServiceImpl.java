package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.utility.MapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	RoleDAO roleDAO;

	MapperUtil<RoleDto, Role> roleMapper = new MapperUtil<>();
	MapperUtil<Role, RoleResponseDto> roleResponse = new MapperUtil<>();

	@Autowired
	private ObjectMapper mapper;

	
	/**
	 * In this method we can create a role
	 */
	@Override
	public RoleResponseDto saveRole(RoleDto roleDto) throws RoleCreationFailureException {
		RoleResponseDto response = null;
		Role role = roleMapper.transfer(roleDto, Role.class);
		role.setStatus(messageProperties.getActiveStatus());
		role.setCreatedDate(new Date());
		Role roleResponseObj = roleDAO.save(role);
		if (roleResponseObj != null) {

			response = roleResponse.transfer(roleResponseObj, RoleResponseDto.class);
			response.setMessage(messageProperties.getSaveMessage());
		} else {
			throw new RoleCreationFailureException(messageProperties.getUserSaveFailureMessage());
		}

		return response;

	}

	/**
	 * In this method we can delete a role
	 */
	@Override
	public void deleteRole(String roleId) throws RoleNotFoundException {

		Role role = findRoleByRoleId(roleId);

		role.setStatus(messageProperties.getInactiveStatus());
		roleDAO.save(role);

	}

	/**
	 * In this method we can list all roles
	 */
	@Override
	public List<RoleResponseDto> getAllRoles() {

		List<RoleResponseDto> roleResponseList = new ArrayList<RoleResponseDto>();

		List<Role> roles = roleDAO.getAllRoles();
		try {
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				RoleResponseDto response = roleResponse.transfer(role, RoleResponseDto.class);
				roleResponseList.add(response);

			}
		}
		return roleResponseList;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			roleResponseList = null;
		}
		
		return null;
		
	}

	
	/**
	 * In this method we can update a role
	 */
	@Override
	public RoleResponseDto updateRole(RoleDto roleDto)
			throws RoleNotFoundException {
		RoleResponseDto response = null;
		Role existingRole = findRoleByRoleId(roleDto.getRoleId());

		Role role = roleMapper.transfer(roleDto, Role.class);
		
		role.setId(existingRole.getId());

		role.setCreatedDate(existingRole.getCreatedDate());

		role.setModifiedDate(new Date());

		Role roleResponseObj = roleDAO.save(role);

		if (roleResponseObj != null) {
			response = roleResponse.transfer(roleResponseObj, RoleResponseDto.class);

			response.setMessage(messageProperties.getUpdationMessage());
		} else {
			// throw new
			// roleCreationFailureException(messageProperties.getUserSaveFailureMessage());//roleupdateFailure
		}

		return response;

	}

	
	/**
	 * In this method we can get a role by role id
	 */
	@Override
	public RoleResponseDto getRole(String roleId) throws RoleNotFoundException {
		Role existingRole = findRoleByRoleId(roleId);

		RoleResponseDto response = roleResponse.transfer(existingRole, RoleResponseDto.class);
		return response;
	}

	private Role findRoleByRoleId(String roleId) throws RoleNotFoundException {
		Role existingRole = roleDAO.findByRoleId(roleId);

		if (existingRole == null) {
			throw new RoleNotFoundException(messageProperties.getRoleNotfoundMessage());

		}
		return existingRole;

	}

	private String mapToJson(Object object) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);

	}

}
