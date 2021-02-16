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
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.abinbev.admin.service.TenantAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/tenant-admin")
public class TenantAdminController {

	@Autowired
	TenantAdminService tenantAdminService;
	

	

	@PostMapping("/createUser")
	public ResponseEntity<UserResponseDto>  createUsers(@RequestBody UserDto userDto) throws DuplicateEmailException {
		UserResponseDto result = tenantAdminService.saveUser(userDto);
		
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<UserResponseDto> updateUsers(@RequestBody UserDto userDto) throws BadRequestAlertException, NotFoundException {
		if(userDto.getUuid() == null)
			throw new BadRequestAlertException("Invalid uuid");
		UserResponseDto result = tenantAdminService.updateUser(userDto);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() throws BadRequestAlertException {
		List<UserResponseDto> result = tenantAdminService.getAllUsers();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/deleteUser/{uuid}")
	public ResponseEntity<Void> deleteUser(@PathVariable String uuid)throws BadRequestAlertException {
		if(uuid == null)
			throw new BadRequestAlertException("Invalid uuid");
		tenantAdminService.deleteUser(uuid);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/createRole")
	public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleDto roleDto) {
		//System.out.println(messageResolver.resolveKey("user.not.found"));
		
		RoleResponseDto result= tenantAdminService.saveRole(roleDto);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/updateRole")
	public ResponseEntity<RoleResponseDto> updateRole(@RequestBody RoleDto roleDto) throws BadRequestAlertException, NotFoundException, JsonMappingException, JsonProcessingException {
		if(roleDto.getRoleId() == null)
			throw new BadRequestAlertException("Invalid RoleId");
		RoleResponseDto result= tenantAdminService.updateRole(roleDto);
		return ResponseEntity.ok().body(result);
	}

	
	@GetMapping("/getAllRoles")
	public ResponseEntity<List<RoleResponseDto>> getAllRoles() throws BadRequestAlertException {
		List<RoleResponseDto> result = tenantAdminService.getAllRoles();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/deleteRole/{roleId}")
	public ResponseEntity<Void> deleteRole(@PathVariable String roleId)throws BadRequestAlertException {
		if(roleId == null)
			throw new BadRequestAlertException("Invalid uuid");
		tenantAdminService.deleteRole(roleId);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/createCategoryService")
	public ResponseEntity<CategoryServiceResponseDto> createCategoryService(@RequestBody CategoryServiceDto categoryServiceDto) {
		CategoryServiceResponseDto result =tenantAdminService.saveCategoryService(categoryServiceDto);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/updateCategoryService")
	public ResponseEntity<CategoryServiceResponseDto> updateCategoryService(@RequestBody CategoryServiceDto categoryServiceDto) throws NotFoundException, BadRequestAlertException {
		if(categoryServiceDto.getCategoryId() == null)
			throw new BadRequestAlertException("Invalid CategoryId");
		CategoryServiceResponseDto result =tenantAdminService.updateCategoryService(categoryServiceDto);
		return ResponseEntity.ok().body(result);
		
	}

	@GetMapping("/getAllCategoryServices")
	public ResponseEntity<List<CategoryServiceResponseDto>> getAllCategoryServices() {
		
		List<CategoryServiceResponseDto> result = tenantAdminService.getAllCategoryServices();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/deleteCategoryService/{categoryId}")
	public void deleteCategoryService(@PathVariable String categoryId) {
		
		tenantAdminService.deleteCategoryService(categoryId);
	
	}
	




	
	

}
