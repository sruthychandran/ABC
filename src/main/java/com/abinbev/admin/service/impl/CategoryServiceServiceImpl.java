package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.exception.NotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.abinbev.admin.utility.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceServiceImpl implements CategoryServiceService {
	@Value("${message.create}")
	String creationMessage;
	@Value("${message.update}")
	String updationMessage;
	@Value("${message.delete}")
	String deletionMessage;
	@Autowired
	CategoryServiceDAO categoryDAO;

	MapperUtil<CategoryServiceDto, CategoryService> toCategoryService = new MapperUtil<>();
	MapperUtil<CategoryService, CategoryServiceResponseDto> toCategoryServiceResponse = new MapperUtil<>();

	@Override
	public CategoryServiceResponseDto saveCategoryService(CategoryServiceDto categoryServiceDto) {

		CategoryService categoryService = toCategoryService.transfer(categoryServiceDto, CategoryService.class);
		categoryService.setStatus("enabled");
		categoryService.setCreatedDate(new Date());
		//categoryService.setCreatedBy();
		categoryService = categoryDAO.save(categoryService);
		
		CategoryServiceResponseDto response= toCategoryServiceResponse.transfer(categoryService, CategoryServiceResponseDto.class);
		response.setMessage(creationMessage);
		return response;
	}

	@Override
	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto) throws NotFoundException {
		CategoryService existingCategoryService = categoryDAO.findByCategoryId(categoryServiceDto.getCategoryId());

		if (existingCategoryService == null) {
			throw new NotFoundException("CategoryService not found");
		}

		
		
		CategoryService categoryService = toCategoryService.transfer(categoryServiceDto, CategoryService.class);
		//categoryService.setCreatedBy(existingCategoryService.getCreatedBy());
		categoryService.setCreatedDate(existingCategoryService.getCreatedDate());

		// categoryService.setModifiedBy();
		categoryService.setModifiedDate(new Date());
		categoryService = categoryDAO.save(categoryService);

		CategoryServiceResponseDto response= toCategoryServiceResponse.transfer(categoryService, CategoryServiceResponseDto.class);
		response.setMessage(updationMessage); 
		return response;

	}

	@Override
	public List<CategoryServiceResponseDto> getAllCategoryServices() {
		List<CategoryService> categoryServiceList =categoryDAO.getAllCategoryServices();
		List<CategoryServiceResponseDto> categoryServiceResponseList = new ArrayList<>();
		for(CategoryService result : categoryServiceList) {
			categoryServiceResponseList.add(toCategoryServiceResponse.transfer(result, CategoryServiceResponseDto.class));
		}
		return categoryServiceResponseList;
		
	}

	@Override
	public void deleteCategoryService(String categoryId) {
		categoryDAO.deleteCategoryService(categoryId);

	}

}
