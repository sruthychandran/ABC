
package com.abinbev.admin.controller;

import java.util.List;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
//import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.utility.ErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController

@RequestMapping("/roleController/v1")
public class RoleController {


	private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	RoleService roleService;

	
	@Autowired
	  private ErrorCodes errorCodes;
	/**
	 * In this method we can create a role
	 * 
	 * @param roleDto
	 * @return RoleResponseDto
	 * @throws RoleCreationFailureException
	 */

	@PostMapping("/role")
	public ResponseEntity<BasicResponse<RoleResponseDto>> createRole(@RequestBody RoleDto roleDto)
			throws RoleCreationFailureException {

		log.debug("Request to create role {}" , roleDto);
		BasicResponse<RoleResponseDto> result = roleService.saveRole(roleDto);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can update a role
	 * 
	 * @param roleDto
	 * @return RoleResponseDto
	 * @throws BadRequestAlertException
	 * @throws RoleUpdationFailureException
	 * @throws NotFoundException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */

	@PutMapping("/role")
	public ResponseEntity<BasicResponse<RoleResponseDto>> updateRole(@RequestBody RoleDto roleDto)
			throws BadRequestAlertException, NotFoundException, RoleUpdationFailureException {
		log.debug("Request to update role {}",  roleDto);

		if (roleDto.getId() == null)
			throw new BadRequestAlertException(errorCodes.getInvalidId());
		BasicResponse<RoleResponseDto> result = roleService.updateRole(roleDto);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can list all roles
	 * 
	 * @return List<RoleResponseDto>
	 * @throws BadRequestAlertException
	 */

	@GetMapping("/role")
	public ResponseEntity<BasicResponse<Page<RoleResponseDto>>> getAllRoles(

			@RequestParam(required = false, defaultValue = "0") int page,

			@RequestParam(required = false, defaultValue = "10") int size,

			@RequestParam(required = false, defaultValue = "desc") String sort,

			@RequestParam(required = false, defaultValue = "id") String sortBy) throws BadRequestAlertException {
		log.debug("Request to list roles ");
		Pageable pageable = PageRequest.of(page, size,
				Sort.by(sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));

		BasicResponse<Page<RoleResponseDto>> result = roleService.getAllRoles(pageable);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can delete a role
	 * 
	 * @param roleId
	 * 
	 * @throws BadRequestAlertException
	 * @throws RoleNotFoundException
	 */

	@DeleteMapping("/role/{roleId}")
	public ResponseEntity<BasicResponse<RoleResponseDto>> deleteRole(@PathVariable String roleId)
			throws BadRequestAlertException, NotFoundException {

		log.debug("Request to delete role {}",  roleId);
		if (roleId == null)
			throw new BadRequestAlertException(errorCodes.getInvalidRoleId());
		BasicResponse<RoleResponseDto> result = roleService.deleteRole(roleId);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can get a role by id
	 * 
	 * @param roleId
	 * @return RoleResponseDto
	 * @throws BadRequestAlertException
	 * 
	 * @throws RoleNotFoundException/*
	 * private Role findById(String id) throws RoleNotFoundException { Role
	 * existingRole = roleDAO.findById(id);
	 * 
	 * if (existingRole == null) { throw new
	 * RoleNotFoundException(messageProperties.getRoleNotfoundMessage());
	 * 
	 * } return existingRole;
	 * 
	 * }
	 */
	 

	@GetMapping("/role/{roleId}")
	public ResponseEntity<BasicResponse<RoleResponseDto>> getRoleByRoleId(@PathVariable String roleId)
			throws BadRequestAlertException, NotFoundException {

		log.debug("Request to get a role {}" , roleId);
		if (roleId == null)
			throw new BadRequestAlertException(errorCodes.getInvalidRoleId());
		BasicResponse<RoleResponseDto> result = roleService.getRole(roleId);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can get a role by userRole
	 * 
	 * @param userRole
	 * @return BasicResponse<List<RoleResponseDto>>
	 *
	 * 
	 * @throws RoleNotFoundException
	 */

	@GetMapping("/role/{userRole}/user_role")
	public ResponseEntity<BasicResponse<List<RoleResponseDto>>> findByUserRole(@PathVariable String userRole)
			throws BadRequestAlertException, RoleNotFoundException {

		log.debug("Request to get list of  roles  {}", userRole );
		
		BasicResponse<List<RoleResponseDto>> result = roleService.findByUserRole(userRole);

		return ResponseEntity.ok().body(result);
	}
	
	
	
}
