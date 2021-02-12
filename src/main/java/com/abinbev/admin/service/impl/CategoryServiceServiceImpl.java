package com.abinbev.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.abinbev.admin.utility.MapperUtil;

@Service
public class CategoryServiceServiceImpl implements CategoryServiceService {

	@Autowired
	CategoryServiceDAO categoryDAO;

	MapperUtil<CategoryServiceDto, CategoryService> toCategoryService = new MapperUtil<>();
	MapperUtil<CategoryService, CategoryServiceResponseDto> toCategoryServiceResponse = new MapperUtil<>();

	@Override
	public CategoryServiceResponseDto saveCategoryService(CategoryServiceDto categoryServiceDto) {

		CategoryService categoryService = toCategoryService.transfer(categoryServiceDto, CategoryService.class);

		categoryService = categoryDAO.save(categoryService);

		return toCategoryServiceResponse.transfer(categoryService, CategoryServiceResponseDto.class);

	}

	@Override
	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto) {
		return null;

	}

	@Override
	public List<CategoryServiceResponseDto> getAllCategoryServices() {
		categoryDAO.getAllCategoryServices();
		return null;
	}

	@Override
	public void deleteCategoryService(String categoryId) {
		categoryDAO.deleteCategoryService(categoryId);

	}

}
