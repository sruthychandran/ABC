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
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
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

	MapperUtil<CategoryServiceDto, CategoryService> categoryServiceMapper = new MapperUtil<>();
	MapperUtil<CategoryService, CategoryServiceResponseDto> categoryServiceResponse = new MapperUtil<>();

	/**
	 * In this method we create a category service
	 */
	@Override
	public CategoryServiceResponseDto saveCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceCreationFailureException {
		CategoryServiceResponseDto response = null;
		CategoryService categoryService = categoryServiceMapper.transfer(categoryServiceDto, CategoryService.class);
		categoryService.setStatus(messageProperties.getActiveStatus());
		categoryService.setCreatedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(categoryService);

		if (categoryServiceObj != null) {

			response = categoryServiceResponse.transfer(categoryServiceObj, CategoryServiceResponseDto.class);
			response.setMessage(messageProperties.getSaveMessage());
		} else {
			throw new CategoryServiceCreationFailureException(messageProperties.getCategoryServiceNotFoundMessage());
		}

		return response;
	}

	/**
	 * In this method we update a category service
	 */
	@Override
	public CategoryServiceResponseDto updateCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceNotFoundException, CategoryServiceUpdationFailureException {

		CategoryServiceResponseDto response = null;
		CategoryService existingCategoryService = findByCategoryId(categoryServiceDto.getCategoryId());

		CategoryService categoryService = categoryServiceMapper.transfer(categoryServiceDto, CategoryService.class);

		categoryService.setId(existingCategoryService.getCategoryId());
		
		categoryService.setCreatedDate(existingCategoryService.getCreatedDate());

		categoryService.setModifiedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(categoryService);
		if (categoryServiceObj != null) {
			response = categoryServiceResponse.transfer(categoryServiceObj, CategoryServiceResponseDto.class);

			response.setMessage(messageProperties.getUpdationMessage());
		} else {
			 throw new CategoryServiceUpdationFailureException(messageProperties.getCategoryServiceUpdateFailureMessage());
		}

		return response;

	}

	/**
	 * In this method we list all the category services
	 */
	@Override
	public List<CategoryServiceResponseDto> getAllCategoryServices() {
		List<CategoryService> categoryServiceList = categoryDAO.getAllCategoryServices();
		List<CategoryServiceResponseDto> categoryServiceResponseList = new ArrayList<>();
		for (CategoryService result : categoryServiceList) {
			categoryServiceResponseList
					.add(categoryServiceResponse.transfer(result, CategoryServiceResponseDto.class));
		}
		return categoryServiceResponseList;

	}

	/**
	 * In this method we delete category service
	 */
	@Override
	public void deleteCategoryService(String categoryId) throws CategoryServiceNotFoundException {
		CategoryService existingCategoryService = findByCategoryId(categoryId);

		existingCategoryService.setStatus(messageProperties.getInactiveStatus());
		categoryDAO.save(existingCategoryService);

	}

	/**
	 * In this method we can find a category by its id
	 */
	@Override
	public CategoryServiceResponseDto findCategoryService(String categoryId) {
		CategoryService result = categoryDAO.findByCategoryId(categoryId);
		CategoryServiceResponseDto response = categoryServiceResponse.transfer(result,
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
