package com.abinbev.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.entity.UserRoleMapping;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.exception.RoleCreationFailureException;
//import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.UserRoleMappingDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.RoleResponseDto;

public interface UserRoleMappingService {

	public UserRoleMapping save(UserRoleMapping userRoleMapping);

	/*
	 * public BasicResponse<RoleResponseDto> deleteRole(String roleId) throws
	 * NotFoundException;
	 * 
	 * public BasicResponse<RoleResponseDto> updateRole(RoleDto RoleDto) throws
	 * NotFoundException, RoleUpdationFailureException;
	 * 
	 * BasicResponse<RoleResponseDto> getRole(String roleId) throws
	 * NotFoundException;
	 * 
	 * BasicResponse<Page<RoleResponseDto>> getAllRoles(Pageable pageable);
	 */
}
