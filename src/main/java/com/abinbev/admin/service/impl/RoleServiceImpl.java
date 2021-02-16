package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.utility.MapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

	@Value("${message.create}")
	String creationMessage;
	@Value("${message.update}")
	String updationMessage;
	@Value("${message.delete}")
	String deletionMessage;

	@Autowired
	RoleDAO roleDAO;

	MapperUtil<RoleDto, Role> toRole = new MapperUtil<>();
	MapperUtil<Role, RoleResponseDto> toRoleResponse = new MapperUtil<>();
	

	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public RoleResponseDto saveRole(RoleDto roleDto) {
		Role role = toRole.transfer(roleDto, Role.class);
		role.setStatus("enabled");
		// role.setCreatedBy(existingRole.getCreatedBy());
		role.setCreatedDate(new Date());
		role = roleDAO.save(role);
		RoleResponseDto response = toRoleResponse.transfer(role, RoleResponseDto.class);
		response.setMessage(creationMessage);
		return response;

		
	}

	@Override
	public void deleteRole(String roleId) {

		roleDAO.deleteRole(roleId);

	}

	@Override
	public List<RoleResponseDto> getAllRoles() {

		List<RoleResponseDto> roleResponses = new ArrayList<RoleResponseDto>();

		List<Role> roles = roleDAO.getAllRoles();

		for (Role role : roles) {
			RoleResponseDto response = toRoleResponse.transfer(role, RoleResponseDto.class);
			roleResponses.add(response);

		}

		return roleResponses;
	}

	@Override
	public RoleResponseDto updateRole(RoleDto roleDto) throws NotFoundException, JsonMappingException, JsonProcessingException {
		log.info("enter in to updateRole : {}", roleDto);

		
		Role existingRole = roleDAO.findByRoleId(roleDto.getRoleId());
		
		if (existingRole == null) {
			throw new NotFoundException("Role not found");
			
		}
		Role role = toRole.transfer(roleDto, Role.class);
		

		// role.setCreatedBy(existingRole.getCreatedBy());
		role.setCreatedDate(existingRole.getCreatedDate());

		// role.setModifiedBy();
		role.setModifiedDate(new Date());

		role = roleDAO.save(role);
		
		RoleResponseDto response =   mapper.readValue(mapToJson(role), RoleResponseDto.class);
		//RoleResponseDto response = toRoleResponse.transfer(role, RoleResponseDto.class);
		response.setMessage(updationMessage);
		log.info("output updateRole : {}", response);
		return response;

	}

	

	
	private String mapToJson(Object object) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);

	}
	
}
