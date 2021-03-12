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
import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.dao.UserRoleMappingDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.UserRoleMapping;
//import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.ErrorResponse;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.SuccessResponse;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.service.UserRoleMappingService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;


@Service

public class RoleServiceImpl implements RoleService {

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	RoleDAO roleDAO;

	MapperUtil<RoleDto, Role> roleMapper = new MapperUtil<>();
	MapperUtil<Role, RoleResponseDto> roleResponse = new MapperUtil<>();
	
	 @Autowired
	  private ErrorCodes errorCodes;
	 

		@Autowired
		UserRoleMappingService userRoleMappingService;
		
		@Autowired
		UserRoleMappingDAO userRoleMappingDAO;

	/**
	 * In this method we can create a role
	 */
	@Override
	public BasicResponse<RoleResponseDto> saveRole(RoleDto roleDto) throws RoleCreationFailureException {
		RoleResponseDto response = null;
		Role role = roleMapper.transfer(roleDto, Role.class);

		role.setStatus(messageProperties.getActiveStatus());
		role.setCreatedDate(new Date());
		Role roleResponseObj = roleDAO.save(role);
		
		
		if (roleResponseObj != null) {

			response = roleResponse.transfer(roleResponseObj, RoleResponseDto.class);

		} else {
			throw new RoleCreationFailureException(errorCodes.getRoleSaveFailure());
		}
		
		UserRoleMapping userRoleMapping = new UserRoleMapping();
		userRoleMapping.setRoleId(roleResponseObj.getRoleId());
		if(roleDto.getUserRoles() !=null && !roleDto.getUserRoles().isEmpty()) {
			userRoleMapping.setUserRoles(roleDto.getUserRoles());
			userRoleMappingService.save(userRoleMapping);
		}
		
		
		
		ErrorResponse error = new ErrorResponse(null, null);

		BasicResponse<RoleResponseDto> basicResponse = new BasicResponse<RoleResponseDto>();
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getRoleSaveSuccessMessage(),messageProperties.getRoleSaveSuccesCode());
		basicResponse.setMessage(message);
	//	basicResponse.setMessage(messageProperties.getRoleSaveSuccessMessage());
	//	basicResponse.setCode(messageProperties.getRoleSaveSuccesCode());

