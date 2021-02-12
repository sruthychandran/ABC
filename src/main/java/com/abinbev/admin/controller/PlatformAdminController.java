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

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;

@RestController
@RequestMapping("/platform-admin")
public class PlatformAdminController {

	@Autowired
	PlatformAdminService platformAdminService;

	@PostMapping("/createUser")
	public ResponseEntity<UserResponseDto>  createUsers(@RequestBody UserDto userDto) throws DuplicateEmailException {
		UserResponseDto result = platformAdminService.saveUser(userDto);
		
		return ResponseEntity.ok().body(result);
		
	}

	@PutMapping("/updateUser")
	public ResponseEntity<UserResponseDto> updateUsers(@RequestBody UserDto userDto) throws BadRequestAlertException, UserNotFoundException {
		if(userDto.getUuid() == null)
			throw new BadRequestAlertException("Invalid uuid");
		UserResponseDto result = platformAdminService.updateUser(userDto);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() throws BadRequestAlertException {
		List<UserResponseDto> result = platformAdminService.getAllUsers();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/deleteUser/{uuid}")
	public ResponseEntity<Void> deleteUser(@PathVariable String uuid)throws BadRequestAlertException {
		if(uuid == null)
			throw new BadRequestAlertException("Invalid uuid");
		platformAdminService.deleteUser(uuid);
		return ResponseEntity.ok().build();
	}


	




	
	

}
