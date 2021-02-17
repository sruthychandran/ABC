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
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.exception.EmailExistException;

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

	@MockBean
	private CategoryServiceDAO categoryServiceDAO;

	@Autowired
	private ObjectMapper mapper;



	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void test_createCategoryServices_success() throws JsonMappingException, JsonProcessingException, CategoryServiceCreationFailureException {
		CategoryServiceDto  categoryServiceDto = CategoryServiceDto.builder().categoryId("CS").categoryName("coreService").moduleId("NI")
				.moduleName("Notification Service").userRole("TA").build();
		
		CategoryService categoryService = mapper.readValue(mapToJson(categoryServiceDto), CategoryService.class);
		categoryService.setStatus("enable");
		categoryService.setCreatedDate(new Date());
		
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryService);
		
		CategoryServiceResponseDto result=categoryServiceService.saveCategoryService(categoryServiceDto);
		 
		
		assertEquals("CS", result.getCategoryId());
		assertEquals("coreService",result.getCategoryName());
		assertEquals("NI",result.getModuleId());
		assertEquals("Notification Service",result.getModuleName());
		assertEquals("enable",result.getStatus());
		assertEquals("TA",result.getUserRole());
		assertNotNull(result.getCreatedDate());
		assertEquals("created successfully",result.getMessage());
		

	}
	
	
	
	  @Test public void test_updateCategoryService_success() throws
	  JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException, CategoryServiceUpdationFailureException {
			CategoryServiceDto  categoryServiceDto = CategoryServiceDto.builder().categoryId("CS").categoryName("coreService").moduleId("NI")
					.moduleName("Notification Service").userRole("TA").status("enable").build();
			
			CategoryService categoryService =mapper.readValue(mapToJson(categoryServiceDto), CategoryService.class); 
			categoryService.setCreatedDate(new Date());
			CategoryServiceResponseDto returnedCategoryService=   CategoryServiceResponseDto.builder().categoryId("CS").categoryName("coreService").moduleId("NI")
					.moduleName("Notification Service").userRole("TA").status("enable").build();
		  Mockito.when(categoryServiceDAO.findByCategoryId("CS")).thenReturn(categoryService);
		  returnedCategoryService.setCategoryName("coreService1");
		  returnedCategoryService.setModuleId("NI");
		  returnedCategoryService.setModuleName("Notification service1");
		  
		  returnedCategoryService.setCreatedDate(categoryService.getCreatedDate());
		  returnedCategoryService.setModifiedDate(new Date());
	      assertNotNull(returnedCategoryService.getCreatedDate());
	      CategoryServiceDto updateRequest= mapper.readValue(mapToJson(returnedCategoryService),CategoryServiceDto.class); 
	      CategoryService categoryService2 =mapper.readValue(mapToJson(updateRequest), CategoryService.class); 
	      Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryService2);
	       CategoryServiceResponseDto  updatedCategoryService=categoryServiceService.updateCategoryService(updateRequest);
	  
	      assertNotNull(updatedCategoryService.getCategoryId());
	      assertEquals("coreService1",updatedCategoryService.getCategoryName());
	      assertEquals("NI",updatedCategoryService.getModuleId());
	      assertEquals("Notification service1",updatedCategoryService.getModuleName());
	      assertEquals("TA",updatedCategoryService.getUserRole());
	      assertEquals("enable",updatedCategoryService.getStatus());
	      assertNotNull(updatedCategoryService.getCreatedDate());
			//assertEquals("abi@gmail.com",returnedUser.getCreatedBy());
		 assertEquals("updated successfully", updatedCategoryService.getMessage());
		 assertNotNull(updatedCategoryService.getModifiedDate());
			//assertEquals("abi@gmail.com",returnedUser.getModifiedBy());
		  
	  
	  }
	 
	  @Test public void test_updateCategoryService_throws_exception() throws JsonMappingException,
	  JsonProcessingException{
		
		  CategoryServiceDto  categoryServiceDto = CategoryServiceDto.builder().categoryId("CS").categoryName("coreService").moduleId("NI")
					.moduleName("Notification Service").userRole("TA").status("enable").build();
		  
		Mockito.when(categoryServiceDAO.findByCategoryId( categoryServiceDto.getCategoryId())).thenReturn(null);

		
		CategoryServiceNotFoundException thrown =assertThrows(CategoryServiceNotFoundException.class, () ->
		  categoryServiceService.updateCategoryService(categoryServiceDto));
		assertEquals("CategoryService not found",thrown.getMessage());
	}
	  
	  @Test
		public void test_getAllCategoryServices_success() throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {
	
		  CategoryService  categoryService1 = CategoryService.builder().categoryId("CS").categoryName("coreService").moduleId("NI")
					.moduleName("Notification service").userRole("TA").status("enable").createdDate(new Date()).build();
		  CategoryService  categoryService2 = CategoryService.builder().categoryId("PS").categoryName("productService").moduleId("xyz")
					.moduleName("service1").userRole("TA").status("enable").createdDate(new Date()).build();
		  
			List<CategoryService> CategoryServicelist = Arrays.asList(categoryService1, categoryService2);

			Mockito.when(categoryServiceDAO.getAllCategoryServices()).thenReturn(CategoryServicelist);
			List<CategoryServiceResponseDto> result = categoryServiceService.getAllCategoryServices();
			  
			  
		      assertEquals("coreService",result.get(0).getCategoryName());
		      assertEquals("NI",result.get(0).getModuleId());
		      assertEquals("Notification service",result.get(0).getModuleName());
		      assertEquals("TA",result.get(0).getUserRole());
		      assertEquals("enable",result.get(0).getStatus());
		      assertNotNull(result.get(0).getCreatedDate());
		      
		      assertNotNull(result.get(1).getCategoryId());
			  assertEquals("PS",result.get(1).getCategoryId());
		      assertEquals("productService",result.get(1).getCategoryName());
		      assertEquals("xyz",result.get(1).getModuleId());
		      assertEquals("service1",result.get(1).getModuleName());
		      assertEquals("TA",result.get(1).getUserRole());
		      assertEquals("enable",result.get(1).getStatus());
		      assertNotNull(result.get(1).getCreatedDate());
		  
	  }

}