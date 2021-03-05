package com.abinbev.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.RoleResponseDto;

public interface RoleService {

	public BasicResponse<RoleResponseDto> saveRole(RoleDto RoleDto) throws RoleCreationFailureException;

	public void deleteRole(String roleId) throws RoleNotFoundException;

	public BasicResponse<RoleResponseDto> updateRole(RoleDto RoleDto) throws RoleNotFoundException, RoleUpdationFailureException;

	BasicResponse<RoleResponseDto> getRole(String roleId) throws RoleNotFoundException;

	BasicResponse<Page<RoleResponseDto>> getAllRoles(Pageable pageable);
}
