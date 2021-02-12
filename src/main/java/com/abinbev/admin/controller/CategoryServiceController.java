package com.abinbev.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.service.CategoryServiceService;

@RestController
@RequestMapping("/categoryServices")
public class CategoryServiceController {

	@Autowired
	CategoryServiceService categoryService;

	@PostMapping("/createCategoryService")
	public void createCategoryService(@RequestBody CategoryServiceDto categoryServiceDto) {
		categoryService.saveCategoryService(categoryServiceDto);
	}

	@PutMapping("/updateCategoryService")
	public void updateCategoryService(@RequestBody CategoryServiceDto categoryServiceDto) {
		categoryService.updateCategoryService(categoryServiceDto);
	}

	@GetMapping("/getAllCategoryServices")
	public List<CategoryServiceResponseDto> getAllCategoryServices() {
		
		return categoryService.getAllCategoryServices();
	}
	
	@GetMapping("/deleteCategoryService/{categoryId}")
	public void deleteCategoryService(@PathVariable String categoryId) {
		
		categoryService.deleteCategoryService(categoryId);
	
	}

}
