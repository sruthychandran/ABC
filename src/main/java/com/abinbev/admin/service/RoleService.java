package com.abinbev.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;

public interface RoleService {

	public RoleResponseDto saveRole(RoleDto RoleDto) throws RoleCreationFailureException;

	public void deleteRole(String roleId) throws RoleNotFoundException;

	public RoleResponseDto updateRole(RoleDto RoleDto) throws RoleNotFoundException, RoleUpdationFailureException;

	RoleResponseDto getRole(String roleId) throws RoleNotFoundException;

	Page<RoleResponseDto> getAllRoles(Pageable pageable);
}
