package com.abinbev.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.abinbev.admin.dao.PermissionDAO;
import com.abinbev.admin.entity.Permission;
import com.abinbev.admin.exception.PermissionCreationFailureException;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.exception.PermissionUpdationFailureException;
import com.abinbev.admin.requestDto.PermissionDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.PermissionResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class PermissionServiceTests {

	@Autowired
	private PermissionService permissionService;

	@MockBean
	private PermissionDAO permissionDAO;

	@Autowired
	private ObjectMapper mapper;

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void test_createPermissions_success()
			throws JsonMappingException, JsonProcessingException, PermissionCreationFailureException {
		PermissionDto permissionDto = PermissionDto.builder().permissionId("EU").permissionName("end user").build();
		Permission permission = Permission.builder().permissionId("EU").permissionName("end user")
				.createdDate(new Date()).build();

		Mockito.when(permissionDAO.save(Mockito.any(Permission.class))).thenReturn(permission);
		BasicResponse<PermissionResponseDto> returnedUser = permissionService.savePermission(permissionDto);
		assertNotNull(returnedUser.getData().getPermissionId());
		assertEquals("EU", returnedUser.getData().getPermissionId());
		assertEquals("end user", returnedUser.getData().getPermissionName());
		assertNotNull(returnedUser.getData().getCreatedDate());

	}

	
	  @Test public void test_updatePermissions_success() throws
	  JsonMappingException, JsonProcessingException, PermissionNotFoundException,
	  PermissionUpdationFailureException { PermissionDto permissionDto =
	  PermissionDto.builder().permissionId("EU").permissionName("end user").build()
	  ;
	  
	  Permission permission = mapper.readValue(mapToJson(permissionDto),
	  Permission.class);
	  
	  PermissionResponseDto saved =
	  PermissionResponseDto.builder().id("qwerty").permissionId("EU")
	  .permissionName("end user").createdDate(new Date()).build();
	  Mockito.when(permissionDAO.findById(saved.getId())).thenReturn(permission);
	  saved.setPermissionId("EU"); saved.setPermissionName("Permission updated");
	  saved.setModifiedDate(new Date()); PermissionDto updateRequest =
	  mapper.readValue(mapToJson(saved), PermissionDto.class); Permission
	  permission1 = mapper.readValue(mapToJson(updateRequest), Permission.class);
	  Mockito.when(permissionDAO.save(Mockito.any(Permission.class))).thenReturn(
	  permission1); BasicResponse<PermissionResponseDto> updatedPermission =
	  permissionService.updatePermission(updateRequest);
	  
	  assertNotNull(updatedPermission.getData().getPermissionId());
	  assertEquals("Permission updated",
	  updatedPermission.getData().getPermissionName());
	  
	  }
	  
	  @Test public void test_updatePermissions_throws_exception() throws
	  JsonMappingException, JsonProcessingException, PermissionNotFoundException {
	  
	  PermissionDto permissionDto =
	  PermissionDto.builder().permissionId("EU").permissionName("end user").build()
	  ;
	  
	  Mockito.when(permissionDAO.findByPermissionId(permissionDto.getPermissionId()
	  )).thenReturn(null);
	  
	  PermissionNotFoundException thrown =
	  assertThrows(PermissionNotFoundException.class, () ->
	  permissionService.updatePermission(permissionDto));
	  
	  }
	  
	  @Test public void test_getAllPermissions() throws JsonMappingException,
	  JsonProcessingException, PermissionNotFoundException { Permission permission1
	  = Permission.builder().permissionId("EU").permissionName("end user")
	  .createdDate(new Date()).build(); Permission permission2 =
	  Permission.builder().permissionId("TA").permissionName("tenant admin")
	  .createdDate(new Date()).build();
	  
	  List<Permission> permissionList = Arrays.asList(permission1, permission2);
	  
	  Page<Permission> page = new PageImpl<>(permissionList);
	  
	  Mockito.when(permissionDAO.getAllPermissions(PageRequest.of(0,
	  20))).thenReturn(page);
	  
	  // assertThat(permissionService.getAllPermissions(PageRequest.of(0, //
	 // 20)).getTotalElements()).isEqualTo(2);
	  
	  }
	 

	
		/*
		 * @Test public void test_deletePermissions_success() throws
		 * JsonMappingException, JsonProcessingException, PermissionNotFoundException {
		 * 
		 * Permission permission = Permission.builder().id("qwerty").permissionId("EU").
		 * permissionName("end user")
		 * .permissionDescription("permissionDescription").build();
		 * 
		 * Mockito.when(permissionDAO.findById("qwerty")).thenReturn(permission);
		 * 
		 * permissionService.deletePermission(permission.getId());
		 * 
		 * }
		 */
	 

}