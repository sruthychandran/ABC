package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.service.CategoryServiceService;
import com.abinbev.admin.utility.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceServiceImpl implements CategoryServiceService {
	@Autowired
	MessageProperties messageProperties;
	@Autowired
	CategoryServiceDAO categoryDAO;

	MapperUtil<CategoryServiceDto, CategoryService> toCategoryService = new MapperUtil<>();
	MapperUtil<CategoryService, CategoryServiceResponseDto> toCategoryServiceResponse = new MapperUtil<>();

	@Override
	public CategoryServiceResponseDto saveCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceCreationFailureException {
		CategoryServiceResponseDto response = null;
		CategoryService categoryService = toCategoryService.transfer(categoryServiceDto, CategoryService.class);
		categoryService.setStatus(messageProperties.getActiveStatus());
		categoryService.setCreatedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(categoryService);

		if (categoryServiceObj != null) {

			response = toCategoryServiceResponse.transfer(categoryServiceObj, CategoryServiceResponseDto.class);
			response.setMessage(messageProperties.getSaveMessage());
		} else {
			throw new CategoryServiceCreationFailureException(messageProperties.getCategoryServiceNotFoundMessage());
		}

		return response;
	}

	@Override
	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceNotFoundException {

		CategoryServiceResponseDto response = null;
		CategoryService existingCategoryService = findByCategoryId(categoryServiceDto.getCategoryId());

		CategoryService categoryService = toCategoryService.transfer(categoryServiceDto, CategoryService.class);

		categoryService.setId(existingCategoryService.getCategoryId());
		categoryService.setCreatedDate(existingCategoryService.getCreatedDate());

		categoryService.setModifiedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(categoryService);
		if (categoryServiceObj != null) {
			response = toCategoryServiceResponse.transfer(categoryServiceObj, CategoryServiceResponseDto.class);

			response.setMessage(messageProperties.getUpdationMessage());
		} else {
			// throw new
			// categoryServiceFailureException(messageProperties.getUserSaveFailureMessage());
		}

		return response;

	}

	@Override
	public List<CategoryServiceResponseDto> getAllCategoryServices() {
		List<CategoryService> categoryServiceList = categoryDAO.getAllCategoryServices();
		List<CategoryServiceResponseDto> categoryServiceResponseList = new ArrayList<>();
		for (CategoryService result : categoryServiceList) {
			categoryServiceResponseList
					.add(toCategoryServiceResponse.transfer(result, CategoryServiceResponseDto.class));
		}
		return categoryServiceResponseList;

	}

	@Override
	public void deleteCategoryService(String categoryId) throws CategoryServiceNotFoundException {
		CategoryService existingCategoryService = findByCategoryId(categoryId);

		existingCategoryService.setStatus(messageProperties.getInactiveStatus());
		categoryDAO.save(existingCategoryService);

	}

	@Override
	public CategoryServiceResponseDto findCategoryService(String categoryId) {
		CategoryService result = categoryDAO.findByCategoryId(categoryId);
		CategoryServiceResponseDto response = toCategoryServiceResponse.transfer(result,
				CategoryServiceResponseDto.class);
		return response;
	}

	private CategoryService findByCategoryId(String categoryId) throws CategoryServiceNotFoundException {
		CategoryService existingCategoryService = categoryDAO.findByCategoryId(categoryId);

		if (existingCategoryService == null) {
			throw new CategoryServiceNotFoundException(messageProperties.getCategoryServiceNotFoundMessage());
		}
		return existingCategoryService;
	}

}
