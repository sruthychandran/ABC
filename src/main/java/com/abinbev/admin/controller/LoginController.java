package com.abinbev.admin.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
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
import com.abinbev.admin.exception.PermissionCreationFailureException;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.exception.UserAlreadyExistsException;
import com.abinbev.admin.requestDto.LoginDto;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.SignupDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/loginController/v1")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
	
	@Autowired
	LoginService loginService;

	@PostMapping("/signup")
	public ResponseEntity<BasicResponse<UserResponseDto>> signup(@RequestBody SignupDto signupDto)
			throws PermissionCreationFailureException, UserAlreadyExistsException {

		log.debug("Request to signup " + signupDto);
		BasicResponse<UserResponseDto> signupResponse=loginService.signup(signupDto);
		return ResponseEntity.ok().body(signupResponse);
	}
	
	@PostMapping("/login")
	public ResponseEntity<BasicResponse<UserResponseDto>> login(@RequestBody LoginDto loginDto)
			throws PermissionCreationFailureException {

		log.debug("Request to login",  loginDto);
		BasicResponse<UserResponseDto> loginResponse=loginService.login(loginDto);
		return ResponseEntity.ok().body(loginResponse);
	}


	
}
