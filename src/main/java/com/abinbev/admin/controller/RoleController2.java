package com.abinbev.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/roles/v2")
public class RoleController2 {

	@Autowired
	RoleService roleService;

	/**
	 *  In this method we can create a role
	 * @param roleDto
	 * @return RoleResponseDto
	 * @throws RoleCreationFailureException
	 */
	@PostMapping("/createRole")
	public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleDto roleDto)
			throws RoleCreationFailureException {

		RoleResponseDto result = roleService.saveRole(roleDto);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can update a role
	 * @param roleDto
	 * @return RoleResponseDto
	 * @throws BadRequestAlertException
	 * @throws RoleUpdationFailureException 
	 * @throws NotFoundException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@PutMapping("/updateRole")
	public ResponseEntity<RoleResponseDto> updateRole(@RequestBody RoleDto roleDto)
			throws BadRequestAlertException, RoleNotFoundException, RoleUpdationFailureException {
		if (roleDto.getRoleId() == null)
			throw new BadRequestAlertException("Invalid RoleId");
		RoleResponseDto result = roleService.updateRole(roleDto);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can list all roles
	 * @return List<RoleResponseDto>
	 * @throws BadRequestAlertException
	 */
	@GetMapping("/getAllRoles")
	public ResponseEntity<List<RoleResponseDto>> getAllRoles() throws BadRequestAlertException {
		List<RoleResponseDto> result = roleService.getAllRoles();
	
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can delete a role
	 * @param roleId
	 * 
	 * @throws BadRequestAlertException
	 * @throws RoleNotFoundException
	 */
	@GetMapping("/deleteRole/{roleId}")
	public ResponseEntity<Void> deleteRole(@PathVariable String roleId)
			throws BadRequestAlertException, RoleNotFoundException {
		if (roleId == null)
			throw new BadRequestAlertException("Invalid roleId");
		roleService.deleteRole(roleId);
		return ResponseEntity.ok().build();
	}

	/**
	 * In this method we can get a role by id
	 * @param roleId
	 * @return RoleResponseDto
	 * @throws BadRequestAlertException
	 * 
	 * @throws RoleNotFoundException
	 */
	@GetMapping("/getRole/{roleId}")
	public ResponseEntity<RoleResponseDto> getRoleByRoleId(@PathVariable String roleId)
			throws BadRequestAlertException, RoleNotFoundException {
		if (roleId == null)
			throw new BadRequestAlertException("Invalid roleId");
		RoleResponseDto result = roleService.getRole(roleId);
		return ResponseEntity.ok().body(result);
	}

}
