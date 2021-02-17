package com.abinbev.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.PlatformAdminOnBoardingDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.PlatformAdminOnBoardingResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/platform-admin")
public class PlatformAdminController {

	@Autowired
	PlatformAdminService platformAdminService;

	/**
	 * In this method a platform admin can create user
	 * 
	 * @param userDto
	 * @return UserResponseDto
	 * @throws EmailExistException
	 * @throws UserCreationFailureException
	 */
	@PostMapping("/createUser")
	public ResponseEntity<UserResponseDto> createUsers(@Valid @RequestBody UserDto userDto)
			throws EmailExistException, UserCreationFailureException {

		UserResponseDto result = platformAdminService.saveUser(userDto);

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
	@PutMapping("/updateUser")
	public ResponseEntity<UserResponseDto> updateUsers(@RequestBody UserDto userDto)
			throws BadRequestAlertException, UserNotFoundException, UserUpdationFailureException {
		if (userDto.getEmailId() == null)
			throw new BadRequestAlertException("Invalid uuid");
		UserResponseDto result = platformAdminService.updateUser(userDto);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a platform admin can list users
	 * 
	 * @return List<UserResponseDto>
	 * @throws BadRequestAlertException
	 */
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() throws BadRequestAlertException {
		List<UserResponseDto> result = platformAdminService.getAllUsers();
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method a platform admin can delete user
	 * 
	 * @param emailId
	 * @return
	 * @throws UserNotFoundException
	 */
	@GetMapping("/deleteUser/{emailId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String emailId) throws UserNotFoundException {

		platformAdminService.deleteUser(emailId);
		return ResponseEntity.ok().build();
	}

	/**
	 * In this method a platform admin can find a user by mail id
	 * 
	 * @param emailId
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/getUser/{emailId}")
	public ResponseEntity<UserResponseDto> getUserByEmailId(@PathVariable String emailId)
			throws JsonMappingException, JsonProcessingException, UserNotFoundException {

		UserResponseDto result = platformAdminService.findByEmailId(emailId);
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
	public ResponseEntity<PlatformAdminOnBoardingResponseDto> createPlatformAdmin(
			@Valid @RequestBody PlatformAdminOnBoardingDto platformAdminOnBoardingDto)
			throws EmailExistException, UserCreationFailureException {


		PlatformAdminOnBoardingResponseDto platformAdminOnBoardingResponse  = platformAdminService.savePlatformAdmin(platformAdminOnBoardingDto);


		return ResponseEntity.ok().body(platformAdminOnBoardingResponse);

	}
}
