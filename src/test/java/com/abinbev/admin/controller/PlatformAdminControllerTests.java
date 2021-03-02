package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = { PlatformAdminController.class })

public class PlatformAdminControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlatformAdminService platformAdminService;

	@Autowired
	private ObjectMapper mapper;

	@Test

	public void createUsers() throws Exception {

		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();

		UserDto user1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).build();

		UserResponseDto userDTO = mapper.readValue(mapToJson(userDto1), UserResponseDto.class);

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(userDTO);
		String URI = "/platform-admin/v1/createUser";

		Mockito.when(platformAdminService.saveUser(Mockito.any(UserDto.class))).thenReturn(userDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();

		UserResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseDto.class);

		assertEquals("rafeeq088@gmail.com", result.getEmailId());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}


	@Test
	void updateUsers_throws_BadRequestAlertException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();

		mockMvc.perform(put("/platform-admin/v1/updateUser").contentType("application/json")
				.content(mapper.writeValueAsString(userDto1))).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException))
				.andExpect(result -> assertEquals("Invalid email", result.getResolvedException().getMessage()));
	}

	@Test
	public void updateUsers_success() throws Exception {

		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();

		UserResponseDto userResponseObj = UserResponseDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson( userDto1);
		String URI = "/platform-admin/v1/updateUser";

		Mockito.when(platformAdminService.updateUser(Mockito.any(UserDto.class)))
				.thenReturn(userResponseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		UserResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				UserResponseDto.class);
		assertEquals("rafeeq088@gmail.com", result.getEmailId());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}


	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
