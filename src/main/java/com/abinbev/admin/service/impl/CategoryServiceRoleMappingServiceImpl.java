package com.abinbev.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.CategoryServiceRoleMappingDAO;
import com.abinbev.admin.entity.CategoryServiceRoleMapping;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.CategoryServiceRoleMappingService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

@Service

public class CategoryServiceRoleMappingServiceImpl implements CategoryServiceRoleMappingService {

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	CategoryServiceRoleMappingDAO categoryServiceRoleMappingDAO;

	MapperUtil<RoleDto, Role> roleMapper = new MapperUtil<>();
	MapperUtil<Role, RoleResponseDto> roleResponse = new MapperUtil<>();

	@Autowired
	private ErrorCodes errorCodes;

	/**
	 * In this method map categoryService with userroles 
	 */
	@Override
	public CategoryServiceRoleMapping save(CategoryServiceRoleMapping categoryServiceRoleMapping) {
		RoleResponseDto response = null;

		CategoryServiceRoleMapping categoryServiceRoleMappingObj = categoryServiceRoleMappingDAO
				.save(categoryServiceRoleMapping);

		return categoryServiceRoleMappingObj;

	}
	@Override
	public CategoryServiceRoleMapping findByModuleId(String moduleId){
		return categoryServiceRoleMappingDAO.findByModuleId( moduleId);
		
		
	}

}
