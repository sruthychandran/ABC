package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
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

import com.abinbev.admin.entity.Permission;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.abinbev.admin.service.PermissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {PermissionController.class})
public class PermissionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PermissionService permissionService;

	@Autowired
	private ObjectMapper mapper;

	@Test
    public void createPermissions() throws Exception {

		
		PermissionDto permissionDto = PermissionDto.builder().permissionId("AU").permissionName("Add User").permissionDescription("Can add a user"
				+ "").roleId("EU").build();
		  
		  PermissionResponseDto permissionResponseDto = mapper.readValue(mapToJson(permissionDto), PermissionResponseDto.class);
		  
		  ObjectMapper mapper=new ObjectMapper();


		String inputInJson = this.mapToJson(permissionDto);
		String URI = "/permissions/v1/createPermission";

		Mockito.when(permissionService.savePermission(Mockito.any(PermissionDto.class))).thenReturn(permissionResponseDto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		PermissionResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), PermissionResponseDto.class);
	
		assertEquals("AU", result.getPermissionId());
		assertEquals("Add User", result.getPermissionName());
		assertEquals("Can add a user",result.getPermissionDescription());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updatePermission_success() throws Exception {

		PermissionDto permissionDto = PermissionDto.builder().permissionId("AU").permissionName("Add User").permissionDescription("Can add a user"
				+ "").roleId("EU").build();
		
		PermissionResponseDto permissionResponse = PermissionResponseDto.builder().permissionId("AU").permissionName("Add User").permissionDescription("Can add a user"
				+ "").roleId("EU").createdDate(new Date(2021, 3, 2)).build();

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(permissionDto);
		String URI = "/permissions/v1/updatePermission";

		Mockito.when(permissionService.updatePermission(Mockito.any(PermissionDto.class)))
				.thenReturn(permissionResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		PermissionResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				PermissionResponseDto.class);

		assertEquals("AU", result.getPermissionId());
		assertEquals("Add User", result.getPermissionName());
		assertEquals("Can add a user", result.getPermissionDescription());
		assertEquals("EU", result.getRoleId());
		assertNotNull(result.getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	/**
	 * Test delete with valid
	 * 
	 * @throws Exception
	 * @Expect HttpStatus is 200 for valid id
	 * 
	 */
	@DisplayName("TestDelete")
	@Test
	public void testDeleteWithValidId() throws Exception {
		
		PermissionDto permissionDto = PermissionDto.builder().permissionId("AU").permissionName("Add User").permissionDescription("Can add a user"
				+ "").roleId("EU").build();
		
		PermissionResponseDto permissionResponse = PermissionResponseDto.builder().permissionId("AU").permissionName("Add User").permissionDescription("Can add a user"
				+ "").roleId("EU").createdDate(new Date(2021, 3, 2)).build();

		Mockito.when(permissionService.getPermission(permissionDto.getId())).thenReturn(permissionResponse);

		String URI = "/permissions/v1/deletePermission/" + permissionDto.getId();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void getPermissionsById_success() throws Exception {

		PermissionDto permissionDto = PermissionDto.builder().permissionId("AU").permissionName("Add User").permissionDescription("Can add a user"
				+ "").roleId("EU").build();
		

		PermissionResponseDto permissionResponse = PermissionResponseDto.builder().permissionId("AU").permissionName("Add User").permissionDescription("Can add a user"
				+ "").roleId("EU").build();
	
		ObjectMapper mapper = new ObjectMapper();

		String URI = "/permissions/v1/getPermission/"+permissionDto.getPermissionId();

		Mockito.when(permissionService.getPermission(permissionDto.getPermissionId())).thenReturn(permissionResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		PermissionResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				PermissionResponseDto.class);

		assertEquals("AU", result.getPermissionId());
		assertEquals("Add User", result.getPermissionName());
		assertEquals("Can add a user", result.getPermissionDescription());
		assertEquals("EU", result.getRoleId());
		assertNotNull(result.getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}


	@Test
	public void getPermissionById_throws_NotFoundException() throws Exception {
		
		Mockito.when(permissionService.getPermission("sdfghjkl"))
		.thenThrow(new PermissionNotFoundException("Permission not found"));

		mockMvc.perform(MockMvcRequestBuilders
		.get("/permissions/v1/getPermission/{id}", "sdfghjkl").contentType("application/json"))
		.andExpect(status().isNotFound())
		.andExpect(
				result -> assertTrue(result.getResolvedException() instanceof PermissionNotFoundException))
		.andExpect(result -> assertEquals("Permission not found",
				result.getResolvedException().getMessage()));

}

	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	

}
