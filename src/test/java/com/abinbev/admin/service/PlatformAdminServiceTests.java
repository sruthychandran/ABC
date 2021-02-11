package com.abinbev.admin.service;




import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;




@SpringBootTest
public class PlatformAdminServiceTests {

	@Autowired
	private PlatformAdminService platformAdminService;
	
	
	@Autowired
	private ObjectMapper mapper;
	
	  @Test 
	  public void test_createUser_success() throws JsonMappingException, JsonProcessingException { 
		  
		  
		  
		  User user = User.builder().firstName("rafeek").build();
		  
		  UserDto userDTO = mapper.readValue(mapToJson(user), UserDto.class);
		  UserResponseDto userResponse  = platformAdminService.saveUser(userDTO);
			assertEquals("rafeek", userResponse.getFirstName());
	}
	  
	  
	  
	  
	  private String mapToJson(Object object) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		}
	  
	
}
