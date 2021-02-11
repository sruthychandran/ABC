package com.abinbev.admin.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
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
	public void saveRole(RoleDto roleDto) {
		Role role=toRole.transfer(roleDto,Role.class);
	   role= roleDAO.save(role); 
		 RoleResponseDto response=toRoleResponse.transfer(role,RoleResponseDto.class);
		 response.setMessage(creationMessage);
	}
	
	



	

		
	
}
