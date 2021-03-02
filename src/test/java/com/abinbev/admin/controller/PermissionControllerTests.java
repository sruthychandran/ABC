package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.requestDto.PermissionDto;
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

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
