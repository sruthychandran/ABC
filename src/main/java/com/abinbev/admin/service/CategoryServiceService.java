package com.abinbev.admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.RoleResponseDto;

public interface CategoryServiceService {

	public BasicResponse<CategoryServiceResponseDto> saveCategoryService(CategoryServiceDto CategoryServiceDto)
			throws CategoryServiceCreationFailureException;

	public BasicResponse<CategoryServiceResponseDto> updateCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceNotFoundException, CategoryServiceUpdationFailureException;

	public BasicResponse<Page<CategoryServiceResponseDto>> getAllCategoryServices(Pageable pageable);

	public BasicResponse<CategoryServiceResponseDto> deleteCategoryService(String categoryId) throws CategoryServiceNotFoundException;

	public BasicResponse<CategoryServiceResponseDto> findById(String id) throws CategoryServiceNotFoundException;

	

	
}
