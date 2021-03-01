package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

		
		PermissionDto permissionDto = PermissionDto.builder().permissionId("EU").permissionName("End User").build();
		  
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
	
		assertEquals("EU", result.getPermissionId());
		assertEquals("End User", result.getPermissionName());
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
