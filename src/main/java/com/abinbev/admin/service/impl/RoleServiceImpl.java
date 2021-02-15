package com.abinbev.admin.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.utility.MapperUtil;





@Service
public class RoleServiceImpl implements RoleService {

	
	@Value("${message.create}")
	String creationMessage;
	@Value("${message.update}")
	String updationMessage;
	@Value("${message.delete}")
	String deletionMessage;

	@Autowired
	RoleDAO  roleDAO;
	
	
	
	MapperUtil<RoleDto,Role> toRole = new MapperUtil<>();
	MapperUtil<Role, RoleResponseDto> toRoleResponse = new MapperUtil<>();


	@Override
	public RoleResponseDto saveRole(RoleDto roleDto) {
		Role role=toRole.transfer(roleDto,Role.class);
		role.setStatus(true);
	   role= roleDAO.save(role); 
		 RoleResponseDto response=toRoleResponse.transfer(role,RoleResponseDto.class);
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
			 RoleResponseDto response=toRoleResponse.transfer(role,RoleResponseDto.class);
			 roleResponses.add(response);

		}

		return roleResponses;
	}
	
	
	@Override
	public RoleResponseDto updateRole(RoleDto roleDto) {
		Role role=toRole.transfer(roleDto,Role.class);
		 //roleDAo.findByRoleId()
	     role= roleDAO.save(role); 
		 RoleResponseDto response=toRoleResponse.transfer(role,RoleResponseDto.class);
		 response.setMessage(updationMessage);
		 return response;
		 
	}

	/*
	 * public void test() { roleDAO.findByRoleId(); }
	 */

		
	
}
