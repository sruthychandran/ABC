package com.abinbev.admin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").status("active")
				.subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryService categoryService = mapper.readValue(mapToJson(categoryServiceDto), CategoryService.class);
		categoryService.setStatus("active");
		categoryService.setCreatedDate(new Date());

		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryService);

		BasicResponse<CategoryServiceResponseDto> result = categoryServiceService
				.saveCategoryService(categoryServiceDto);

		assertEquals("CS", result.getData().getCategoryId());
		assertEquals("coreService", result.getData().getCategoryName());
		assertEquals("NI", result.getData().getModuleId());
		assertEquals("Notification Service", result.getData().getModuleName());
		assertEquals("active", result.getData().getStatus());
		assertEquals("TA-Add", result.getData().getSubModuleId());
		assertEquals("Tenant Addition", result.getData().getSubModuleName());
		assertNotNull(result.getData().getCreatedDate());
		assertEquals("Category Service created successfully", result.getMessage());
		assertNull(result.getError().getErrorCode());
		assertNull(result.getError().getErrorMessage());

	}

	@Test
	public void test_createCategoryServices__throws_exception()
			throws JsonMappingException, JsonProcessingException, CategoryServiceCreationFailureException {
		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").status("active")
				.subModuleId("TA-Add").subModuleName("Tenant Addition").build();
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(null);
		CategoryServiceCreationFailureException thrown = assertThrows(CategoryServiceCreationFailureException.class,
				() -> categoryServiceService.saveCategoryService(categoryServiceDto));
		assertEquals("Save Category Service operation is failed", thrown.getMessage());

	}

	@Test
	public void test_updateCategoryService_success() throws JsonMappingException, JsonProcessingException,
			CategoryServiceNotFoundException, CategoryServiceUpdationFailureException {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service1").status("active")
				.subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryService categoryService = CategoryService.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").createdDate(new Date(2021, 3, 1)).build();

		Mockito.when(categoryServiceDAO.findById(categoryServiceDto.getId())).thenReturn(categoryService);
		Date createdDate = categoryService.getCreatedDate();
		CategoryService categoryServiceObj = mapper.readValue(mapToJson(categoryServiceDto), CategoryService.class);
		categoryServiceObj.setCreatedDate(createdDate);
		categoryServiceObj.setModifiedDate(new Date());
		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryServiceObj);
		BasicResponse<CategoryServiceResponseDto> updatedCategoryService = categoryServiceService
				.updateCategoryService(categoryServiceDto);

		assertNotNull(updatedCategoryService.getData().getId());
		assertEquals("coreService", updatedCategoryService.getData().getCategoryName());
		assertEquals("NI", updatedCategoryService.getData().getModuleId());
		assertEquals("Notification Service1", updatedCategoryService.getData().getModuleName());

		assertEquals("active", updatedCategoryService.getData().getStatus());
		assertEquals(createdDate, updatedCategoryService.getData().getCreatedDate());

		assertEquals("Category Service updated successfully", updatedCategoryService.getMessage());
		assertNotNull(updatedCategoryService.getData().getModifiedDate());
		assertNull(updatedCategoryService.getError().getErrorCode());
		assertNull(updatedCategoryService.getError().getErrorMessage());

	}

	@Test
	public void test_updateCategoryService_throws_CategoryServiceNotFoundException()
			throws JsonMappingException, JsonProcessingException {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").status("active").build();

		Mockito.when(categoryServiceDAO.findById(categoryServiceDto.getId())).thenReturn(null);

		CategoryServiceNotFoundException thrown = assertThrows(CategoryServiceNotFoundException.class,
				() -> categoryServiceService.updateCategoryService(categoryServiceDto));
		assertEquals("Category Service not exists", thrown.getMessage());
	}

	@Test
	public void test_updateCategoryService_throws_CategoryServiceUpdationFailureException()
			throws JsonMappingException, JsonProcessingException, CategoryServiceUpdationFailureException {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").status("active").build();
		Mockito.when(categoryServiceDAO.findById(categoryServiceDto.getId())).thenReturn(new CategoryService());

		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(null);

		CategoryServiceUpdationFailureException thrown = assertThrows(CategoryServiceUpdationFailureException.class,
				() -> categoryServiceService.updateCategoryService(categoryServiceDto));
		assertEquals("Update Category Service operation is failed", thrown.getMessage());
	}

	@Test
	public void test_deleteCategoryService_success()
			throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {
		CategoryService categoryService = CategoryService.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").status("active")
				.createdDate(new Date(2021, 3, 1)).modifiedDate(new Date(2021, 3, 1)).build();

		Date createdDate = categoryService.getCreatedDate();
		
		Mockito.when(categoryServiceDAO.findById("sdfghjkl")).thenReturn(categoryService);

		categoryService.setStatus("inactive");

		Mockito.when(categoryServiceDAO.save(Mockito.any(CategoryService.class))).thenReturn(categoryService);
		BasicResponse<CategoryServiceResponseDto> response = categoryServiceService.deleteCategoryService("sdfghjkl");

		assertNotNull(response.getData().getId());
		assertEquals("sdfghjkl", response.getData().getId());
		assertEquals("CS", response.getData().getCategoryId());
		assertEquals("coreService", response.getData().getCategoryName());
		assertEquals("NI", response.getData().getModuleId());
		assertEquals("Notification Service", response.getData().getModuleName());
		assertEquals("inactive", response.getData().getStatus());
		assertEquals(createdDate, response.getData().getCreatedDate());
		assertNotNull(response.getData().getModifiedDate());
		assertEquals("Category Service delete successfully", response.getMessage());
		assertEquals("10013", response.getCode());
		assertNull(response.getError().getErrorCode());
		assertNull(response.getError().getErrorMessage());

	}

	@Test
	public void test_findById_success()
			throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {
		CategoryService categoryService = CategoryService.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").createdDate(new Date(2021, 3, 1)).build();

		Mockito.when(categoryServiceDAO.findById("sdfghjkl")).thenReturn(categoryService);

		BasicResponse<CategoryServiceResponseDto> categoryServiceResponseObj = categoryServiceService
				.findById("sdfghjkl");

		assertEquals("sdfghjkl", categoryServiceResponseObj.getData().getId());
		assertEquals("coreService", categoryServiceResponseObj.getData().getCategoryName());
		assertEquals("NI", categoryServiceResponseObj.getData().getModuleId());
		assertEquals("Notification Service", categoryServiceResponseObj.getData().getModuleName());
		assertEquals("TA", categoryServiceResponseObj.getData().getUserRole());
		assertEquals("active", categoryServiceResponseObj.getData().getStatus());
		assertNotNull(categoryServiceResponseObj.getData().getCreatedDate());

	}

	@Test
	public void test_findById_throws_exception()
			throws JsonMappingException, JsonProcessingException, CategoryServiceNotFoundException {

		Mockito.when(categoryServiceDAO.findById("sdfghjkl")).thenReturn(null);
		CategoryServiceNotFoundException thrown = assertThrows(CategoryServiceNotFoundException.class,
				() -> categoryServiceService.findById("sdfghjkl"));
		assertEquals("Category Service not exists", thrown.getMessage());

	}

}