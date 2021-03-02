package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = { RoleController.class })

public class RoleControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoleService roleService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void createRoles() throws Exception {

		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").status("active").userRole("TA").build();

		RoleResponseDto roleResponseDto = RoleResponseDto.builder().roleId("EU").roleName("end user").status("active")
				.userRole("TA").createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(roleDto);
		String URI = "/roles/v1/createRole";

		Mockito.when(roleService.saveRole(Mockito.any(RoleDto.class))).thenReturn(roleResponseDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		RoleResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), RoleResponseDto.class);

		assertEquals("EU", result.getRoleId());
		assertEquals("end user", result.getRoleName());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void updateRole_throws_BadRequestAlertException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").status("active").userRole("TA").build();

		mockMvc.perform(
				put("/roles/v1/updateRole").contentType("application/json").content(mapper.writeValueAsString(roleDto)))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException))
				.andExpect(result -> assertEquals("Invalid Id", result.getResolvedException().getMessage()));
	}

	@Test
	public void updateRoles_success() throws Exception {

		RoleDto roleDto = RoleDto.builder().id("sdfghjkl").roleId("EU").roleName("end user").status("active")
				.userRole("TA").build();

		RoleResponseDto roleResponseDto = RoleResponseDto.builder().id("sdfghjkl").roleId("EU").roleName("end user")
				.status("active").userRole("TA").createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(roleDto);
		String URI = "/roles/v1/updateRole";

		Mockito.when(roleService.updateRole(Mockito.any(RoleDto.class))).thenReturn(roleResponseDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		RoleResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), RoleResponseDto.class);

		assertEquals("EU", result.getRoleId());
		assertEquals("end user", result.getRoleName());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getRolesById_success() throws Exception {

		RoleResponseDto roleResponseDto = RoleResponseDto.builder().id("sdfghjkl").roleId("EU").roleName("end user")
				.status("active").userRole("TA").createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String URI = "/roles/v1//getRole/EU";

		Mockito.when(roleService.getRole("EU")).thenReturn(roleResponseDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		RoleResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), RoleResponseDto.class);

		assertEquals("EU", result.getRoleId());
		assertEquals("end user", result.getRoleName());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	/*
	 * @Test public void deleteCategoryservicesById_success() throws Exception {
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * String URI = "/categoryServices/v1/deleteCategoryService/sdfghjkl";
	 * 
	 * RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
	 * .contentType(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
	 * MockHttpServletResponse response = mvcResult.getResponse();
	 * 
	 * CategoryServiceResponseDto result =
	 * mapper.readValue(mvcResult.getResponse().getContentAsString(),
	 * CategoryServiceResponseDto.class);
	 * 
	 * }
	 */

	@Test
	void getRolesById_throws_NotFoundException() throws Exception {

		Mockito.when(roleService.getRole("EU"))
				.thenThrow(new RoleNotFoundException("Role not found"));

		mockMvc.perform(MockMvcRequestBuilders
				.get("/roles/v1/getRole/{roleId}", "EU").contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof RoleNotFoundException))
				.andExpect(result -> assertEquals("Role not found",
						result.getResolvedException().getMessage()));
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
