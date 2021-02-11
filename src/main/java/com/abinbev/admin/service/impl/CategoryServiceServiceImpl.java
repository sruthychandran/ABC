package com.abinbev.admin.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abinbev.admin.dao.CategoryServiceDAO;

import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.UserResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.abinbev.admin.service.RoleService;
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
		
		categoryService =categoryDAO.save(categoryService);
		
		return toCategoryServiceResponse.transfer(categoryService, CategoryServiceResponseDto.class);
		
	}

	@Override
	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto) {
		return null;

		
	}




	



	

		
	
}
