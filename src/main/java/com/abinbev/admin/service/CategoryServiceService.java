package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;

public interface CategoryServiceService {
	
	public CategoryServiceResponseDto saveCategoryService(CategoryServiceDto CategoryServiceDto);

	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto);

	public List<CategoryServiceResponseDto> getAllCategoryServices();

	public void deleteCategoryService(String categoryId);
}
