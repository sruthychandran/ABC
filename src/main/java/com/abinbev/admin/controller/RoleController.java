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

import com.abinbev.admin.config.MessageResolver;
import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	RoleService roleService;
	 @Autowired private MessageResolver messageResolver;
	@PostMapping("/createRole")
	public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleDto roleDto) {
		//System.out.println(messageResolver.resolveKey("user.not.found"));
		
		RoleResponseDto result= roleService.saveRole(roleDto);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/updateRole")
	public ResponseEntity<RoleResponseDto> updateRole(@RequestBody RoleDto roleDto) throws BadRequestAlertException, NotFoundException, JsonMappingException, JsonProcessingException {
		if(roleDto.getRoleId() == null)
			throw new BadRequestAlertException("Invalid RoleId");
		RoleResponseDto result= roleService.updateRole(roleDto);
		return ResponseEntity.ok().body(result);
	}

	
	@GetMapping("/getAllRoles")
	public ResponseEntity<List<RoleResponseDto>> getAllRoles() throws BadRequestAlertException {
		List<RoleResponseDto> result = roleService.getAllRoles();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/deleteRole/{roleId}")
	public ResponseEntity<Void> deleteRole(@PathVariable String roleId)throws BadRequestAlertException {
		if(roleId == null)
			throw new BadRequestAlertException("Invalid roleId");
		roleService.deleteRole(roleId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/getRole/{roleId}")
	public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable String roleId) throws BadRequestAlertException, JsonMappingException, JsonProcessingException {
		if(roleId == null)
			throw new BadRequestAlertException("Invalid roleId");
		RoleResponseDto result = roleService.findByRoleId(roleId);
		return ResponseEntity.ok().body(result);
	}

}
