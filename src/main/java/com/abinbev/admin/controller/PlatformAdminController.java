package com.abinbev.admin.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
//import org.apache.log4j.Logger;
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
import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.PlatformAdminOnBoardingDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.PlatformAdminOnBoardingResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.abinbev.admin.utility.ErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/platformAdminController/v1")
public class PlatformAdminController {

	private static final Logger log = LoggerFactory.getLogger(PlatformAdminController.class);

	@Autowired
	PlatformAdminService platformAdminService;

	@Autowired
	private ErrorCodes errorCodes;

	/**
	 * In this method a platform admin can create user
	 * 
	 * @param userDto
	 * @return UserResponseDto
	 * @throws EmailExistException
	 * @throws UserCreationFailureException
	 */

	@PostMapping("/user")
	public ResponseEntity<BasicResponse<UserResponseDto>> createUsers(@Valid @RequestBody UserDto userDto)
			throws EmailExistException, UserCreationFailureException {

		log.debug("Request to create user {}", userDto);
		BasicResponse<UserResponseDto> result = platformAdminService.saveUser(userDto);

		return ResponseEntity.ok().body(result);

	}

	/**
	 * In this method a platform admin can update user
	 * 
	 * @param userDto
	 * @return
	 * @throws BadRequestAlertException
	 * @throws NotFoundException
	 * @throws UserNotFoundException
	 * @throws UserUpdationFailureException
	 */

	@PutMapping("/user")
	public ResponseEntity<BasicResponse<UserResponseDto>> updateUsers(@RequestBody UserDto userDto)
			throws BadRequestAlertException, UserNotFoundException, UserUpdationFailureException {

		log.debug("Request to update user {}", userDto);
		if (userDto.getEmailId() == null)
			throw new BadRequestAlertException(errorCodes.getInvalidEmailId());
		BasicResponse<UserResponseDto> result = platformAdminService.updateUser(userDto);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a platform admin can list users
	 * 
	 * @return List<UserResponseDto>
	 * @throws BadRequestAlertException
	 */

	@GetMapping("/user")
	public ResponseEntity<BasicResponse<Page<UserResponseDto>>> getAllUsers(

			@RequestParam(required = false, defaultValue = "0") int page,

			@RequestParam(required = false, defaultValue = "10") int size,

			@RequestParam(required = false, defaultValue = "desc") String sort,

			@RequestParam(required = false, defaultValue = "id") String sortBy) throws BadRequestAlertException {
		log.debug("Request to get all users ");
		Pageable pageable = PageRequest.of(page, size,
				Sort.by(sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));

		BasicResponse<Page<UserResponseDto>> result = platformAdminService.getAllUsers(pageable);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a platform admin can delete user
	 * 
	 * @param emailId
	 * @return
	 * @throws UserNotFoundException
	 */

	@DeleteMapping("/user/{emailId}")
	public ResponseEntity<BasicResponse<UserResponseDto>> deleteUser(@PathVariable String emailId)
			throws UserNotFoundException {
		log.debug("Request to delete a user {}", emailId);
		BasicResponse<UserResponseDto> result = platformAdminService.deleteUser(emailId);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a platform admin can find a user by mail id
	 * 
	 * @param emailId
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */

	@GetMapping("/user/{emailId}")
	public ResponseEntity<BasicResponse<UserResponseDto>> getUserByEmailId(@PathVariable String emailId)
			throws JsonMappingException, JsonProcessingException, UserNotFoundException {
		log.debug("Request to get a user {}", emailId);
		BasicResponse<UserResponseDto> result = platformAdminService.findByEmailId(emailId);

		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a platform admin is being created
	 * 
	 * @param userDto
	 * @return UserResponseDto
	 * @throws EmailExistException
	 * @throws UserCreationFailureException
	 */

	@PostMapping("/onboardPlatformAdmin")
	public ResponseEntity<BasicResponse<PlatformAdminOnBoardingResponseDto>> createPlatformAdmin(

			@Valid @RequestBody PlatformAdminOnBoardingDto platformAdminOnBoardingDto)
			throws EmailExistException, UserCreationFailureException {
		log.debug("Request to onboard patform Admin");

		BasicResponse<PlatformAdminOnBoardingResponseDto> platformAdminOnBoardingResponse = platformAdminService
				.savePlatformAdmin(platformAdminOnBoardingDto);

		return ResponseEntity.ok().body(platformAdminOnBoardingResponse);

	}

}
