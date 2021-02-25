package com.abinbev.admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

	public Page<CategoryServiceResponseDto> getAllCategoryServices(Pageable pageable);

	public void deleteCategoryService(String categoryId) throws CategoryServiceNotFoundException;

	public CategoryServiceResponseDto findById(String id) throws CategoryServiceNotFoundException;

	//public HashMap<String, List<Object>> findModulesByCategoryId(String categoryId);
}
