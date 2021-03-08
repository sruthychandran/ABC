package com.abinbev.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.exception.PermissionCreationFailureException;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.exception.PermissionUpdationFailureException;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.PermissionResponseDto;

public interface PermissionService {

	public BasicResponse<PermissionResponseDto> savePermission(PermissionDto PermissionDto) throws PermissionCreationFailureException;

	public BasicResponse<PermissionResponseDto> deletePermission(String permissionId) throws PermissionNotFoundException;

	public BasicResponse<PermissionResponseDto> updatePermission(PermissionDto PermissionDto) throws PermissionNotFoundException, PermissionUpdationFailureException;

	BasicResponse<PermissionResponseDto> getPermission(String permissionId) throws PermissionNotFoundException;

	BasicResponse<Page<PermissionResponseDto>> getAllPermissions(Pageable pageable);
}
