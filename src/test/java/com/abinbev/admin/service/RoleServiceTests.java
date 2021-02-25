package com.abinbev.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class RoleServiceTests {

	@Autowired
	private RoleService roleService;

	@MockBean
	private RoleDAO roleDAO;

	@Autowired
	private ObjectMapper mapper;

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void test_createRoles_success()
			throws JsonMappingException, JsonProcessingException, RoleCreationFailureException {
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
		assertEquals("created successfully", returnedUser.getMessage());

	}

	@Test
	public void test_updateRoles_success()
			throws JsonMappingException, JsonProcessingException, RoleNotFoundException, RoleUpdationFailureException {
		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").build();

		Role role = mapper.readValue(mapToJson(roleDto), Role.class);

		RoleResponseDto saved = RoleResponseDto.builder().id("qwerty").roleId("EU").roleName("end user").createdDate(new Date())
				.status("enable").userRole("TA").build();
		Mockito.when(roleDAO.findById(saved.getId())).thenReturn(role);
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
		assertEquals("updated successfully", updatedRole.getMessage());
		assertNotNull(updatedRole.getModifiedDate());

	}

	@Test
	public void test_updateRoles_throws_exception() throws JsonMappingException, JsonProcessingException ,RoleNotFoundException{

		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").build();

		Mockito.when(roleDAO.findByRoleId(roleDto.getRoleId())).thenReturn(null);

		RoleNotFoundException thrown = assertThrows(RoleNotFoundException.class, () -> roleService.updateRole(roleDto));
		assertEquals("Role not found", thrown.getMessage());
	}

	@Test
	public void test_getAllRoles() throws JsonMappingException, JsonProcessingException, RoleNotFoundException {
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
	
	@Test
	public void test_deleteRoles_success() throws JsonMappingException, JsonProcessingException, RoleNotFoundException {
		
	
		    
		    Role role = Role.builder().id("qwerty").roleId("EU").roleName("end user").roleDescription("roleDescription").status("active").build();
		    
		    Mockito.when(roleDAO.findByRoleId("EU")).thenReturn(role);
		    
		    role.setStatus("inactive");
		    
		    Role updatedRole = Role.builder().id("qwerty").roleId("EU").roleName("end user").roleDescription("roleDescription").status("inactive").build();
		    
		    Mockito.when(roleDAO.save(role)).thenReturn(updatedRole);
		    
		     roleService.deleteRole("EU");
		
		
		
		
	

				
				/*
				 * assertNotNull(updatedRole.getId());
				 * assertEquals("end user",updatedRole.getRoleName()); assertEquals("inactive",
				 * updatedRole.getStatus());
				 */
				
				 
		
		
	}

}