		basicResponse.setData(response);
		return basicResponse;

	}

	/**
	 * In this method we can delete a role	@Autowired
		UserRoleMappingService userRoleMappingService;
	 */
	@Override
	public BasicResponse<RoleResponseDto> deleteRole(String roleId) throws RoleNotFoundException {

		Role role = findRoleByRoleId(roleId);

		Role roleResponseObj = roleDAO.deleteRole(role);
		RoleResponseDto response = roleResponse.transfer(roleResponseObj, RoleResponseDto.class);
		ErrorResponse error = new ErrorResponse(null, null);
		BasicResponse<RoleResponseDto> basicResponse = new BasicResponse<RoleResponseDto>();
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getRoleSaveSuccessMessage(),messageProperties.getRoleSaveSuccesCode());
		basicResponse.setMessage(message);
		
		basicResponse.setData(response);
		return basicResponse;

	}

	/**
	 * In this method we can list all roles
	 */
	@Override
	public BasicResponse<Page<RoleResponseDto>> getAllRoles(Pageable pageable) {
		BasicResponse<Page<RoleResponseDto>> basicResponse = new BasicResponse<Page<RoleResponseDto>>();
		Page<RoleResponseDto> roleResponsePage = null;

		List<RoleResponseDto> roleResponseList = new ArrayList<RoleResponseDto>();

		Page<Role> roles = roleDAO.getAllRoles(pageable);

		try {
			if (roles != null && !roles.isEmpty()) {
				for (Role role : roles) {
					RoleResponseDto response = roleResponse.transfer(role, RoleResponseDto.class);
					roleResponseList.add(response);

				}

				roleResponsePage = new PageImpl<RoleResponseDto>(roleResponseList, pageable, roles.getContent().size());

			} else {
				ErrorResponse error = new ErrorResponse(messageProperties.getNoContentErrorCode(),
						messageProperties.getNoContentErrorMessage());
				basicResponse.setError(error);
				return basicResponse;
			}
			ErrorResponse error = new ErrorResponse(null, null);
			basicResponse.setError(error);
			SuccessResponse message = new SuccessResponse(messageProperties.getRoleRetrieveSuccessMessage(),messageProperties.getRoleRetrieveSuccesCode());
			basicResponse.setMessage(message);
			

			basicResponse.setData(roleResponsePage);
			return basicResponse;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			roleResponsePage = null;
		}

		return null;

	}

	/**
	 * In this method we can update a role
	 * 
	 * @throws RoleUpdationFailureException
	 */
	@Override
	public BasicResponse<RoleResponseDto> updateRole(RoleDto roleDto)
			throws RoleNotFoundException, RoleUpdationFailureException {
		RoleResponseDto response = null;
		Role existingRole = roleDAO.findById(roleDto.getId());
	
		
		
		if (existingRole == null) {
			throw new RoleNotFoundException(errorCodes.getRoleNotFound());
		}

		Role role = roleMapper.transfer(roleDto, Role.class);

		role.setId(existingRole.getId());

		role.setCreatedDate(existingRole.getCreatedDate());

		role.setStatus(existingRole.getStatus());
		role.setModifiedDate(new Date());

		Role roleResponseObj = roleDAO.save(role);

		if (roleResponseObj != null) {
			response = roleResponse.transfer(roleResponseObj, RoleResponseDto.class);

		} else {
			throw new RoleUpdationFailureException(errorCodes.getRoleUpdateFailure());
		}

		BasicResponse<RoleResponseDto> basicResponse = new BasicResponse<RoleResponseDto>();
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getRoleUpdateSuccessMessage(),messageProperties.getRoleUpdateSuccesCode());
		basicResponse.setMessage(message);
		
		basicResponse.setData(response);
		return basicResponse;

	}

	/**
	 * In this method we can get a role by role id
	 */
	@Override
	public BasicResponse<RoleResponseDto> getRole(String roleId) throws RoleNotFoundException {
		Role existingRole = findRoleByRoleId(roleId);

		RoleResponseDto response = roleResponse.transfer(existingRole, RoleResponseDto.class);

		BasicResponse<RoleResponseDto> basicResponse = new BasicResponse<RoleResponseDto>();
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getRoleRetrieveSuccessMessage(),messageProperties.getRoleRetrieveSuccesCode());
		basicResponse.setMessage(message);
		
		basicResponse.setData(response);
		return basicResponse;

	}

	private Role findRoleByRoleId(String roleId) throws RoleNotFoundException {
		Role existingRole = roleDAO.findByRoleId(roleId);

		if (existingRole == null) {
			throw new RoleNotFoundException(errorCodes.getRoleNotFound());

		}
		return existingRole;

	}
	
	


	/**
	 * In this method we can list all roles by userRoles
	 */
	@Override
	public BasicResponse<List<RoleResponseDto>> findByUserRole(String userRole) {
		BasicResponse<List<RoleResponseDto>> basicResponse = new BasicResponse<List<RoleResponseDto>>();

		List<RoleResponseDto> roleResponseList = new ArrayList<RoleResponseDto>();

		List<UserRoleMapping> roles = userRoleMappingDAO.findByUserRole(userRole);
		
		
		

		try {
			if (roles != null && !roles.isEmpty()) {
				for (UserRoleMapping role : roles) {
					Role roleobj =roleDAO.findByRoleId(role.getRoleId());
					RoleResponseDto response = roleResponse.transfer(roleobj, RoleResponseDto.class);
					roleResponseList.add(response);

				}


			} else {
				ErrorResponse error = new ErrorResponse(messageProperties.getNoContentErrorCode(),
						messageProperties.getNoContentErrorMessage());
				basicResponse.setError(error);
				return basicResponse;
			}
			ErrorResponse error = new ErrorResponse(null, null);
			basicResponse.setError(error);
			SuccessResponse message = new SuccessResponse(messageProperties.getRoleRetrieveSuccessMessage(),messageProperties.getRoleRetrieveSuccesCode());
			basicResponse.setMessage(message);
			

			basicResponse.setData(roleResponseList);
			return basicResponse;

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			roleResponseList = null;
		}

		return null;

	}


}
