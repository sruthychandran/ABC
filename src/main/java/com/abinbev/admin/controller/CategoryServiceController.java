package com.abinbev.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.UserDto;
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

	@PutMapping("/categories")
	public void updateCategoryService(@RequestBody CategoryServiceDto categoryServiceDto) {
		categoryService.updateCategoryService(categoryServiceDto);
	}

	@GetMapping("/categories")
	public String getCategoryService() {
		return null;
	}

}
