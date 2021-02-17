package com.abinbev.admin.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.CategoryDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class RoleServiceTests {

	@Autowired
	private RoleService roleService;

	@MockBean
	private RoleDAO roleDAO;

	@Autowired
	private ObjectMapper mapper;

	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void afterEach() {
		// roleDAO.deleteAll();
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void test_createRoles_success() throws JsonMappingException, JsonProcessingException {
		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").build();
		Role role = Role.builder().roleId("EU").roleName("end user").status("enable").userRole("TA")
				.createdDate(new Date()).build();

		Mockito.when(roleDAO.save(Mockito.any(Role.class))).thenReturn(role);
		RoleResponseDto returnedUser = roleService.saveRole(roleDto);
		assertNotNull(returnedUser.getRoleId());
		assertEquals("EU", returnedUser.getRoleId());
		assertEquals("end user", returnedUser.getRoleName());
		assertEquals("enable", returnedUser.getStatus());
		assertNotNull(returnedUser.getCreatedDate());
		// assertEquals("abi@gmail.com",returnedUser.getCreatedBy());
		assertEquals("created successfully", returnedUser.getMessage());

	}

	@Test
	public void test_updateRoles_success() throws JsonMappingException, JsonProcessingException, NotFoundException {
		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").build();
		
		Role role = mapper.readValue(mapToJson(roleDto), Role.class);

		RoleResponseDto saved = RoleResponseDto.builder().roleId("EU").roleName("end user").createdDate(new Date())
				.status("enable").userRole("TA").build();
		Mockito.when(roleDAO.findByRoleId(saved.getRoleId())).thenReturn(role);
		saved.setRoleId("EU");
		saved.setRoleName("Role updated");
		saved.setModifiedDate(new Date());
		RoleDto updateRequest = mapper.readValue(mapToJson(saved), RoleDto.class);
		Role role1 = mapper.readValue(mapToJson(updateRequest), Role.class);
		Mockito.when(roleDAO.save(Mockito.any(Role.class))).thenReturn(role1);
		RoleResponseDto updatedRole = roleService.updateRole(updateRequest);

		assertNotNull(updatedRole.getRoleId());
		assertEquals("Role updated", updatedRole.getRoleName());
		assertEquals("enable", updatedRole.getStatus());
		assertNotNull(updatedRole.getCreatedDate());
		assertEquals("TA", updatedRole.getUserRole());
		// assertEquals("abi@gmail.com",saved.getCreatedBy());
		assertEquals("updated successfully", updatedRole.getMessage());
		assertNotNull(updatedRole.getModifiedDate());
		// assertEquals("abi@gmail.com",saved.getModifiedBy());

	}

	@Test
	public void test_updateRoles_throws_exception()
			throws JsonMappingException, JsonProcessingException, NotFoundException {

		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").build();

		Mockito.when(roleDAO.findByRoleId(roleDto.getRoleId())).thenReturn(null);

		NotFoundException thrown = assertThrows(NotFoundException.class, () -> roleService.updateRole(roleDto));
		assertEquals("Role not found", thrown.getMessage());
	}

	
	@Test
	public void test_getAllRoles() throws JsonMappingException, JsonProcessingException, NotFoundException {
		Role role1 = Role.builder().roleId("EU").roleName("end user").status("enable").userRole("TA")
				.createdDate(new Date()).build();
		Role role2 = Role.builder().roleId("TA").roleName("tenant admin").status("enable").userRole("TA")
				.createdDate(new Date()).build();

		List<Role> roleList = Arrays.asList(role1, role2);
		

		Mockito.when(roleDAO.getAllRoles()).thenReturn(roleList);
		List<RoleResponseDto> result = roleService.getAllRoles();

		assertEquals(result.size(), 2);
		assertEquals("EU", result.get(0).getRoleId());
		assertEquals("end user", result.get(0).getRoleName());
		assertEquals("enable", result.get(0).getStatus());
		assertNotNull(result.get(0).getCreatedDate());

		assertEquals("TA", result.get(0).getUserRole());

		assertEquals("TA", result.get(1).getRoleId());

		assertEquals("tenant admin", result.get(1).getRoleName());
		assertEquals("enable", result.get(1).getStatus());
		assertNotNull(result.get(1).getCreatedDate());
		assertEquals("TA", result.get(1).getUserRole());

	}
	/*
	 * @Test public void test_deleteRole_success() throws JsonMappingException,
	 * JsonProcessingException{
	 * Mockito.when(roleDAO.getAllRoles()).thenReturn(roleList); }
	 */
	
	
	

}