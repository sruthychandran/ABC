package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.PermissionDAO;
import com.abinbev.admin.entity.Permission;
import com.abinbev.admin.exception.PermissionCreationFailureException;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.exception.PermissionUpdationFailureException;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.service.PermissionService;
import com.abinbev.admin.utility.MapperUtil;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	PermissionDAO permissionDAO;

	MapperUtil<PermissionDto, Permission> permissionMapper = new MapperUtil<>();
	MapperUtil<Permission, PermissionResponseDto> permissionResponse = new MapperUtil<>();

	/**
	 * In this method we can create a permission
	 */
	@Override
	public PermissionResponseDto savePermission(PermissionDto permissionDto) throws PermissionCreationFailureException {
		PermissionResponseDto response = null;
		Permission permission = permissionMapper.transfer(permissionDto, Permission.class);
		permission.setStatus(messageProperties.getActiveStatus());
		permission.setCreatedDate(new Date());
		Permission permissionResponseObj = permissionDAO.save(permission);
		if (permissionResponseObj != null) {

			response = permissionResponse.transfer(permissionResponseObj, PermissionResponseDto.class);
			response.setMessage(messageProperties.getSaveMessage());
		} else {
			throw new PermissionCreationFailureException(messageProperties.getUserSaveFailureMessage());
		}

		return response;

	}

	/**
	 * In this method we can delete a permission
	 */
	@Override
	public void deletePermission(String id) throws PermissionNotFoundException {
		Permission existingPermission = permissionDAO.findById(id);
		existingPermission.setStatus(messageProperties.getInactiveStatus());
		existingPermission.setModifiedDate(new Date());
		permissionDAO.save(existingPermission);

	}

	/**
	 * In this method we can list all permissions
	 */
	@Override
	public Page<PermissionResponseDto> getAllPermissions(Pageable pageable) {

		Page<PermissionResponseDto> permissionResponsePage = null;

		List<PermissionResponseDto> permissionResponseList = new ArrayList<PermissionResponseDto>();

		Page<Permission> permissions = permissionDAO.getAllPermissions(pageable);

		try {
			if (permissions != null && !permissions.isEmpty()) {
				for (Permission permission : permissions) {
					PermissionResponseDto response = permissionResponse.transfer(permission, PermissionResponseDto.class);
					permissionResponseList.add(response);

				}

				permissionResponsePage = new PageImpl<PermissionResponseDto>(permissionResponseList, pageable, permissions.getContent().size());

			}

			return permissionResponsePage;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			permissionResponsePage = null;
		}

		return null;

	}

	/**
	 * In this method we can update a permission
	 * 
	 * @throws PermissionUpdationFailureException
	 */
	@Override
	public PermissionResponseDto updatePermission(PermissionDto permissionDto) throws PermissionNotFoundException, PermissionUpdationFailureException {
		PermissionResponseDto response = null;
		Permission existingPermission = permissionDAO.findById(permissionDto.getId());
		if (existingPermission == null) {
			throw new PermissionNotFoundException(messageProperties.getPermissionNotfoundMessage());
		}

		Permission permission = permissionMapper.transfer(permissionDto, Permission.class);

		permission.setId(existingPermission.getId());

		permission.setCreatedDate(existingPermission.getCreatedDate());
		permission.setModifiedDate(new Date());

		Permission permissionResponseObj = permissionDAO.save(permission);

		if (permissionResponseObj != null) {
			response = permissionResponse.transfer(permissionResponseObj, PermissionResponseDto.class);

			response.setMessage(messageProperties.getUpdationMessage());
		} else {
			throw new PermissionUpdationFailureException(messageProperties.getPermissionUpdateFailureMessage());
		}

		return response;

	}

	/**
	 * In this method we can get a permission by permission id
	 */
	@Override
	public PermissionResponseDto getPermission(String permissionId) throws PermissionNotFoundException {
		Permission existingPermission = findPermissionByPermissionId(permissionId);

		PermissionResponseDto response = permissionResponse.transfer(existingPermission, PermissionResponseDto.class);
		return response;
	}

	private Permission findPermissionByPermissionId(String permissionId) throws PermissionNotFoundException {
		Permission existingPermission = permissionDAO.findByPermissionId(permissionId);

		if (existingPermission == null) {
			throw new PermissionNotFoundException(messageProperties.getPermissionNotfoundMessage());

		}
		return existingPermission;

	}

}
