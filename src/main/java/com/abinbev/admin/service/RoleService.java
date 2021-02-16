package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface RoleService {

	public RoleResponseDto saveRole(RoleDto RoleDto);
	
	public void deleteRole(String roleId);
	
	public List<RoleResponseDto> getAllRoles();
	
	public RoleResponseDto updateRole(RoleDto RoleDto) throws NotFoundException, JsonMappingException, JsonProcessingException;
}
