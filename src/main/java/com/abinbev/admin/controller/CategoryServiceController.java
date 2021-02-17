package com.abinbev.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/categoryServices")
public class CategoryServiceController {

	@Autowired
	CategoryServiceService categoryService;

	/**
	 * In this method we can create a category service
	 * 
	 * @param categoryServiceDto
	 * @return CategoryServiceResponseDto
	 * @throws CategoryServiceCreationFailureException
	 */
	@PostMapping("/createCategoryService")
	public ResponseEntity<CategoryServiceResponseDto> createCategoryService(
			@RequestBody CategoryServiceDto categoryServiceDto) throws CategoryServiceCreationFailureException {
		CategoryServiceResponseDto result = categoryService.saveCategoryService(categoryServiceDto);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * In this method we can update a category service
	 * 
	 * @param categoryServiceDto
	 * @return
	 * @throws NotFoundException
	 * @throws BadRequestAlertException
	 * @throws CategoryServiceUpdationFailureException
	 */
	@PutMapping("/updateCategoryService")
	public ResponseEntity<CategoryServiceResponseDto> updateCategoryService(
			@RequestBody CategoryServiceDto categoryServiceDto)
			throws CategoryServiceNotFoundException, BadRequestAlertException, CategoryServiceUpdationFailureException {
		if (categoryServiceDto.getCategoryId() == null)
			throw new BadRequestAlertException("Invalid CategoryId");
		CategoryServiceResponseDto categoryServiceResponse = categoryService.updateCategoryService(categoryServiceDto);
		return ResponseEntity.ok().body(categoryServiceResponse);

	}

	/**
	 * In this method we can get all category services
	 * 
	 * @return List<CategoryServiceResponseDto>
	 */
	@GetMapping("/getAllCategoryServices")
	public ResponseEntity<List<CategoryServiceResponseDto>> getAllCategoryServices() {

		List<CategoryServiceResponseDto> categoryServiceResponse = categoryService.getAllCategoryServices();
		return ResponseEntity.ok().body(categoryServiceResponse);
	}

	/**
	 * In this method we can delete a category service
	 * 
	 * @param categoryId
	 * @throws CategoryServiceNotFoundException
	 */
	@GetMapping("/deleteCategoryService/{categoryId}")
	public void deleteCategoryService(@PathVariable String categoryId) throws CategoryServiceNotFoundException {

		categoryService.deleteCategoryService(categoryId);

	}

	/**
	 * In this method we can get a category service by id
	 * 
	 * @param categoryId
	 * @return
	 * @throws BadRequestAlertException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws CategoryServiceNotFoundException
	 */
	@GetMapping("/getCategoryService/{categoryId}")
	public ResponseEntity<CategoryServiceResponseDto> getCategoryServiceById(@PathVariable String categoryId)
			throws BadRequestAlertException, JsonMappingException, JsonProcessingException,
			CategoryServiceNotFoundException {
		if (categoryId == null)
			throw new BadRequestAlertException("Invalid categoryId");
		CategoryServiceResponseDto categoryServiceResponse = categoryService.findCategoryService(categoryId);
		return ResponseEntity.ok().body(categoryServiceResponse);
	}
}
