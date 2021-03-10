package com.abinbev.admin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryServiceControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryServiceService categoryServiceService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void createCategoryservices_success() throws Exception {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").status("active")
				.subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryServiceResponseDto categoryServiceResponseObj = CategoryServiceResponseDto.builder().id("sdfghjkl")
				.categoryId("CS").categoryName("coreService").moduleId("NI").moduleName("Notification Service")
				.userRole("TA").status("active").subModuleId("TA-Add").subModuleName("Tenant Addition")
				.createdDate(new Date(2021, 2, 1)).build();

		BasicResponse<CategoryServiceResponseDto> responseObj = new BasicResponse<CategoryServiceResponseDto>();
		responseObj.setData(categoryServiceResponseObj);

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(categoryServiceDto);
		String URI = "/categoryController/v1/categoryService";

		Mockito.when(categoryServiceService.saveCategoryService(Mockito.any(CategoryServiceDto.class)))
				.thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<CategoryServiceResponseDto>> typeReference = new TypeReference<BasicResponse<CategoryServiceResponseDto>>() {
		};
		BasicResponse<CategoryServiceResponseDto> result = mapper
				.readValue(mvcResult.getResponse().getContentAsString(), typeReference);

		assertEquals("CS", result.getData().getCategoryId());
		assertEquals("coreService", result.getData().getCategoryName());
		assertEquals("NI", result.getData().getModuleId());
		assertEquals("Notification Service", result.getData().getModuleName());
		assertEquals("active", result.getData().getStatus());
		assertEquals("TA", result.getData().getUserRole());
		assertEquals("TA-Add", result.getData().getSubModuleId());
		assertEquals("Tenant Addition", result.getData().getSubModuleName());
		assertNotNull(result.getData().getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updateCategoryService_throws_BadRequestAlertException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").status("active")
				.subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		mockMvc.perform(put("/categoryController/v1/categoryService").contentType("application/json")
				.content(mapper.writeValueAsString(categoryServiceDto))).andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestAlertException))
				.andExpect(result -> assertEquals("Invalid Id ", result.getResolvedException().getMessage()));
	}

	@Test
	public void updateCategoryservices_success() throws Exception {

		CategoryServiceDto categoryServiceDto = CategoryServiceDto.builder().id("sdfghjkl").categoryId("CS")
				.categoryName("coreServicev1").moduleId("NI").moduleName("Notification Service").status("active")
				.subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryServiceResponseDto categoryServiceResponseObj = CategoryServiceResponseDto.builder().id("sdfghjkl")
				.categoryId("CS").categoryName("coreServicev1").moduleId("NI").moduleName("Notification Service")
				.userRole("TA").status("active").subModuleId("TA-Add").subModuleName("Tenant Addition")
				.createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String inputInJson = this.mapToJson(categoryServiceDto);
		String URI = "/categoryController/v1/categoryService";
		BasicResponse<CategoryServiceResponseDto> responseObj = new BasicResponse<CategoryServiceResponseDto>();
		responseObj.setData(categoryServiceResponseObj);
		Mockito.when(categoryServiceService.updateCategoryService(Mockito.any(CategoryServiceDto.class)))
				.thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<CategoryServiceResponseDto>> typeReference = new TypeReference<BasicResponse<CategoryServiceResponseDto>>() {
		};
		BasicResponse<CategoryServiceResponseDto> result = mapper
				.readValue(mvcResult.getResponse().getContentAsString(), typeReference);
		/*
		 * CategoryServiceResponseDto result =
		 * mapper.readValue(mvcResult.getResponse().getContentAsString(),
		 * CategoryServiceResponseDto.class);
		 */

		assertEquals("CS", result.getData().getCategoryId());
		assertEquals("coreServicev1", result.getData().getCategoryName());
		assertEquals("NI", result.getData().getModuleId());
		assertEquals("Notification Service", result.getData().getModuleName());
		assertEquals("active", result.getData().getStatus());
		assertEquals("TA", result.getData().getUserRole());
		assertEquals("TA-Add", result.getData().getSubModuleId());
		assertEquals("Tenant Addition", result.getData().getSubModuleName());
		assertNotNull(result.getData().getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getCategoryservicesById_success() throws Exception {

		CategoryServiceResponseDto categoryServiceResponseObj = CategoryServiceResponseDto.builder().id("sdfghjkl")
				.categoryId("CS").categoryName("coreServicev1").moduleId("NI").moduleName("Notification Service")
				.userRole("TA").status("active").subModuleId("TA-Add").subModuleName("Tenant Addition")
				.createdDate(new Date(2021, 2, 1)).build();

		ObjectMapper mapper = new ObjectMapper();

		String URI = "/categoryController/v1/categoryService/sdfghjkl";

		BasicResponse<CategoryServiceResponseDto> responseObj = new BasicResponse<CategoryServiceResponseDto>();
		responseObj.setData(categoryServiceResponseObj);

		Mockito.when(categoryServiceService.findById("sdfghjkl")).thenReturn(responseObj);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		TypeReference<BasicResponse<CategoryServiceResponseDto>> typeReference = new TypeReference<BasicResponse<CategoryServiceResponseDto>>() {
		};
		BasicResponse<CategoryServiceResponseDto> result = mapper
				.readValue(mvcResult.getResponse().getContentAsString(), typeReference);

		assertEquals("CS", result.getData().getCategoryId());
		assertEquals("coreServicev1", result.getData().getCategoryName());
		assertEquals("NI", result.getData().getModuleId());
		assertEquals("Notification Service", result.getData().getModuleName());
		assertEquals("active", result.getData().getStatus());
		assertEquals("TA", result.getData().getUserRole());
		assertEquals("TA-Add", result.getData().getSubModuleId());
		assertEquals("Tenant Addition", result.getData().getSubModuleName());
		assertNotNull(result.getData().getCreatedDate());

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getCategoryServiceById_throws_NotFoundException() throws Exception {
		ErrorCodeMessage errorCodeMessage = new ErrorCodeMessage();
		errorCodeMessage.setErrorCode("20003");
		errorCodeMessage.setErrorMessage("Category Service not exists");
		Mockito.when(categoryServiceService.findById("sdfghjkl"))
				.thenThrow(new CategoryServiceNotFoundException(errorCodeMessage));

		mockMvc.perform(MockMvcRequestBuilders
				.get("/categoryController/v1/categoryService/{id}", "sdfghjkl").contentType("application/json"))
				.andExpect(status().isNotFound())
				.andExpect(
						result -> assertTrue(result.getResolvedException() instanceof CategoryServiceNotFoundException))
				.andExpect(result -> assertEquals("Category Service not exists",
						result.getResolvedException().getMessage()));

	}

	/**
	 * Test delete with valid
	 * 
	 * @throws Exception
	 * @Expect HttpStatus is 200 for valid id
	 * 
	 */
	@DisplayName("TestDelete")

	@Test
	public void testDeleteWithValidId() throws Exception {

		CategoryService categoryService = CategoryService.builder().id("qwerty").categoryId("CS")
				.categoryName("coreService").moduleId("NI").moduleName("Notification Service").userRole("TA")
				.status("active").subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		CategoryServiceResponseDto categoryServiceDto = CategoryServiceResponseDto.builder().id("qwerty")
				.categoryId("CS").categoryName("coreService").moduleId("NI").moduleName("Notification Service")
				.userRole("TA").status("active").subModuleId("TA-Add").subModuleName("Tenant Addition").build();

		BasicResponse<CategoryServiceResponseDto> responseObj = new BasicResponse<CategoryServiceResponseDto>();
		responseObj.setData(categoryServiceDto);

		Mockito.when(categoryServiceService.findById(categoryServiceDto.getId())).thenReturn(responseObj);

		String URI = "/categoryController/v1/categoryService/" + categoryServiceDto.getId();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
