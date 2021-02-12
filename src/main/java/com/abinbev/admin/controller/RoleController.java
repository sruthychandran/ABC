package com.abinbev.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.entity.Role;
import com.abinbev.admin.requestDto.RoleDto;
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
	public void updateRole(@RequestBody Role userDto) {

		
	}

	@GetMapping("/roles")
	public String getRole() {
		return null;
	}

}
