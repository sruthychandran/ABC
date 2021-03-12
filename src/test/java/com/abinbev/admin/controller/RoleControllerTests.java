package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.RoleNotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.RoleService;
import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@AutoConfigureMockMvc
@SpringBootTest
public class RoleControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoleService roleService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void createRoles() throws Exception {

		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").status("active").build();

		RoleResponseDto roleResponseDto = RoleResponseDto.builder().roleId("EU").roleName("end user").status("active")
				.userRole("TA").createdDate(new Date(2021, 2, 1)).build();
		BasicResponse<RoleResponseDto> responseObj = new BasicResponse<RoleResponseDto>();
		responseObj.setData(roleResponseDto);
		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(roleDto);
		String URI = "/roleController/v1/role";

		Mockito.when(roleService.saveRole(Mockito.any(RoleDto.class))).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		
		TypeReference<BasicResponse<RoleResponseDto>> typeReference = new TypeReference<BasicResponse<RoleResponseDto>>() {
		};
		BasicResponse<RoleResponseDto> result = mapper
				.readValue(mvcResult.getResponse().getContentAsString(), typeReference);


		assertEquals("EU", result.getData().getRoleId());
		assertEquals("end user", result.getData().getRoleName());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void updateRole_throws_BadRequestAlertException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("end user").status("active").build();

		mockMvc.perform(
				put("/roleController/v1/role").contentType("application/json").content(mapper.writeValueAsString(roleDto)))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException))
				.andExpect(result -> assertEquals("Invalid Id ", result.getResolvedException().getMessage()));
	}

	/*
	 * @Test public void updateCategoryService_throws_BadRequestAlertException()
	 * throws Exception { ObjectMapper mapper = new ObjectMapper();
	 * CategoryServiceDto categoryServiceDto =
	 * CategoryServiceDto.builder().categoryId("CS")
	 * .categoryName("coreService").moduleId("NI").moduleName("Notification Service"
	 * ).status("active")
	 * .subModuleId("TA-Add").subModuleName("Tenant Addition").build();
	 * 
	 * mockMvc.perform(put("/categoryController/v1/categoryService").contentType(
	 * "application/json")
	 * .content(mapper.writeValueAsString(categoryServiceDto))).andExpect(status().
	 * isBadRequest()) .andExpect(result -> assertTrue(result.getResolvedException()
	 * instanceof BadRequestAlertException)) .andExpect(result ->
	 * assertEquals("Invalid Id ", result.getResolvedException().getMessage())); }
	 */
	
	
	
	
	
	
	
	
	@Test
	public void updateRoles_success() throws Exception {

		RoleDto roleDto = RoleDto.builder().id("sdfghjkl").roleId("EU").roleName("end user").status("active")
				.build();

		RoleResponseDto roleResponseDto = RoleResponseDto.builder().id("sdfghjkl").roleId("EU").roleName("end user")
				.status("active").userRole("TA").createdDate(new Date(2021, 2, 1)).build();
		BasicResponse<RoleResponseDto> responseObj = new BasicResponse<RoleResponseDto>();
		responseObj.setData(roleResponseDto);
		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(roleDto);
		String URI = "/roleController/v1/role";

		Mockito.when(roleService.updateRole(Mockito.any(RoleDto.class))).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		TypeReference<BasicResponse<RoleResponseDto>> typeReference = new TypeReference<BasicResponse<RoleResponseDto>>() {
		};
		BasicResponse<RoleResponseDto> result = mapper
				.readValue(mvcResult.getResponse().getContentAsString(), typeReference);


		assertEquals("EU", result.getData().getRoleId());
		assertEquals("end user", result.getData().getRoleName());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getRolesById_success() throws Exception {

		RoleResponseDto roleResponseDto = RoleResponseDto.builder().id("sdfghjkl").roleId("EU").roleName("end user")
				.status("active").createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String URI = "/roleController/v1/role/EU";
		BasicResponse<RoleResponseDto> responseObj = new BasicResponse<RoleResponseDto>();
		responseObj.setData(roleResponseDto);
		Mockito.when(roleService.getRole("EU")).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();


		TypeReference<BasicResponse<RoleResponseDto>> typeReference = new TypeReference<BasicResponse<RoleResponseDto>>() {
		};
		BasicResponse<RoleResponseDto> result = mapper
				.readValue(mvcResult.getResponse().getContentAsString(), typeReference);

		assertEquals("EU", result.getData().getRoleId());
		assertEquals("end user", result.getData().getRoleName());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void getRolesById_throws_NotFoundException() throws Exception {
		ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage();
		errorCodeMessage.setErrorCode("20001");
		errorCodeMessage.setErrorMessage("Role not exists");
		Mockito.when(roleService.getRole("EU")).thenThrow(new RoleNotFoundException(errorCodeMessage));

		mockMvc.perform(MockMvcRequestBuilders.get("/roleController/v1/role/{roleId}", "EU").contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof RoleNotFoundException))
				.andExpect(result -> assertEquals("Role not exists", result.getResolvedException().getMessage()));
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
