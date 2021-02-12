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
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	RoleService roleService;

	@PostMapping("/createRole")
	public void createRole(@RequestBody RoleDto roleDto) {
		 roleService.saveRole(roleDto);
	}

	@PutMapping("/updateRole")
	public void updateRole(@RequestBody RoleDto roleDto) {
		roleService.saveRole(roleDto);
	}

	@GetMapping("/getAllRoles")
	public ResponseEntity<List<RoleResponseDto>> getAllRoles() throws BadRequestAlertException {
		List<RoleResponseDto> result = roleService.getAllRoles();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/deleteRole/{roleId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String roleId)throws BadRequestAlertException {
		if(roleId == null)
			throw new BadRequestAlertException("Invalid uuid");
		roleService.deleteRole(roleId);
		return ResponseEntity.ok().build();
	}

}
