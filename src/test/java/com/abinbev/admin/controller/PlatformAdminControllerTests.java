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
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.PlatformAdminService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@WebMvcTest(controllers = {PlatformAdminController.class})

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
				.phoneNo(8089587001l).roleId("TA").status("enable").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();

		/*
		 * UserDto user1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId(
		 * "rafeeq088@gmail.com") .phoneNo(8089587001l).build();
		 */
		  
		  UserResponseDto userDTO = mapper.readValue(mapToJson(userDto1), UserResponseDto.class);
		  
		  ObjectMapper mapper=new ObjectMapper();


		String inputInJson = this.mapToJson(userDTO);
		String URI = "/platform-admin/createUser";

		Mockito.when(platformAdminService.saveUser(Mockito.any(UserDto.class))).thenReturn(userDTO);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		UserResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponseDto.class);
	
		assertEquals("rafeeq088@gmail.com", result.getEmailId());
	    assertEquals("PLS", result.getCategories().get(0).getCategoryId());
		assertEquals("NI", result.getCategories().get(0).getModuleId().get(0));
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
