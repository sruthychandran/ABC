package com.abinbev.admin.service;

import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.requestDto.UserDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;

public interface CategoryServiceService {
	
	public CategoryServiceResponseDto saveCategoryService(CategoryServiceDto CategoryServiceDto);

	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto);
}
