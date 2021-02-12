package com.abinbev.admin.service;




import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.UserResponseDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;


@SpringBootTest
public class PlatformAdminServiceTests {

	@Autowired
	private PlatformAdminService platformAdminService;
	
	@Autowired
	private UserDAO userDAO;
	
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@BeforeEach
	void setUp() {
		 
	}
	@AfterEach
	void afterEach() {
		//log.info("@AfterEach - executed after each test method.");
	}


	
	  @Test 
	  public void test_createUser_success() throws JsonMappingException, JsonProcessingException, DuplicateEmailException { 
		  
		  User user = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com").phoneNo(8089587001L).build();
		  UserDto userDTO = mapper.readValue(mapToJson(user), UserDto.class);
		  UserResponseDto userResponse  = platformAdminService.saveUser(userDTO);
			assertEquals("rafeek", userResponse.getFirstName());
	}
	  
	  @Test 
	  public void test_emailUnique_success() {
		  User user = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com").phoneNo(8089587001L).build();
		user= userDAO.findByEmail(user.getEmailId());
		assertNull(user);
		  
	  }
	  
	  @Test 
	  public void test_emailUnique_fail_throw_exception() throws JsonMappingException, JsonProcessingException {
		  User user = User.builder().firstName("rafeek").lastName("ks").emailId("abdul.r@gmail.com").phoneNo(8089587001L).build();
		  UserDto userDTO = mapper.readValue(mapToJson(user), UserDto.class);
		  user= userDAO.findByEmail(user.getEmailId());
		   Exception exception = assertThrows(DuplicateEmailException.class,
		          () -> platformAdminService.saveUser(userDTO));
		   
		   String expectedMessage = "The email address: abdul.r@gmail.com is already in use.";
		    String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
		  
	  }
	  
	  
	  
	  private String mapToJson(Object object) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		}
	  
	  
	// ====================== Test Data ========================== 
	  
	
}