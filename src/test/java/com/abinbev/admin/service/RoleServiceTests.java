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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.exception.RoleCreationFailureException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.exception.RoleUpdationFailureException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.BasicResponse;
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

		Role role = Role.builder().roleId("EU").roleName("end user").status("active")

				.createdDate(new Date()).build();

		Mockito.when(roleDAO.save(Mockito.any(Role.class))).thenReturn(role);

		BasicResponse<RoleResponseDto> returnedUser = roleService.saveRole(roleDto);

		assertNotNull(returnedUser.getData().getRoleId());
		assertEquals("EU", returnedUser.getData().getRoleId());
		assertEquals("end user", returnedUser.getData().getRoleName());

		assertEquals("active", returnedUser.getData().getStatus());
		assertNotNull(returnedUser.getData().getCreatedDate());
		// assertEquals("created successfully", returnedUser.getMessage());

	}

	@Test
	public void test_updateRoles_success()
			throws JsonMappingException, JsonProcessingException, RoleNotFoundException, RoleUpdationFailureException {
		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").build();

		Role role = mapper.readValue(mapToJson(roleDto), Role.class);

		RoleResponseDto saved = RoleResponseDto.builder().id("qwerty").roleId("EU").roleName("end user")

				.createdDate(new Date()).status("active").build();

		Mockito.when(roleDAO.findById(saved.getId())).thenReturn(role);
		saved.setRoleId("EU");
		saved.setRoleName("Role updated");
		saved.setModifiedDate(new Date());
		RoleDto updateRequest = mapper.readValue(mapToJson(saved), RoleDto.class);
		Role role1 = mapper.readValue(mapToJson(updateRequest), Role.class);
		Mockito.when(roleDAO.save(Mockito.any(Role.class))).thenReturn(role1);
		BasicResponse<RoleResponseDto> updatedRole = roleService.updateRole(updateRequest);

		assertNotNull(updatedRole.getData().getRoleId());
		assertEquals("Role updated", updatedRole.getData().getRoleName());
		assertEquals("active", updatedRole.getData().getStatus());
		assertNotNull(updatedRole.getData().getCreatedDate());
		//assertEquals("TA", updatedRole.getData().getUserRole());
		// assertEquals("updated successfully",updatedRole.getData().getMessage());
		assertNotNull(updatedRole.getData().getModifiedDate());

	}

	@Test
	public void test_updateRoles_throws_exception()
			throws JsonMappingException, JsonProcessingException, RoleNotFoundException {

		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").build();

		Mockito.when(roleDAO.findByRoleId(roleDto.getRoleId())).thenReturn(null);

		RoleNotFoundException thrown = assertThrows(RoleNotFoundException.class, () -> roleService.updateRole(roleDto));
		assertEquals("Role not exists", thrown.getMessage());
	}

	@Test

	public void test_getRole_success() throws RoleNotFoundException {
		Role role = Role.builder().roleId("EU").roleName("end user").status("active").createdDate(new Date()).build();

		Mockito.when(roleDAO.findByRoleId("EU")).thenReturn(role);

		BasicResponse<RoleResponseDto> roleResponseObj = roleService.getRole("EU");
		assertEquals("EU", roleResponseObj.getData().getRoleId());
		assertEquals("end user", roleResponseObj.getData().getRoleName());

		assertEquals("active", roleResponseObj.getData().getStatus());

	}

	@Test
	public void test_getAllRoles() throws JsonMappingException, JsonProcessingException, RoleNotFoundException {

		Role role1 = Role.builder().roleId("EU").roleName("end user").status("enable").createdDate(new Date()).build();
		Role role2 = Role.builder().roleId("TA").roleName("tenant admin").status("enable").createdDate(new Date())
				.build();

		List<Role> roleList = Arrays.asList(role1, role2);

		Page<Role> page = new PageImpl<>(roleList);

		Mockito.when(roleDAO.getAllRoles(PageRequest.of(0, 20))).thenReturn(page);

		// assertThat(roleService.getAllRoles(PageRequest.of(0,
		// 20)).getTotalElements()).isEqualTo(2);

	}

	/*
	 * @Test public void test_deleteRoles_success() throws JsonMappingException,
	 * JsonProcessingException, RoleNotFoundException {
	 * 
	 * Role role =
	 * Role.builder().id("qwerty").roleId("EU").roleName("end user").roleDescription
	 * ("roleDescription") .status("active").build();
	 * 
	 * Role roleResponse =
	 * Role.builder().id("qwerty").roleId("EU").roleName("end user")
	 * .roleDescription("roleDescription").status("active").build();
	 * 
	 * Mockito.when(roleDAO.findByRoleId(role.getRoleId())).thenReturn(roleResponse)
	 * ; roleService.deleteRole(role.getRoleId()); }
	 */

}