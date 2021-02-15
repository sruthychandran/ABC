package com.abinbev.admin.service;



import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.requestDto.CategoryDto;
import com.abinbev.admin.requestDto.UserDto;
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
		// log.info("@AfterEach - executed after each test method.");
	}

	/*
	 * @Test public void test_emailUnique_success() { User user =
	 * User.builder().firstName("rafeek").lastName("ks").emailId(
	 * "rafeeq088@gmail.com").phoneNo(8089587001L).build(); user=
	 * userDAO.findByEmail(user.getEmailId()); assertNull(user);
	 * 
	 * 
	 * }
	 */

	/*
	 * @Test public void test_emailUnique_fail_throw_exception() throws
	 * JsonMappingException, JsonProcessingException { User user =
	 * User.builder().firstName("rafeek").lastName("ks").emailId("abdul.r@gmail.com"
	 * ).phoneNo(8089587001L).build(); UserDto userDTO =
	 * mapper.readValue(mapToJson(user), UserDto.class); user=
	 * userDAO.findByEmail(user.getEmailId()); Exception exception =
	 * assertThrows(DuplicateEmailException.class, () ->
	 * platformAdminService.saveUser(userDTO));
	 * 
	 * String expectedMessage =
	 * "The email address: abdul.r@gmail.com is already in use."; String
	 * actualMessage = exception.getMessage();
	 * 
	 * assertTrue(actualMessage.contains(expectedMessage));
	 * 
	 * }
	 */

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	// ====================== Test Data ==========================

	/*
	 * @Test public void test_createUserWithDifferentEmailIds() throws
	 * JsonMappingException, JsonProcessingException, DuplicateEmailException {
	 * 
	 * UserDto user1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId(
	 * "rafeeq088@gmail.com") .phoneNo(8089587001l) .categories(
	 * Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList(
	 * "NI")).build())) .build();
	 * 
	 * UserResponseDto userResponse1 = platformAdminService.saveUser(user1);
	 * assertNotNull(userResponse1.getUuid()); assertEquals("rafeeq088@gmail.com",
	 * userResponse1.getEmailId()); assertEquals("PLS",
	 * userResponse1.getCategories().get(0).getCategoryId()); assertEquals("NI",
	 * userResponse1.getCategories().get(0).getModuleId().get(0));
	 * 
	 * UserDto user2 =
	 * UserDto.builder().firstName("abi").lastName("shammu").emailId(
	 * "abi@gmail.com") .phoneNo(8089587001l) .categories(
	 * Arrays.asList(CategoryDto.builder().categoryId("PS").moduleId(Arrays.asList(
	 * "XYZ")).build())) .build(); UserResponseDto userResponse2 =
	 * platformAdminService.saveUser(user2); assertNotNull(userResponse2.getUuid());
	 * assertEquals("abi@gmail.com", userResponse2.getEmailId()); assertEquals("PS",
	 * userResponse2.getCategories().get(0).getCategoryId()); assertEquals("XYZ",
	 * userResponse1.getCategories().get(0).getModuleId().get(0));
	 * 
	 * }
	 * 
	 * @Test public void test_createUserWithExistingEmail() throws
	 * JsonMappingException, JsonProcessingException, DuplicateEmailException {
	 * 
	 * UserDto user1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId(
	 * "rafeeq088@gmail.com") .phoneNo(8089587001l) .categories(
	 * Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList(
	 * "NI")).build())) .build(); UserResponseDto userResponse1 =
	 * platformAdminService.saveUser(user1); assertNotNull(userResponse1.getUuid());
	 * 
	 * UserDto user2 = UserDto.builder().firstName("rafeek").lastName("ks").emailId(
	 * "rafeeq088@gmail.com") .phoneNo(8089587001l) .categories(
	 * Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList(
	 * "NI")).build())) .build(); assertThrows(DuplicateEmailException.class, () ->
	 * platformAdminService.saveUser(user2));
	 * 
	 * }
	 */
	@Test
	public void test_updateUser_success() throws JsonMappingException, JsonProcessingException, DuplicateEmailException, UserNotFoundException {
		UserDto user1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l)
				.categories(
						Arrays.asList(CategoryDto.builder().categoryId("PLS").moduleId(Arrays.asList("NI")).build()))
				.build();

		UserResponseDto userResponse1 = platformAdminService.saveUser(user1);
		assertNotNull(userResponse1.getUuid());
		assertEquals("rafeeq088@gmail.com", userResponse1.getEmailId());
		assertEquals("PLS", userResponse1.getCategories().get(0).getCategoryId());
		assertEquals("NI", userResponse1.getCategories().get(0).getModuleId().get(0));

		User retrievedUser = userDAO.findByEmail(user1.getEmailId());
		// Update the required fields of retrieved user
		// The retrieved user must have the primary key
		Date createdDate = retrievedUser.getCreatedDate();
		String createdBy = retrievedUser.getCreatedBy();

		// The created date and created by are modified now
		// retrievedUser.setCreatedBy(newCreatedDate);
		// retrievedUser.setCreatedBy(newCreatedBy);
		UserDto userDTO1 = mapper.readValue(mapToJson(retrievedUser), UserDto.class);
		UserResponseDto updatedUser = platformAdminService.updateUser(userDTO1);
		assertNotNull(updatedUser.getUuid());
		// Verifying the created Date and created by are not modified in the db
		assertEquals(createdDate, updatedUser.getCreatedBy());
		assertEquals(createdBy, updatedUser.getCreatedBy());
		// assert all the updated values
	}

}