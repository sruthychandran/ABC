package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;

public interface RoleService {

	public RoleResponseDto saveRole(RoleDto RoleDto) throws RoleCreationFailureException;

	public void deleteRole(String roleId) throws RoleNotFoundException;

	public List<RoleResponseDto> getAllRoles();

	public RoleResponseDto updateRole(RoleDto RoleDto) throws RoleNotFoundException;

	RoleResponseDto getRole(String roleId) throws RoleNotFoundException;
}
