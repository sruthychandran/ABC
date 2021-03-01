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

import com.abinbev.admin.dao.PermissionDAO;
import com.abinbev.admin.entity.Permission;
import com.abinbev.admin.exception.PermissionCreationFailureException;
import com.abinbev.admin.exception.PermissionNotFoundException;
import com.abinbev.admin.exception.PermissionUpdationFailureException;
import com.abinbev.admin.requestDto.PermissionDto;
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
		PermissionResponseDto returnedUser = permissionService.savePermission(permissionDto);
		assertNotNull(returnedUser.getPermissionId());
		assertEquals("EU", returnedUser.getPermissionId());
		assertEquals("end user", returnedUser.getPermissionName());
		assertNotNull(returnedUser.getCreatedDate());
		assertEquals("created successfully", returnedUser.getMessage());

	}

	@Test
	public void test_updatePermissions_success()
			throws JsonMappingException, JsonProcessingException, PermissionNotFoundException, PermissionUpdationFailureException {
		PermissionDto permissionDto = PermissionDto.builder().permissionId("EU").permissionName("end user").build();

		Permission permission = mapper.readValue(mapToJson(permissionDto), Permission.class);

		PermissionResponseDto saved = PermissionResponseDto.builder().id("qwerty").permissionId("EU").permissionName("end user").createdDate(new Date()).
				build();
		Mockito.when(permissionDAO.findById(saved.getId())).thenReturn(permission);
		saved.setPermissionId("EU");
		saved.setPermissionName("Permission updated");
		saved.setModifiedDate(new Date());
		PermissionDto updateRequest = mapper.readValue(mapToJson(saved), PermissionDto.class);
		Permission permission1 = mapper.readValue(mapToJson(updateRequest), Permission.class);
		Mockito.when(permissionDAO.save(Mockito.any(Permission.class))).thenReturn(permission1);
		PermissionResponseDto updatedPermission = permissionService.updatePermission(updateRequest);

		assertNotNull(updatedPermission.getPermissionId());
		assertEquals("Permission updated", updatedPermission.getPermissionName());

		assertNotNull(updatedPermission.getCreatedDate());

		assertEquals("updated successfully", updatedPermission.getMessage());
		assertNotNull(updatedPermission.getModifiedDate());

	}

	@Test
	public void test_updatePermissions_throws_exception() throws JsonMappingException, JsonProcessingException ,PermissionNotFoundException{

		PermissionDto permissionDto = PermissionDto.builder().permissionId("EU").permissionName("end user").build();

		Mockito.when(permissionDAO.findByPermissionId(permissionDto.getPermissionId())).thenReturn(null);

		PermissionNotFoundException thrown = assertThrows(PermissionNotFoundException.class, () -> permissionService.updatePermission(permissionDto));
		assertEquals("Permission not found", thrown.getMessage());
	}

	@Test
	public void test_getAllPermissions() throws JsonMappingException, JsonProcessingException, PermissionNotFoundException {
		Permission permission1 = Permission.builder().permissionId("EU").permissionName("end user")
				.createdDate(new Date()).build();
		Permission permission2 = Permission.builder().permissionId("TA").permissionName("tenant admin")
				.createdDate(new Date()).build();

		List<Permission> permissionList = Arrays.asList(permission1, permission2);

		/*
		 * Mockito.when(permissionDAO.getAllPermissions()).thenReturn(permissionList);
		 * List<PermissionResponseDto> result = permissionService.getAllPermissions();
		 * 
		 * assertEquals(result.size(), 2); assertEquals("EU",
		 * result.get(0).getPermissionId()); assertEquals("end user",
		 * result.get(0).getPermissionName()); assertEquals("enable",
		 * result.get(0).getStatus()); assertNotNull(result.get(0).getCreatedDate());
		 * 
		 * assertEquals("TA", result.get(0).getUserPermission());
		 * 
		 * assertEquals("TA", result.get(1).getPermissionId());
		 * 
		 * assertEquals("tenant admin", result.get(1).getPermissionName());
		 * assertEquals("enable", result.get(1).getStatus());
		 * assertNotNull(result.get(1).getCreatedDate()); assertEquals("TA",
		 * result.get(1).getUserPermission());
		 */

	}
	
	@Test
	public void test_deletePermissions_success() throws JsonMappingException, JsonProcessingException, PermissionNotFoundException {
		
	
		    
		    Permission permission = Permission.builder().id("qwerty").permissionId("EU").permissionName("end user").permissionDescription("permissionDescription").build();
		    
		    Mockito.when(permissionDAO.findByPermissionId("EU")).thenReturn(permission);
	    
		    Permission updatedPermission = Permission.builder().id("qwerty").permissionId("EU").permissionName("end user").permissionDescription("permissionDescription").build();
		    
		    Mockito.when(permissionDAO.save(permission)).thenReturn(updatedPermission);
		    
		     permissionService.deletePermission("EU");
		
		
		
		
	

				
				/*
				 * assertNotNull(updatedPermission.getId());
				 * assertEquals("end user",updatedPermission.getPermissionName()); assertEquals("inactive",
				 * updatedPermission.getStatus());
				 */
				
				 
		
		
	}

}