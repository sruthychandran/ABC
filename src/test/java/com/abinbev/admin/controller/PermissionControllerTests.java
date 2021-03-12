package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.service.PermissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest

public class PermissionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PermissionService permissionService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void createPermissions() throws Exception {

		PermissionDto permissionDto = PermissionDto.builder().permissionId("AU").permissionName("Add User")
				.permissionDescription("Can add a user" + "").roleId("EU").build();

		PermissionResponseDto permissionResponseDto = mapper.readValue(mapToJson(permissionDto),
				PermissionResponseDto.class);
		BasicResponse<PermissionResponseDto> responseObj = new BasicResponse<PermissionResponseDto>();
		responseObj.setData(permissionResponseDto);

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(permissionDto);
		String URI = "/permissionController/v1/permission";

		Mockito.when(permissionService.savePermission(Mockito.any(PermissionDto.class))).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<PermissionResponseDto>> typeReference = new TypeReference<BasicResponse<PermissionResponseDto>>() {
		};
		BasicResponse<PermissionResponseDto> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				typeReference);


		assertEquals("AU", result.getData().getPermissionId());
		assertEquals("Add User", result.getData().getPermissionName());
		assertEquals("Can add a user", result.getData().getPermissionDescription());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updatePermission_success() throws Exception {

		PermissionDto permissionDto = PermissionDto.builder().id("qwerty").permissionId("AU").permissionName("Add User")
				.permissionDescription("Can add a user" + "").roleId("EU").build();

		PermissionResponseDto permissionResponse = PermissionResponseDto.builder().id("qwerty").permissionId("AU")
				.permissionName("Add User").permissionDescription("Can add a user" + "").roleId("EU")
				.createdDate(new Date(2021, 3, 2)).build();
		BasicResponse<PermissionResponseDto> responseObj = new BasicResponse<PermissionResponseDto>();
		responseObj.setData(permissionResponse);
		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(permissionDto);
		String URI = "/permissionController/v1/permission";

		Mockito.when(permissionService.updatePermission(Mockito.any(PermissionDto.class))).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<PermissionResponseDto>> typeReference = new TypeReference<BasicResponse<PermissionResponseDto>>() {
		};
		BasicResponse<PermissionResponseDto> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				typeReference);

		assertEquals("AU", result.getData().getPermissionId());
		assertEquals("Add User", result.getData().getPermissionName());
		assertEquals("Can add a user", result.getData().getPermissionDescription());
		assertEquals("EU", result.getData().getRoleId());
		assertNotNull(result.getData().getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	/**
	 * Test delete with valid
	 * 
	 * @throws Exception
	 * @Expect HttpStatus is 200 for valid id
	 * 
	 *//*
		 * @DisplayName("TestDelete")
		 * 
		 * @Test public void testDeleteWithValidId() throws Exception {
		 * 
		 * PermissionDto permissionDto =
		 * PermissionDto.builder().permissionId("AU").permissionName("Add User")
		 * .permissionDescription("Can add a user" + "").roleId("EU").build();
		 * 
		 * PermissionResponseDto permissionResponse =
		 * PermissionResponseDto.builder().permissionId("AU")
		 * .permissionName("Add User").permissionDescription("Can add a user" +
		 * "").roleId("EU") .createdDate(new Date(2021, 3, 2)).build();
		 * 
		 * Mockito.when(permissionService.getPermission(permissionDto.getId())).
		 * thenReturn(permissionResponse);
		 * 
		 * String URI = "/permissions/v1/deletePermission/" + permissionDto.getId();
		 * RequestBuilder requestBuilder =
		 * MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON); MvcResult
		 * result = mockMvc.perform(requestBuilder).andReturn(); MockHttpServletResponse
		 * response = result.getResponse(); assertEquals(HttpStatus.OK.value(),
		 * response.getStatus());
		 * 
		 * }
		 */
	@Test
	public void getPermissionsById_success() throws Exception {

		PermissionDto permissionDto = PermissionDto.builder().permissionId("AU").permissionName("Add User")
				.permissionDescription("Can add a user" + "").roleId("EU").build();

		PermissionResponseDto permissionResponse = PermissionResponseDto.builder().permissionId("AU")
				.permissionName("Add User").permissionDescription("Can add a user" + "").roleId("EU").build();

		ObjectMapper mapper = new ObjectMapper();

		String URI = "/permissionController/v1/permission/" + permissionDto.getPermissionId();
		BasicResponse<PermissionResponseDto> responseObj = new BasicResponse<PermissionResponseDto>();
		responseObj.setData(permissionResponse);
		Mockito.when(permissionService.getPermission(permissionDto.getPermissionId())).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<PermissionResponseDto>> typeReference = new TypeReference<BasicResponse<PermissionResponseDto>>() {
		};
		BasicResponse<PermissionResponseDto> result = mapper
				.readValue(mvcResult.getResponse().getContentAsString(), typeReference);
		

		assertEquals("AU", result.getData().getPermissionId());
		assertEquals("Add User", result.getData().getPermissionName());
		assertEquals("Can add a user", result.getData().getPermissionDescription());
		assertEquals("EU", result.getData().getRoleId());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
