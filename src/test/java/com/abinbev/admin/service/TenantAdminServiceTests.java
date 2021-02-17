package com.abinbev.admin.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.requestDto.CategoryDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class TenantAdminServiceTests{

	@Autowired
	private PlatformAdminService platformAdminService;

	@MockBean
	private UserDAO userDAO;

	@Autowired
	private ObjectMapper mapper;

	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void afterEach() {
		userDAO.deleteAll();

	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void test_createUserWithDifferentEmailIds()
			throws JsonMappingException, JsonProcessingException, EmailExistException, UserCreationFailureException {

		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("enable").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();

		User user = mapper.readValue(mapToJson(userDto1), User.class);
		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user);
		UserResponseDto userResponse1 = platformAdminService.saveUser(userDto1);
		assertNotNull(userResponse1.getEmailId());
		assertEquals("TA", userResponse1.getRoleId());
		assertEquals("rafeeq088@gmail.com", userResponse1.getEmailId());
		assertEquals("rafeek", userResponse1.getFirstName());
		assertEquals("ks", userResponse1.getLastName());

		assertEquals("PLS", userResponse1.getCategories().get(0).getCategoryId());
		assertEquals("NI", userResponse1.getCategories().get(0).getModuleId().get(0));

		assertEquals(8089587001l, userResponse1.getPhoneNo());
		assertEquals("enable", userResponse1.getStatus());
		assertNotNull(userResponse1.getCreatedDate());
		assertEquals("created successfully", userResponse1.getMessage());
		UserDto userDto2 = UserDto.builder().firstName("abi").lastName("shammu").emailId("abi@gmail.com")
				.phoneNo(8089587001l).roleId("EU").status("enable").createdDate(new Date()).createdBy("abi@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PS").moduleId(Arrays.asList("XYZ")).build()))
				.build();
		User user2 = mapper.readValue(mapToJson(userDto2), User.class);
		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user2);

		UserResponseDto userResponse2 = platformAdminService.saveUser(userDto2);
		assertNotNull(userResponse2.getEmailId());
		assertEquals("EU", userResponse2.getRoleId());
		assertEquals("abi@gmail.com", userResponse2.getEmailId());
		assertEquals("abi", userResponse2.getFirstName());
		assertEquals("shammu", userResponse2.getLastName());

		assertEquals("PS", userResponse2.getCategories().get(0).getCategoryId());
		assertEquals("XYZ", userResponse2.getCategories().get(0).getModuleId().get(0));
		assertEquals(8089587001l, userResponse2.getPhoneNo());
		assertEquals("enable", userResponse2.getStatus());
		assertNotNull(userResponse2.getCreatedDate());
		assertEquals("abi@gmail.com", userResponse2.getCreatedBy());
		assertEquals("created successfully", userResponse2.getMessage());

	}

	@Test
	public void test_createUserWithExistingEmail()
			throws JsonMappingException, JsonProcessingException, EmailExistException, UserCreationFailureException {

		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("EU").status("enable").createdDate(new Date()).createdBy("abi@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();
		User user = mapper.readValue(mapToJson(userDto1), User.class);
		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user);
		UserResponseDto userResponse1 = platformAdminService.saveUser(userDto1);

		assertNotNull(userResponse1.getEmailId());

		UserDto user2 = UserDto.builder().firstName("raf").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("EU").status("enable").createdDate(new Date()).createdBy("abi@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();

		Mockito.when(userDAO.findByEmail(user2.getEmailId())).thenReturn(new User());
		assertThrows(EmailExistException.class, () -> platformAdminService.saveUser(user2));

	}

	@Test
	public void test_updateUser_success()
			throws JsonMappingException, JsonProcessingException, EmailExistException, NotFoundException, UserNotFoundException {
		
		 
		  UserResponseDto userResponse1 = UserResponseDto.builder().firstName("rafeek").lastName("ks")
				.emailId("rafeeq088@gmail.com").phoneNo(8089587001l).roleId("TA").status("enable")
				.createdDate(new Date()).createdBy("rafeeq088@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();
		assertNotNull(userResponse1.getEmailId());
		assertEquals("rafeeq088@gmail.com", userResponse1.getEmailId());

		// User retrievedUser = userDAO.findByEmail(userResponse1.getEmailId());
		User retrievedUser = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("enable")
				.createdBy("rafeeq088@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();

		Mockito.when(userDAO.findByEmail(userResponse1.getEmailId())).thenReturn(retrievedUser);
		// Update the required fields of retrieved user
		// The retrieved user must have the primarykey
		assertNotNull(retrievedUser.getEmailId());
		Date createdDate = retrievedUser.getCreatedDate();
		String createdBy = retrievedUser.getCreatedBy();
		
		 User user1 = User.builder().firstName("rafeek").lastName("ks").emailId(
				  "rafeeq088@gmail.com")
				  .phoneNo(8089587001l).roleId("TA").status("enable").createdDate(createdDate).createdBy("rafeeq088@gmail.com") .categories(
				  Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList(
				  "NI")).build())) .build();

		UserDto userDTO1 = mapper.readValue(mapToJson(retrievedUser), UserDto.class);
		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user1);
		UserResponseDto updatedUser = platformAdminService.updateUser(userDTO1);
		assertNotNull(userResponse1.getEmailId());
		// Verifying the created Date andcreated by are not modified in the db
		assertEquals(createdDate, updatedUser.getCreatedDate());
		assertEquals(createdBy, updatedUser.getCreatedBy());
		// assert all the updated values

		assertEquals("TA", updatedUser.getRoleId());
		assertEquals("rafeeq088@gmail.com", updatedUser.getEmailId());
		assertEquals("rafeek", updatedUser.getFirstName());
		assertEquals("ks", updatedUser.getLastName());

		assertEquals("PLS", updatedUser.getCategories().get(0).getCategoryId());
		assertEquals("NI", updatedUser.getCategories().get(0).getModuleId().get(0));
		assertEquals(8089587001l, updatedUser.getPhoneNo());
		assertEquals("enable", updatedUser.getStatus());

		//assertNotNull(updatedUser.getModifiedDate());
		//assertEquals("rafeeq088@gmail.com", updatedUser.getModifiedBy());

		assertEquals("updated successfully", updatedUser.getMessage());

	}

	@Test
	public void test_updateUser_throws_exception()
			throws JsonMappingException, JsonProcessingException, NotFoundException {

	
		UserDto userDto = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("enable")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();
		
		Mockito.when(userDAO.findByEmail(userDto.getEmailId())).thenReturn(null);

		NotFoundException thrown = assertThrows(NotFoundException.class, () -> platformAdminService.updateUser(userDto));
		assertEquals("user not found", thrown.getMessage());
	}

	@Test
	public void test_getAllUsers_success() throws JsonMappingException, JsonProcessingException, NotFoundException {
		User user1 = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("enable").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();
		User user2 = User.builder().firstName("sruthy").lastName("kc").emailId("rskc@gmail.com").phoneNo(9496352903l)
				.roleId("TA").status("enable").createdDate(new Date()).createdBy("rskc@gmail.com")
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();
		List<User> userList = Arrays.asList(user1, user2);

		Mockito.when(userDAO.getAllUsers()).thenReturn(userList);
		List<UserResponseDto> result = platformAdminService.getAllUsers();
		assertNotNull(result.get(0).getEmailId());
		assertEquals("TA", result.get(0).getRoleId());
		assertEquals("rafeeq088@gmail.com", result.get(0).getEmailId());
		assertEquals("rafeek", result.get(0).getFirstName());
		assertEquals("ks", result.get(0).getLastName());
		assertEquals("PLS", result.get(0).getCategories().get(0).getCategoryId());
		assertEquals("NI", result.get(0).getCategories().get(0).getModuleId().get(0));
		assertEquals(8089587001l, result.get(0).getPhoneNo());
		assertEquals("enable", result.get(0).getStatus());
		assertNotNull(result.get(0).getCreatedDate());
		assertEquals("rafeeq088@gmail.com", result.get(0).getCreatedBy());

	}

}