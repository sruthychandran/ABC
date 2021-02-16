package com.abinbev.admin.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.exception.DuplicateEmailException;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.CategoryDto;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
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
public class CategoryServiceServiceTests {

	@Autowired
	private CategoryServiceService categoryServiceService;

	@Mock
	private CategoryServiceDAO categoryServiceDAO;

	@Autowired
	private ObjectMapper mapper;

	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void afterEach() {
		// roleDAO.deleteAll();
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void test_createCategoryServices_success() throws JsonMappingException, JsonProcessingException {
		CategoryServiceDto  categoryServiceDto = CategoryServiceDto.builder().categoryId("CS").categoryName("coreService").moduleId("NI")
				.categoryName("Notification Service").build();
		
		CategoryService categoryService = mapper.readValue(mapToJson(categoryServiceDto), CategoryService.class);
		
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryService);
		
		CategoryServiceResponseDto result=categoryServiceService.saveCategoryService(categoryServiceDto);
		 
		
		assertEquals("CS", result.getCategoryId());
		assertEquals("coreService",result.getCategoryName());
		assertEquals("NI",result.getModuleId());
		assertEquals("Notification Service",result.getModuleName());
		assertNotNull(result.getCreatedDate());
		assertEquals("created successfully",result.getMessage());
		

	}
	
	
	/*
	 * @Test public void test_updateCategoryService_success() throws
	 * JsonMappingException, JsonProcessingException, NotFoundException {
	 * 
	 * }
	 */
	 
	

}