package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;

public interface RoleService {

	public void saveRole(RoleDto RoleDto);
	
	public void deleteRole(String roleId);
	
	public List<RoleResponseDto> getAllRoles();
}
