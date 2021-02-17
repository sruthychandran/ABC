package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;

public interface CategoryServiceService {

	public CategoryServiceResponseDto saveCategoryService(CategoryServiceDto CategoryServiceDto)
			throws CategoryServiceCreationFailureException;

	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceNotFoundException, CategoryServiceUpdationFailureException;

	public List<CategoryServiceResponseDto> getAllCategoryServices();

	public void deleteCategoryService(String categoryId) throws CategoryServiceNotFoundException;

	public CategoryServiceResponseDto findCategoryService(String categoryId);
}
