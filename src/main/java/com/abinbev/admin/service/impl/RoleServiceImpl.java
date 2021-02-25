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
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.utility.MapperUtil;

@Service

public class RoleServiceImpl implements RoleService {

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	RoleDAO roleDAO;

	MapperUtil<RoleDto, Role> roleMapper = new MapperUtil<>();
	MapperUtil<Role, RoleResponseDto> roleResponse = new MapperUtil<>();

	/**
	 * In this method we can create a role
	 */
	@Override
	public RoleResponseDto saveRole(RoleDto roleDto) throws RoleCreationFailureException {
		RoleResponseDto response = null;
		Role role = roleMapper.transfer(roleDto, Role.class);

		role.setStatus(messageProperties.getActiveStatus());
		role.setCreatedDate(new Date());
		Role roleResponseObj = roleDAO.save(role);
		if (roleResponseObj != null) {

			response = roleResponse.transfer(roleResponseObj, RoleResponseDto.class);
			response.setMessage(messageProperties.getSaveMessage());
		} else {
			throw new RoleCreationFailureException(messageProperties.getUserSaveFailureMessage());
		}

		return response;

	}

	/**
	 * In this method we can delete a role
	 */
	@Override
	public void deleteRole(String roleId) throws RoleNotFoundException {

		Role role = findRoleByRoleId(roleId);

		role.setStatus(messageProperties.getInactiveStatus());
		role.setModifiedDate(new Date());
		roleDAO.save(role);

	}

	/**
	 * In this method we can list all roles
	 */
	@Override
	public Page<RoleResponseDto> getAllRoles(Pageable pageable) {

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

			}

			return roleResponsePage;
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
	public RoleResponseDto updateRole(RoleDto roleDto) throws RoleNotFoundException, RoleUpdationFailureException {
		RoleResponseDto response = null;
		Role existingRole = roleDAO.findById(roleDto.getId());
		if (existingRole == null) {
			throw new RoleNotFoundException(messageProperties.getRoleNotfoundMessage());
		}

		Role role = roleMapper.transfer(roleDto, Role.class);

		role.setId(existingRole.getId());

		role.setCreatedDate(existingRole.getCreatedDate());

		role.setStatus(existingRole.getStatus());
		role.setModifiedDate(new Date());

		Role roleResponseObj = roleDAO.save(role);

		if (roleResponseObj != null) {
			response = roleResponse.transfer(roleResponseObj, RoleResponseDto.class);

			response.setMessage(messageProperties.getUpdationMessage());
		} else {
			throw new RoleUpdationFailureException(messageProperties.getRoleUpdateFailureMessage());
		}

		return response;

	}

	/**
	 * In this method we can get a role by role id
	 */
	@Override
	public RoleResponseDto getRole(String roleId) throws RoleNotFoundException {
		Role existingRole = findRoleByRoleId(roleId);

		RoleResponseDto response = roleResponse.transfer(existingRole, RoleResponseDto.class);
		return response;
	}

	private Role findRoleByRoleId(String roleId) throws RoleNotFoundException {
		Role existingRole = roleDAO.findByRoleId(roleId);

		if (existingRole == null) {
			throw new RoleNotFoundException(messageProperties.getRoleNotfoundMessage());

		}
		return existingRole;

	}

}
