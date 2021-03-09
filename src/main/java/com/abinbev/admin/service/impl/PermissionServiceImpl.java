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
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.ErrorResponse;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.service.PermissionService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.ExceptionErrorResponse;
import com.abinbev.admin.utility.MapperUtil;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	PermissionDAO permissionDAO;

	MapperUtil<PermissionDto, Permission> permissionMapper = new MapperUtil<>();
	MapperUtil<Permission, PermissionResponseDto> permissionResponse = new MapperUtil<>();
	 @Autowired
	  private ErrorCodes errorCodes;
	/**
	 * In this method we can create a permission
	 */
	@Override
	public BasicResponse<PermissionResponseDto> savePermission(PermissionDto permissionDto) throws PermissionCreationFailureException {
		PermissionResponseDto response = null;
		Permission permission = permissionMapper.transfer(permissionDto, Permission.class);
		permission.setStatus(messageProperties.getActiveStatus());
		permission.setCreatedDate(new Date());
		Permission permissionResponseObj = permissionDAO.save(permission);
		if (permissionResponseObj != null) {

			response = permissionResponse.transfer(permissionResponseObj, PermissionResponseDto.class);
	
		} else {
			throw new PermissionCreationFailureException(errorCodes.getCategoryServiceSaveFailure());
		}
		BasicResponse<PermissionResponseDto> basicResponse = new BasicResponse<PermissionResponseDto>();
		ErrorResponse error = new ErrorResponse(null,null);
		basicResponse.setError(error);
		basicResponse.setMessage(messageProperties.getPermissionSaveSuccessMessage());
		basicResponse.setCode(messageProperties.getPermissionSaveSuccesCode());
		basicResponse.setData(response);
		return basicResponse;

	}

	/**
	 * In this method we can delete a permission
	 */
	@Override
	public  BasicResponse<PermissionResponseDto> deletePermission(String id) throws PermissionNotFoundException {
		Permission existingPermission = permissionDAO.findById(id);
		existingPermission.setStatus(messageProperties.getInactiveStatus());
		existingPermission.setModifiedDate(new Date());
		Permission permissionResponseObj = permissionDAO.save(existingPermission);
		PermissionResponseDto response = permissionResponse.transfer(permissionResponseObj, PermissionResponseDto.class);
		ErrorResponse error = new ErrorResponse(null,null);
		BasicResponse<PermissionResponseDto> basicResponse = new BasicResponse<PermissionResponseDto>();
		basicResponse.setError(error);
		basicResponse.setMessage(messageProperties.getPermissionDeleteSuccessMessage());
		basicResponse.setCode(messageProperties.getPermissionDeleteSuccesCode());
		basicResponse.setData( response);
		return basicResponse;
	}

	/**
	 * In this method we can list all permissions
	 */
	@Override
	public BasicResponse<Page<PermissionResponseDto>>  getAllPermissions(Pageable pageable) {
		BasicResponse<Page<PermissionResponseDto>> basicResponse = new BasicResponse<Page<PermissionResponseDto>>();
		Page<PermissionResponseDto> permissionResponsePage = null;

		List<PermissionResponseDto> permissionResponseList = new ArrayList<PermissionResponseDto>();

		Page<Permission> permissions = permissionDAO.getAllPermissions(pageable);

		try {
			if (permissions != null && !permissions.isEmpty()) {
				for (Permission permission : permissions) {
					PermissionResponseDto response = permissionResponse.transfer(permission,
							PermissionResponseDto.class);
					permissionResponseList.add(response);

				}

				permissionResponsePage = new PageImpl<PermissionResponseDto>(permissionResponseList, pageable,
						permissions.getContent().size());

			}
			else {
				ErrorResponse error = new ErrorResponse(messageProperties.getNoContentErrorCode(),messageProperties.getNoContentErrorMessage());
				basicResponse.setError(error);
				return basicResponse;
			}
			ErrorResponse error = new ErrorResponse(null,null);
			basicResponse.setError(error);
			basicResponse.setMessage(messageProperties.getPermissionRetrieveSuccessMessage());
			basicResponse.setCode(messageProperties.getPermissionRetrieveSuccesCode());
			
			basicResponse.setData(permissionResponsePage);
			return basicResponse;
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
	public BasicResponse<PermissionResponseDto> updatePermission(PermissionDto permissionDto)
			throws PermissionNotFoundException, PermissionUpdationFailureException {
		PermissionResponseDto response = null;
		Permission existingPermission = permissionDAO.findById(permissionDto.getId());
		if (existingPermission == null) {
			throw new PermissionNotFoundException(errorCodes.getPermissionNotFound());
		}

		Permission permission = permissionMapper.transfer(permissionDto, Permission.class);

		permission.setId(existingPermission.getId());

		permission.setCreatedDate(existingPermission.getCreatedDate());
		permission.setModifiedDate(new Date());

		Permission permissionResponseObj = permissionDAO.save(permission);

		if (permissionResponseObj != null) {
			response = permissionResponse.transfer(permissionResponseObj, PermissionResponseDto.class);

			
		} else {
			throw new PermissionUpdationFailureException(errorCodes.getCategoryServiceUpdateFailure());
		}

		BasicResponse<PermissionResponseDto> basicResponse = new BasicResponse<PermissionResponseDto>();
		ErrorResponse error = new ErrorResponse(null,null);
		basicResponse.setError(error);
		basicResponse.setMessage(messageProperties.getPermissionUpdateSuccessMessage());
		basicResponse.setCode(messageProperties.getPermissionUpdateSuccesCode());
		basicResponse.setData(response);
		return basicResponse;

	}

	/**
	 * In this method we can get a permission by permission id
	 */
	@Override
	public BasicResponse<PermissionResponseDto> getPermission(String permissionId) throws PermissionNotFoundException {
		Permission existingPermission = findPermissionByPermissionId(permissionId);

		PermissionResponseDto response = permissionResponse.transfer(existingPermission, PermissionResponseDto.class);
		BasicResponse<PermissionResponseDto> basicResponse = new BasicResponse<PermissionResponseDto>();
		ErrorResponse error = new ErrorResponse(null,null);
		basicResponse.setError(error);
		basicResponse.setMessage(messageProperties.getPermissionRetrieveSuccessMessage());
		basicResponse.setCode(messageProperties.getPermissionRetrieveSuccesCode());
		basicResponse.setData(response);
		return basicResponse;
	}

	private Permission findPermissionByPermissionId(String permissionId) throws PermissionNotFoundException {
		Permission existingPermission = permissionDAO.findByPermissionId(permissionId);

		if (existingPermission == null) {
			throw new PermissionNotFoundException(errorCodes.getPermissionNotFound());

		}
		return existingPermission;

	}

}
