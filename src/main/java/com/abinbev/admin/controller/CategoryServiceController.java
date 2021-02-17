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
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/categoryServices")
public class CategoryServiceController {

	@Autowired
	CategoryServiceService categoryService;

	@PostMapping("/createCategoryService")
	public ResponseEntity<CategoryServiceResponseDto> createCategoryService(@RequestBody CategoryServiceDto categoryServiceDto) {
		CategoryServiceResponseDto result =categoryService.saveCategoryService(categoryServiceDto);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/updateCategoryService")
	public ResponseEntity<CategoryServiceResponseDto> updateCategoryService(@RequestBody CategoryServiceDto categoryServiceDto) throws NotFoundException, BadRequestAlertException {
		if(categoryServiceDto.getCategoryId() == null)
			throw new BadRequestAlertException("Invalid CategoryId");
		CategoryServiceResponseDto result =categoryService.updateCategoryService(categoryServiceDto);
		return ResponseEntity.ok().body(result);
		
	}

	@GetMapping("/getAllCategoryServices")
	public ResponseEntity<List<CategoryServiceResponseDto>> getAllCategoryServices() {
		
		List<CategoryServiceResponseDto> result = categoryService.getAllCategoryServices();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/deleteCategoryService/{categoryId}")
	public void deleteCategoryService(@PathVariable String categoryId) {
		
		categoryService.deleteCategoryService(categoryId);
	
	}
	@GetMapping("/getCategoryService/{categoryId}")
	public ResponseEntity<CategoryServiceResponseDto> getCategoryServiceById(@PathVariable String categoryId) throws BadRequestAlertException, JsonMappingException, JsonProcessingException {
		if(categoryId == null)
			throw new BadRequestAlertException("Invalid categoryId");
		CategoryServiceResponseDto result = categoryService.findByCategoryId(categoryId);
		return ResponseEntity.ok().body(result);
	}
}
