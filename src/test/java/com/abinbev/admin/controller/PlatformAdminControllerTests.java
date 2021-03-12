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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc


@SpringBootTest

public class PlatformAdminControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlatformAdminService platformAdminService;

	@Autowired
	private ObjectMapper mapper;

	@Test

	public void createUsers() throws Exception {

		UserDto userDto = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();

		UserResponseDto userResponseDTO = UserResponseDto.builder().firstName("rafeek").lastName("ks")
				.emailId("rafeeq088@gmail.com").phoneNo(8089587001l).roleId("TA").status("active")
				.createdDate(new Date()).createdBy("rafeeq088@gmail.com").build();

		BasicResponse<UserResponseDto> responseObj = new BasicResponse<UserResponseDto>();
		responseObj.setData(userResponseDTO);
		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(userDto);
		String URI = "/platformAdminController/v1/user";

		Mockito.when(platformAdminService.saveUser(Mockito.any(UserDto.class))).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<UserResponseDto>> typeReference = new TypeReference<BasicResponse<UserResponseDto>>() {
		};
		BasicResponse<UserResponseDto> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				typeReference);

		assertEquals("rafeeq088@gmail.com", result.getData().getEmailId());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void updateUsers_throws_BadRequestAlertException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks").phoneNo(8089587001l).roleId("TA")
				.status("active").createdDate(new Date()).createdBy("rafeeq088@gmail.com").build();

		mockMvc.perform(put("/platformAdminController/v1/user").contentType("application/json")
				.content(mapper.writeValueAsString(userDto1))).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException))
				.andExpect(result -> assertEquals("Invalid email-Id ", result.getResolvedException().getMessage()));
	}

	@Test
	public void updateUsers_success() throws Exception {

		UserDto userDto = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();

		UserResponseDto userResponseObj = UserResponseDto.builder().id("qwerty").firstName("rafeek").lastName("ks")
				.emailId("rafeeq088@gmail.com").phoneNo(8089587001l).roleId("TA").status("active")
				.createdDate(new Date()).createdBy("rafeeq088@gmail.com").build();

		ObjectMapper mapper = new ObjectMapper();
		BasicResponse<UserResponseDto> responseObj = new BasicResponse<UserResponseDto>();
		responseObj.setData(userResponseObj);
		String inputInJson = this.mapToJson(userDto);
		String URI = "/platformAdminController/v1/user";

		Mockito.when(platformAdminService.updateUser(Mockito.any(UserDto.class))).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<UserResponseDto>> typeReference = new TypeReference<BasicResponse<UserResponseDto>>() {
		};
		BasicResponse<UserResponseDto> result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				typeReference);

		assertEquals("rafeeq088@gmail.com", result.getData().getEmailId());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
