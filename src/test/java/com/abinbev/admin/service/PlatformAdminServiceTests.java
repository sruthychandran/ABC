package com.abinbev.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.exception.EmailExistException;
import com.abinbev.admin.exception.UserCreationFailureException;
import com.abinbev.admin.exception.UserNotFoundException;
import com.abinbev.admin.exception.UserUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class PlatformAdminServiceTests {

	@Autowired
	private PlatformAdminService platformAdminService;

	@MockBean
	private UserDAO userDAO;

	@Autowired
	private ObjectMapper mapper;

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void test_createUserWithDifferentEmailIds()
			throws JsonMappingException, JsonProcessingException, EmailExistException, UserCreationFailureException {

		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com")

				.build();

		User user = mapper.readValue(mapToJson(userDto1), User.class);
		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user);
		BasicResponse<UserResponseDto> userResponse1 = platformAdminService.saveUser(userDto1);
		assertNotNull(userResponse1.getData().getEmailId());
		assertEquals("TA", userResponse1.getData().getRoleId());
		assertEquals("rafeeq088@gmail.com", userResponse1.getData().getEmailId());
		assertEquals("rafeek", userResponse1.getData().getFirstName());
		assertEquals("ks", userResponse1.getData().getLastName());

		assertEquals(8089587001l, userResponse1.getData().getPhoneNo());
		assertEquals("active", userResponse1.getData().getStatus());
		assertNotNull(userResponse1.getData().getCreatedDate());
		// assertEquals("created successfully", userResponse1.getMessage());
		 UserDto userDto2 = UserDto.builder().firstName("abi").lastName("shammu").emailId("abi@gmail.com").phoneNo(8089587001l)
				.roleId("EU").status("active").createdDate(new Date()).createdBy("abi@gmail.com")

				.build();
		User user2 = mapper.readValue(mapToJson(userDto2), User.class);
		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user2);

		BasicResponse<UserResponseDto> userResponse2 = platformAdminService.saveUser(userDto2);
		assertNotNull(userResponse2.getData().getEmailId());
		assertEquals("EU", userResponse2.getData().getRoleId());
		assertEquals("abi@gmail.com", userResponse2.getData().getEmailId());
		assertEquals("abi", userResponse2.getData().getFirstName());
		assertEquals("shammu", userResponse2.getData().getLastName());

		assertEquals(8089587001l, userResponse2.getData().getPhoneNo());
		assertEquals("active", userResponse2.getData().getStatus());
		assertNotNull(userResponse2.getData().getCreatedDate());
		assertEquals("abi@gmail.com", userResponse2.getData().getCreatedBy());
		// assertEquals("created successfully", userResponse2.getMessage());

	}

	@Test
	public void test_createUserWithExistingEmail()
			throws JsonMappingException, JsonProcessingException, EmailExistException, UserCreationFailureException {

		UserDto userDto1 = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("EU").status("active").createdDate(new Date()).createdBy("abi@gmail.com")

				.build();
		User user = mapper.readValue(mapToJson(userDto1), User.class);

		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user);
		BasicResponse<UserResponseDto> userResponse1 = platformAdminService.saveUser(userDto1);

		assertNotNull(userResponse1.getData().getEmailId());

		UserDto user2 = UserDto.builder().firstName("raf").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("EU").status("active").createdDate(new Date()).createdBy("abi@gmail.com")

				.build();

		Mockito.when(userDAO.findByEmail(user2.getEmailId())).thenReturn(new User());
		assertThrows(EmailExistException.class, () -> platformAdminService.saveUser(user2));

	}

	@Test
	public void test_updateUser_success() throws JsonMappingException, JsonProcessingException, EmailExistException,
			NotFoundException, UserNotFoundException, UserUpdationFailureException {

		UserResponseDto userResponse1 = UserResponseDto.builder().firstName("rafeek").lastName("ks")
				.emailId("rafeeq088@gmail.com").phoneNo(8089587001l).roleId("TA").status("enable")
				.createdDate(new Date()).createdBy("rafeeq088@gmail.com").build();

		User retrievedUser = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdBy("rafeeq088@gmail.com").build();

		Mockito.when(userDAO.findByEmail(userResponse1.getEmailId())).thenReturn(retrievedUser);
		assertNotNull(retrievedUser.getEmailId());
		Date createdDate = retrievedUser.getCreatedDate();
		String createdBy = retrievedUser.getCreatedBy();

		User user1 = User.builder().firstName("abdulrafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(createdDate)
				.createdBy("rafeeq088@gmail.com").build();

		UserDto userDTO1 = mapper.readValue(mapToJson(retrievedUser), UserDto.class);
		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(user1);
		BasicResponse<UserResponseDto> updatedUser = platformAdminService.updateUser(userDTO1);
		assertNotNull(userResponse1.getEmailId());

		assertEquals(createdDate, updatedUser.getData().getCreatedDate());
		assertEquals(createdBy, updatedUser.getData().getCreatedBy());

		assertEquals("TA", updatedUser.getData().getRoleId());
		assertEquals("rafeeq088@gmail.com", updatedUser.getData().getEmailId());
		assertEquals("abdulrafeek", updatedUser.getData().getFirstName());
		assertEquals("ks", updatedUser.getData().getLastName());

		assertEquals(8089587001l, updatedUser.getData().getPhoneNo());
		assertEquals("active", updatedUser.getData().getStatus());

		// assertEquals("updated successfully", updatedUser.getMessage());

	}

	@Test
	public void test_updateUser_throws_exception()
			throws JsonMappingException, JsonProcessingException, UserNotFoundException {

		UserDto userDto = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active")

				.build();

		Mockito.when(userDAO.findByEmail(userDto.getEmailId())).thenReturn(null);

		UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
				() -> platformAdminService.updateUser(userDto));
		assertEquals("User not exists", thrown.getMessage());
	}

	@Test
	public void test_getAllUsers_success() throws JsonMappingException, JsonProcessingException, NotFoundException {
		User user1 = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com")

				.build();
		User user2 = User.builder().firstName("sruthy").lastName("kc").emailId("rskc@gmail.com").phoneNo(9496352903l)
				.roleId("TA").status("active").createdDate(new Date()).createdBy("rskc@gmail.com")

				.build();
		List<User> userList = Arrays.asList(user1, user2);

		Page<User> page = new PageImpl<>(userList);

		Mockito.when(userDAO.getAllUsers(PageRequest.of(0, 20))).thenReturn(page);

	//	assertThat(platformAdminService.getAllUsers(PageRequest.of(0, 20)).getTotalElements()).isEqualTo(2);

	}

	@Test
	public void test_deleteUser() throws UserNotFoundException {
		User user = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();
		User response = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com").build();

		Mockito.when(userDAO.findByEmail(user.getEmailId())).thenReturn(response);

	}

	@Test
	public void test_findByEmailId_success() throws JsonMappingException, JsonProcessingException,
			CategoryServiceNotFoundException, UserNotFoundException {
		User user = User.builder().firstName("rafeek").lastName("ks").emailId("rafeeq088@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active").createdDate(new Date())
				.createdBy("rafeeq088@gmail.com")

				.build();
		Mockito.when(userDAO.findByEmail("rafeeq088@gmail.com")).thenReturn(user);

		BasicResponse<UserResponseDto> userResponseObj = platformAdminService.findByEmailId("rafeeq088@gmail.com");

		assertNotNull(userResponseObj.getData().getEmailId());
		assertEquals("TA", userResponseObj.getData().getRoleId());
		assertEquals("rafeeq088@gmail.com", userResponseObj.getData().getEmailId());
		assertEquals("rafeek", userResponseObj.getData().getFirstName());
		assertEquals("ks", userResponseObj.getData().getLastName());

		assertEquals(8089587001l, userResponseObj.getData().getPhoneNo());
		assertEquals("active", userResponseObj.getData().getStatus());

	}

	@Test
	public void test_findByEmailId_throws_exception()
			throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {

		Mockito.when(userDAO.findByEmail("rafeeq088@gmail.com")).thenReturn(null);
		UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
				() -> platformAdminService.findByEmailId("sdfghjkl"));
		assertEquals("User not exists", thrown.getMessage());

	}

	@Test
	public void test_updateUserService_throws_UserUpdationFailureException()
			throws JsonMappingException, JsonProcessingException, CategoryServiceUpdationFailureException {

		UserDto userDto = UserDto.builder().firstName("rafeek").lastName("ks").emailId("rafeeq@gmail.com")
				.phoneNo(8089587001l).roleId("TA").status("active")

				.build();
		Mockito.when(userDAO.findByEmail(userDto.getEmailId())).thenReturn(new User());

		Mockito.when(userDAO.save(Mockito.any(User.class))).thenReturn(null);

		UserUpdationFailureException thrown = assertThrows(UserUpdationFailureException.class,
				() -> platformAdminService.updateUser(userDto));
		assertEquals("Update user operation is failed", thrown.getMessage());
	}

}