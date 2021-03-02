package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.RoleDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.abinbev.admin.service.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = { CategoryServiceController.class })

public class CategoryServiceControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryServiceService categoryService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void createCategoryservices_success() throws Exception {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryServiceResponseDto categoryServiceResponseObj = CategoryServiceResponseDto.builder().id("sdfghjkl")
				.categoryId("CS").categoryName("coreService").moduleId("NI").moduleName("Notification Service")
				.userRole("TA").status("active").subModuleId("TA-Add").subModuleName("Tenant Addition")
				.createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(categoryServiceDto);
		String URI = "/categoryServices/v1/createCategoryService";

		Mockito.when(categoryService.saveCategoryService(Mockito.any(CategoryServiceDto.class)))
				.thenReturn(categoryServiceResponseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		CategoryServiceResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				CategoryServiceResponseDto.class);

		assertEquals("CS", result.getCategoryId());
		assertEquals("coreService", result.getCategoryName());
		assertEquals("NI", result.getModuleId());
		assertEquals("Notification Service", result.getModuleName());
		assertEquals("active", result.getStatus());
		assertEquals("TA", result.getUserRole());
		assertEquals("TA-Add", result.getSubModuleId());
		assertEquals("Tenant Addition", result.getSubModuleName());
		assertNotNull(result.getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updateCategoryservices_success() throws Exception {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreServicev1").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryServiceResponseDto categoryServiceResponseObj = CategoryServiceResponseDto.builder().id("sdfghjkl")
				.categoryId("CS").categoryName("coreServicev1").moduleId("NI").moduleName("Notification Service")
				.userRole("TA").status("active").subModuleId("TA-Add").subModuleName("Tenant Addition")
				.createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(categoryServiceDto);
		String URI = "/categoryServices/v1/updateCategoryService";

		Mockito.when(categoryService.updateCategoryService(Mockito.any(CategoryServiceDto.class)))
				.thenReturn(categoryServiceResponseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		CategoryServiceResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				CategoryServiceResponseDto.class);

		assertEquals("CS", result.getCategoryId());
		assertEquals("coreServicev1", result.getCategoryName());
		assertEquals("NI", result.getModuleId());
		assertEquals("Notification Service", result.getModuleName());
		assertEquals("active", result.getStatus());
		assertEquals("TA", result.getUserRole());
		assertEquals("TA-Add", result.getSubModuleId());
		assertEquals("Tenant Addition", result.getSubModuleName());
		assertNotNull(result.getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getCategoryservicesById_success() throws Exception {

		CategoryServiceResponseDto categoryServiceResponseObj = CategoryServiceResponseDto.builder().id("sdfghjkl")
				.categoryId("CS").categoryName("coreServicev1").moduleId("NI").moduleName("Notification Service")
				.userRole("TA").status("active").subModuleId("TA-Add").subModuleName("Tenant Addition")
				.createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String URI = "/categoryServices/v1/getCategoryService/sdfghjkl";

		Mockito.when(categoryService.findById("sdfghjkl")).thenReturn(categoryServiceResponseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		CategoryServiceResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				CategoryServiceResponseDto.class);

		assertEquals("CS", result.getCategoryId());
		assertEquals("coreServicev1", result.getCategoryName());
		assertEquals("NI", result.getModuleId());
		assertEquals("Notification Service", result.getModuleName());
		assertEquals("active", result.getStatus());
		assertEquals("TA", result.getUserRole());
		assertEquals("TA-Add", result.getSubModuleId());
		assertEquals("Tenant Addition", result.getSubModuleName());
		assertNotNull(result.getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void deleteCategoryservicesById_success() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		String URI = "/categoryServices/v1/deleteCategoryService/sdfghjkl";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		CategoryServiceResponseDto result = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				CategoryServiceResponseDto.class);

	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
