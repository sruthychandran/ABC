package com.abinbev.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.exception.PermissionCreationFailureException;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.exception.PermissionUpdationFailureException;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.responseDto.PermissionResponseDto;

public interface PermissionService {

	public PermissionResponseDto savePermission(PermissionDto PermissionDto) throws PermissionCreationFailureException;

	public void deletePermission(String permissionId) throws PermissionNotFoundException;

	public PermissionResponseDto updatePermission(PermissionDto PermissionDto) throws PermissionNotFoundException, PermissionUpdationFailureException;

	PermissionResponseDto getPermission(String permissionId) throws PermissionNotFoundException;

	Page<PermissionResponseDto> getAllPermissions(Pageable pageable);
}
