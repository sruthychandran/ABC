package com.abinbev.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times; 
import static org.mockito.Mockito.verify;
//@ExtendWith(MockitoExtension.class)
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
	public void test_createCategoryServices_success()
			throws JsonMappingException, JsonProcessingException, CategoryServiceCreationFailureException {
		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryService categoryService = mapper.readValue(mapToJson(categoryServiceDto), CategoryService.class);
		categoryService.setStatus("active");
		categoryService.setCreatedDate(new Date());

		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryService);

		CategoryServiceResponseDto result = categoryServiceService.saveCategoryService(categoryServiceDto);

		assertEquals("CS", result.getCategoryId());
		assertEquals("coreService", result.getCategoryName());
		assertEquals("NI", result.getModuleId());
		assertEquals("Notification Service", result.getModuleName());
		assertEquals("active", result.getStatus());
		assertEquals("TA", result.getUserRole());
		assertEquals("TA-Add", result.getSubModuleId());
		assertEquals("Tenant Addition", result.getSubModuleName());
		assertNotNull(result.getCreatedDate());
		assertEquals("created successfully", result.getMessage());

	}

	@Test
	public void test_createCategoryServices__throws_exception()
			throws JsonMappingException, JsonProcessingException, CategoryServiceCreationFailureException {
		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").subModuleId("TA-Add").subModuleName("Tenant Addition").build();
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(null);
		CategoryServiceCreationFailureException thrown = assertThrows(CategoryServiceCreationFailureException.class,
				() -> categoryServiceService.saveCategoryService(categoryServiceDto));
		assertEquals("Save Category Service operation is failed", thrown.getMessage());
		
	}

	@Test
	public void test_updateCategoryService_success() throws JsonMappingException, JsonProcessingException,
			CategoryServiceNotFoundException, CategoryServiceUpdationFailureException {
	
		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service1").userRole("TA")
				.status("active").subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryService categoryService = CategoryService.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").createdDate(new Date(2021, 3, 1)).build();

		Mockito.when(categoryServiceDAO.findById(categoryServiceDto.getId())).thenReturn(categoryService);
		Date createdDate = categoryService.getCreatedDate();
		CategoryService categoryServiceObj = mapper.readValue(mapToJson(categoryServiceDto), CategoryService.class);
		categoryServiceObj.setCreatedDate(createdDate);
		categoryServiceObj.setModifiedDate(new Date());
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryServiceObj);
		CategoryServiceResponseDto updatedCategoryService = categoryServiceService
				.updateCategoryService(categoryServiceDto);

		assertNotNull(updatedCategoryService.getId());
		assertEquals("coreService", updatedCategoryService.getCategoryName());
		assertEquals("NI", updatedCategoryService.getModuleId());
		assertEquals("Notification Service1", updatedCategoryService.getModuleName());
		assertEquals("TA", updatedCategoryService.getUserRole());
		assertEquals("active", updatedCategoryService.getStatus());
		assertEquals(createdDate, updatedCategoryService.getCreatedDate());

		assertEquals("updated successfully", updatedCategoryService.getMessage());
		assertNotNull(updatedCategoryService.getModifiedDate());

	}

	@Test
	public void test_updateCategoryService_throws_CategoryServiceNotFoundException() throws JsonMappingException, JsonProcessingException {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").build();

		Mockito.when(categoryServiceDAO.findById(categoryServiceDto.getId())).thenReturn(null);

		CategoryServiceNotFoundException thrown = assertThrows(CategoryServiceNotFoundException.class,
				() -> categoryServiceService.updateCategoryService(categoryServiceDto));
		assertEquals("Category Service not found", thrown.getMessage());
	}

	@Test
	public void test_updateCategoryService_throws_CategoryServiceUpdationFailureException() throws JsonMappingException, JsonProcessingException, CategoryServiceUpdationFailureException {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").build();
		Mockito.when(categoryServiceDAO.findById(categoryServiceDto.getId())).thenReturn(new CategoryService() );
		
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(null);

		CategoryServiceUpdationFailureException thrown = assertThrows(CategoryServiceUpdationFailureException.class,
				() -> categoryServiceService.updateCategoryService(categoryServiceDto));
		assertEquals("Update Category Service operation is failed", thrown.getMessage());
	}
	
	
	
	@Test
	public void test_deleteCategoryService_success() throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {
		CategoryService categoryService = CategoryService.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").createdDate(new Date(2021, 3, 1)).build();

		Mockito.when(categoryServiceDAO.findById("sdfghjkl")).thenReturn(categoryService);
		
		categoryService.setStatus("inactive");
		
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryService);
		categoryServiceService.deleteCategoryService("sdfghjkl");

		
	}
	
	
	@Test
	public void test_findById_success() throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {
		CategoryService categoryService = CategoryService.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").createdDate(new Date(2021, 3, 1)).build();

		Mockito.when(categoryServiceDAO.findById("sdfghjkl")).thenReturn(categoryService);

		CategoryServiceResponseDto categoryServiceResponseObj =categoryServiceService.findById("sdfghjkl");
		
		assertEquals("sdfghjkl",categoryServiceResponseObj.getId());
		assertEquals("coreService", categoryServiceResponseObj.getCategoryName());
		assertEquals("NI", categoryServiceResponseObj.getModuleId());
		assertEquals("Notification Service", categoryServiceResponseObj.getModuleName());
		assertEquals("TA", categoryServiceResponseObj.getUserRole());
		assertEquals("active", categoryServiceResponseObj.getStatus());
		assertNotNull(categoryServiceResponseObj.getCreatedDate());
		

		
	}
	@Test
	public void test_findById_throws_exception() throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {
	
		Mockito.when(categoryServiceDAO.findById("sdfghjkl")).thenReturn(null);
		CategoryServiceNotFoundException thrown = assertThrows(CategoryServiceNotFoundException.class,
				() -> categoryServiceService.findById("sdfghjkl"));
		assertEquals("Category Service not found", thrown.getMessage());
		
	}
	
	
	
}