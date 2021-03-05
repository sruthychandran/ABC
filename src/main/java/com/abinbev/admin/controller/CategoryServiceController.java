package com.abinbev.admin.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.exception.BadRequestAlertException;
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/categoryController/v1")
public class CategoryServiceController {


	private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
	@Autowired
	CategoryServiceService categoryService;

	/**
	 * In this method we can create a category service
	 * 
	 * @param categoryServiceDto
	 * @return CategoryServiceResponseDto
	 * @throws CategoryServiceCreationFailureException
	 */
	@PostMapping("/categoryService")
	public ResponseEntity<BasicResponse<CategoryServiceResponseDto>> createCategoryService(
			@RequestBody CategoryServiceDto categoryServiceDto) throws CategoryServiceCreationFailureException {
		log.debug("Request to create category service " + categoryServiceDto);
		BasicResponse<CategoryServiceResponseDto> result = categoryService.saveCategoryService(categoryServiceDto);
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

	@PutMapping("/categoryService")
	public ResponseEntity<BasicResponse<CategoryServiceResponseDto>> updateCategoryService(
			@RequestBody CategoryServiceDto categoryServiceDto)
			throws CategoryServiceNotFoundException, BadRequestAlertException, CategoryServiceUpdationFailureException {

		log.debug("Request to update category service " + categoryServiceDto);

		if (categoryServiceDto.getId() == null)
			throw new BadRequestAlertException("Invalid Id");
		BasicResponse<CategoryServiceResponseDto> result = categoryService.updateCategoryService(categoryServiceDto);

		return ResponseEntity.ok().body(result);

	}

	/**
	 * In this method we can get all category services
	 * 
	 * @return List<CategoryServiceResponseDto>
	 */

	@GetMapping("/categoryService")
	public ResponseEntity<BasicResponse<Page<CategoryServiceResponseDto>>> getAllCategoryServices(

			@RequestParam(required = false, defaultValue = "0") int page,

			@RequestParam(required = false, defaultValue = "10") int size,

			@RequestParam(required = false, defaultValue = "desc") String sort,

			@RequestParam(required = false, defaultValue = "id") String sortBy) {
		log.debug("Request to get all category services");
		Pageable pageable = PageRequest.of(page, size,
				Sort.by(sort.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy));

		BasicResponse<Page<CategoryServiceResponseDto>> categoryServiceResponse = categoryService
				.getAllCategoryServices(pageable);

		return ResponseEntity.ok().body(categoryServiceResponse);
	}

	/**
	 * In this method we can delete a category service
	 * 
	 * @param categoryId
	 * @throws CategoryServiceNotFoundException
	 */

	@DeleteMapping("/categoryService/{id}")
	public void deleteCategoryService(@PathVariable String id) throws CategoryServiceNotFoundException {

		log.debug("Request to delete a category service " + id);
		categoryService.deleteCategoryService(id);

	}

	/**
	 * In this method we can get a category service by id
	 * 
	 * @param id
	 * @return
	 * @throws BadRequestAlertException
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws CategoryServiceNotFoundException
	 */

	@GetMapping("/categoryService/{id}")
	public ResponseEntity<BasicResponse<CategoryServiceResponseDto>> getCategoryServiceById(@PathVariable String id)
			throws BadRequestAlertException, JsonMappingException, JsonProcessingException,
			CategoryServiceNotFoundException {
		log.debug("Request to get a category service " + id);

		if (id == null)
			throw new BadRequestAlertException("Invalid categoryId");
		BasicResponse<CategoryServiceResponseDto> categoryServiceResponse = categoryService.findById(id);
		return ResponseEntity.ok().body(categoryServiceResponse);
	}

}
