package com.abinbev.admin.controller;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.CategoryDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.abinbev.admin.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

		RoleDto roleDto = RoleDto.builder().roleId("EU").roleName("End User").build();

		RoleResponseDto roleResponseDto = mapper.readValue(mapToJson(roleDto), RoleResponseDto.class);

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
		assertEquals("End User", result.getRoleName());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(object);
	}

}
