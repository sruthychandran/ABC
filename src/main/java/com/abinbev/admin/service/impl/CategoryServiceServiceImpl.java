package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.ErrorResponse;
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
	public BasicResponse<CategoryServiceResponseDto> saveCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceCreationFailureException {
		CategoryServiceResponseDto categoryServiceResponseObj = null;
		CategoryService categoryService = categoryServiceMapper.transfer(categoryServiceDto, CategoryService.class);
		categoryService.setStatus(messageProperties.getActiveStatus());
		categoryService.setCreatedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(categoryService);

		if (categoryServiceObj != null) {

			categoryServiceResponseObj = categoryServiceResponse.transfer(categoryServiceObj,
					CategoryServiceResponseDto.class);
		} else {
			throw new CategoryServiceCreationFailureException(messageProperties.getCategoryServiceSaveFailureMessage());
		}

		BasicResponse<CategoryServiceResponseDto> basicResponse = new BasicResponse<CategoryServiceResponseDto>();
		basicResponse.setMessage(messageProperties.getSaveMessage());
		basicResponse.setData(categoryServiceResponseObj);

		return basicResponse;
	}

	/**
	 * In this method we update a category service
	 */
	@Override
	public BasicResponse<CategoryServiceResponseDto> updateCategoryService(CategoryServiceDto categoryServiceDto)
			throws CategoryServiceNotFoundException, CategoryServiceUpdationFailureException {

		CategoryServiceResponseDto categoryServiceResponseObj = null;
		CategoryService existingCategoryService = categoryDAO.findById(categoryServiceDto.getId());

		if (existingCategoryService == null) {
			throw new CategoryServiceNotFoundException(messageProperties.getCategoryServiceNotFoundMessage());
		}

		CategoryService categoryService = categoryServiceMapper.transfer(categoryServiceDto, CategoryService.class);

		categoryService.setId(existingCategoryService.getId());

		categoryService.setCreatedDate(existingCategoryService.getCreatedDate());

		categoryService.setModifiedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(categoryService);
		if (categoryServiceObj != null) {
			categoryServiceResponseObj = categoryServiceResponse.transfer(categoryServiceObj,
					CategoryServiceResponseDto.class);

		} else {
			throw new CategoryServiceUpdationFailureException(
					messageProperties.getCategoryServiceUpdateFailureMessage());
		}
		BasicResponse<CategoryServiceResponseDto> basicResponse = new BasicResponse<CategoryServiceResponseDto>();
		basicResponse.setMessage(messageProperties.getUpdationMessage());
		basicResponse.setData(categoryServiceResponseObj);
		return basicResponse;

	}

	/**
	 * In this method we list all the category services
	 */
	@Override
	public BasicResponse<Page<CategoryServiceResponseDto>> getAllCategoryServices(Pageable pageable) {
		BasicResponse<Page<CategoryServiceResponseDto>> basicResponse = new BasicResponse<Page<CategoryServiceResponseDto>>();
		Page<CategoryService> categoryServicePage = categoryDAO.getAllCategoryServices(pageable);

		List<CategoryServiceResponseDto> categoryServiceResponseList = new ArrayList<CategoryServiceResponseDto>();
		if (categoryServicePage.getContent() != null) {
			for (CategoryService result : categoryServicePage.getContent()) {
				categoryServiceResponseList
						.add(categoryServiceResponse.transfer(result, CategoryServiceResponseDto.class));
			}
		} else {
			ErrorResponse error = new ErrorResponse("10008", "no content");
			basicResponse.setError(error);
			return basicResponse;
		}

		Page<CategoryServiceResponseDto> categoryResponsePage = new PageImpl<CategoryServiceResponseDto>(
				categoryServiceResponseList, pageable, categoryServicePage.getContent().size());
		basicResponse.setData(categoryResponsePage);
		return basicResponse;

	}

	/**
	 * In this method we delete category service
	 */

	@Override
	public void deleteCategoryService(String id) throws CategoryServiceNotFoundException {
		CategoryService existingCategoryService = categoryDAO.findById(id);
		if (existingCategoryService == null) {
			throw new CategoryServiceNotFoundException(messageProperties.getCategoryServiceNotFoundMessage());
		}
		existingCategoryService.setStatus(messageProperties.getInactiveStatus());
		categoryDAO.save(existingCategoryService);

	}

	/**
	 * In this method we can find a category by id
	 * 
	 * @throws CategoryServiceNotFoundException
	 */

	@Override
	public BasicResponse<CategoryServiceResponseDto> findById(String id) throws CategoryServiceNotFoundException {
		CategoryService existingCategoryService = categoryDAO.findById(id);
		if (existingCategoryService == null) {
			throw new CategoryServiceNotFoundException(messageProperties.getCategoryServiceNotFoundMessage());
		}
		CategoryServiceResponseDto categoryServiceResponseObj = categoryServiceResponse
				.transfer(existingCategoryService, CategoryServiceResponseDto.class);

		BasicResponse<CategoryServiceResponseDto> basicResponse = new BasicResponse<CategoryServiceResponseDto>();
		//basicResponse.setMessage();
		basicResponse.setData(categoryServiceResponseObj);

		return basicResponse;
	}

}
