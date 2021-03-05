package com.abinbev.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;
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
import com.abinbev.admin.exception.PermissionCreationFailureException;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.exception.PermissionUpdationFailureException;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.service.PermissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@RequestMapping("/permissionController/v1")
public class PermissionController {



	private static final Logger log = LoggerFactory.getLogger(PermissionController.class);

	@Autowired
	PermissionService permissionService;

	/**
	 * In this method we can create a permission
	 * 
	 * @param permissionDto
	 * @return PermissionResponseDto
	 * @throws PermissionCreationFailureException
	 */

	@PostMapping("/permission")
	public ResponseEntity<BasicResponse<PermissionResponseDto>> createPermission(

			@RequestBody PermissionDto permissionDto) throws PermissionCreationFailureException {

		log.debug("Request to create permission {}", permissionDto);
		BasicResponse<PermissionResponseDto> result = permissionService.savePermission(permissionDto);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can update a permission
	 * 
	 * @param permissionDto
	 * @return PermissionResponseDto
	 * @throws BadRequestAlertException
	 * @throws PermissionUpdationFailureException
	 * @throws NotFoundException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */

	@PutMapping("/permission")
	public ResponseEntity<BasicResponse<PermissionResponseDto>> updatePermission(

			@RequestBody PermissionDto permissionDto)
			throws BadRequestAlertException, PermissionNotFoundException, PermissionUpdationFailureException {
		log.debug("Request to update permission " + permissionDto);

		if (permissionDto.getPermissionId() == null)
			throw new BadRequestAlertException("Invalid PermissionId");
		BasicResponse<PermissionResponseDto> result = permissionService.updatePermission(permissionDto);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can list all permissions
	 * 
	 * @return List<PermissionResponseDto>
	 * @throws BadRequestAlertException
	 */

	@GetMapping("/permission")
	public ResponseEntity<BasicResponse<Page<PermissionResponseDto>>> getAllPermissions(

			@RequestParam(required = false, defaultValue = "0") int page,

			@RequestParam(required = false, defaultValue = "10") int size,

			@RequestParam(required = false, defaultValue = "desc") String sort,

			@RequestParam(required = false, defaultValue = "id") String sortBy) throws BadRequestAlertException {
		log.debug("Request to list permissions ");
		Pageable pageable = PageRequest.of(page, size,
				Sort.by(sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));

		BasicResponse<Page<PermissionResponseDto>> result = permissionService.getAllPermissions(pageable);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can delete a permission
	 * 
	 * @param permissionId
	 * 
	 * @throws BadRequestAlertException
	 * @throws PermissionNotFoundException
	 */

	@DeleteMapping("/permission/{id}")
	public ResponseEntity<Void> deletePermission(@PathVariable String id)
			throws BadRequestAlertException, PermissionNotFoundException {

		log.debug("Request to delete permission " + id);
		if (id == null)
			throw new BadRequestAlertException("Invalid id");
		permissionService.deletePermission(id);
		return ResponseEntity.ok().build();
	}

	/**
	 * In this method we can get a permission by id
	 * 
	 * @param permissionId
	 * @return PermissionResponseDto
	 * @throws BadRequestAlertException
	 * 
	 * @throws PermissionNotFoundException
	 */
	@GetMapping("/permission/{permissionId}")
	public ResponseEntity<BasicResponse<PermissionResponseDto>> getPermissionByPermissionId(

			@PathVariable String permissionId) throws BadRequestAlertException, PermissionNotFoundException {

		log.debug("Request to get a permission " + permissionId);
		if (permissionId == null)
			throw new BadRequestAlertException("Invalid permissionId");
		BasicResponse<PermissionResponseDto> result = permissionService.getPermission(permissionId);

		return ResponseEntity.ok().body(result);
	}

}
