package com.abinbev.admin.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.TenantAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/tenant-admin")
public class TenantAdminController {

	static Logger log = Logger.getLogger(RoleController2.class); 
	
	@Autowired
	TenantAdminService tenantAdminService;

	/**
	 * In this method a tenant admin can create user
	 * 
	 * @param userDto
	 * @return UserResponseDto
	 * @throws EmailExistException
	 * @throws UserCreationFailureException
	 */
	@PostMapping("/createUser")
	public ResponseEntity<UserResponseDto> createUsers(@RequestBody UserDto userDto)
			throws EmailExistException, UserCreationFailureException {
		
		log.debug("Request to create user " + userDto);
		UserResponseDto result = tenantAdminService.saveUser(userDto);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a tenant admin can update user
	 * 
	 * @param userDto
	 * @return UserResponseDto
	 * @throws BadRequestAlertException
	 * @throws UserUpdationFailureException
	 * @throws NotFoundException
	 */
	@PutMapping("/updateUser")
	public ResponseEntity<UserResponseDto> updateUsers(@RequestBody UserDto userDto)
			throws BadRequestAlertException, UserNotFoundException, UserUpdationFailureException {
	
		log.debug("Request to update user " + userDto);
		if (userDto.getEmailId() == null)
			throw new BadRequestAlertException("Invalid emailId");
		UserResponseDto result = tenantAdminService.updateUser(userDto);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a tenant admin can list user
	 * 
	 * @return List<UserResponseDto>
	 * @throws BadRequestAlertException
	 */
	@GetMapping("/getAllUsers")
	public ResponseEntity<Page<UserResponseDto>> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size,
			@RequestParam(required = false, defaultValue = "desc") String sort,
			@RequestParam(required = false, defaultValue = "id") String sortBy) throws BadRequestAlertException {
		
		log.debug("Request to get all users ");
		Pageable pageable = PageRequest.of(page, size,
				Sort.by(sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));


	
		Page<UserResponseDto> result = tenantAdminService.getAllUsers(pageable);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a Tenant admin can delete user
	 * 
	 * @param emailId
	 * @return
	 * @throws UserNotFoundException
	 */
	@GetMapping("/deleteUser/{emailId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String emailId) throws UserNotFoundException {

		log.debug("Request to delete a user " + emailId);
		tenantAdminService.deleteUser(emailId);
		return ResponseEntity.ok().build();
	}

	/**
	 * In this method a Tenant admin can find a user by mail id
	 * 
	 * @param emailId
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/getUser/{emailId}")
	public ResponseEntity<UserResponseDto> getUserByEmailId(@PathVariable String emailId) throws UserNotFoundException {
		log.debug("Request to get a user " + emailId);
		UserResponseDto result = tenantAdminService.findByEmailId(emailId);
		return ResponseEntity.ok().body(result);
	}

}
