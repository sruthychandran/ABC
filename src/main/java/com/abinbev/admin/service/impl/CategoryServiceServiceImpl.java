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
import com.abinbev.admin.dao.CategoryServiceRoleMappingDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.CategoryServiceRoleMapping;
import com.abinbev.admin.exception.CategoryServiceCreationFailureException;
import com.abinbev.admin.exception.CategoryServiceNotFoundException;
import com.abinbev.admin.exception.CategoryServiceUpdationFailureException;
import com.abinbev.admin.requestDto.CategoryServiceDto;
import com.abinbev.admin.responseDto.BasicResponse;
import com.abinbev.admin.responseDto.CategoryServiceResponseDto;
import com.abinbev.admin.responseDto.ErrorResponse;
import com.abinbev.admin.responseDto.SuccessResponse;
import com.abinbev.admin.service.CategoryServiceRoleMappingService;
import com.abinbev.admin.service.CategoryServiceService;
import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.MapperUtil;

@Service

public class CategoryServiceServiceImpl implements CategoryServiceService {
	@Autowired
	MessageProperties messageProperties;
	@Autowired
	CategoryServiceDAO categoryDAO;

	MapperUtil<CategoryServiceDto, CategoryService> categoryServiceMapper = new MapperUtil<>();
	MapperUtil<CategoryService, CategoryServiceResponseDto> categoryServiceResponse = new MapperUtil<>();

	@Autowired
	private ErrorCodes errorCodes;

	@Autowired
	CategoryServiceRoleMappingService categoryServiceRoleMappingService;

	@Autowired
	CategoryServiceRoleMappingDAO categoryServiceRoleMappingDAO;

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
			throw new CategoryServiceCreationFailureException(errorCodes.getCategoryServiceSaveFailure());
		}

		BasicResponse<CategoryServiceResponseDto> basicResponse = new BasicResponse<CategoryServiceResponseDto>();
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getCatergoryServiceSaveSuccessMessage(),
				messageProperties.getCategoryServiceSaveSuccesCode());
		basicResponse.setMessage(message);
		

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
			throw new CategoryServiceNotFoundException(errorCodes.getCategoryServiceNotFound());
		}

		CategoryService categoryService = categoryServiceMapper.transfer(categoryServiceDto, CategoryService.class);

		categoryService.setId(existingCategoryService.getId());

		categoryService.setCreatedDate(existingCategoryService.getCreatedDate());

		categoryService.setStatus(existingCategoryService.getStatus());

		categoryService.setModifiedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(categoryService);
		if (categoryServiceObj != null) {
			categoryServiceResponseObj = categoryServiceResponse.transfer(categoryServiceObj,
					CategoryServiceResponseDto.class);

		} else {
			throw new CategoryServiceUpdationFailureException(errorCodes.getCategoryServiceUpdateFailure());
		}
		BasicResponse<CategoryServiceResponseDto> basicResponse = new BasicResponse<CategoryServiceResponseDto>();
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getCatergoryServiceUpdateSuccessMessage(),
				messageProperties.getCategoryServiceUpdateSuccesCode());
		basicResponse.setMessage(message);
	
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
			ErrorResponse error = new ErrorResponse(messageProperties.getNoContentErrorCode(),
					messageProperties.getNoContentErrorMessage());
			basicResponse.setError(error);
			return basicResponse;
		}

		Page<CategoryServiceResponseDto> categoryResponsePage = new PageImpl<CategoryServiceResponseDto>(
				categoryServiceResponseList, pageable, categoryServicePage.getContent().size());
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getCatergoryServiceRetrieveSuccessMessage(),
				messageProperties.getCategoryServiceRetrieveSuccesCode());
		basicResponse.setMessage(message);

		
		basicResponse.setData(categoryResponsePage);
		return basicResponse;

	}

	/**
	 * In this method we delete category service
	 */

	@Override
	public BasicResponse<CategoryServiceResponseDto> deleteCategoryService(String id)
			throws CategoryServiceNotFoundException {
		CategoryServiceResponseDto categoryServiceResponseObj = null;
		CategoryService existingCategoryService = categoryDAO.findById(id);
		if (existingCategoryService == null) {
			throw new CategoryServiceNotFoundException(errorCodes.getCategoryServiceNotFound());
		}
		existingCategoryService.setStatus(messageProperties.getInactiveStatus());

		existingCategoryService.setModifiedDate(new Date());
		CategoryService categoryServiceObj = categoryDAO.save(existingCategoryService);

		categoryServiceResponseObj = categoryServiceResponse.transfer(categoryServiceObj,
				CategoryServiceResponseDto.class);
		ErrorResponse error = new ErrorResponse(null, null);
		BasicResponse<CategoryServiceResponseDto> basicResponse = new BasicResponse<CategoryServiceResponseDto>();
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getCatergoryServiceDeleteSuccessMessage(),
				messageProperties.getCategoryServiceDeleteSuccesCode());
		basicResponse.setMessage(message);
		
		basicResponse.setData(categoryServiceResponseObj);
		return basicResponse;

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
			throw new CategoryServiceNotFoundException(errorCodes.getCategoryServiceNotFound());
		}
		CategoryServiceResponseDto categoryServiceResponseObj = categoryServiceResponse
				.transfer(existingCategoryService, CategoryServiceResponseDto.class);

		BasicResponse<CategoryServiceResponseDto> basicResponse = new BasicResponse<CategoryServiceResponseDto>();
		ErrorResponse error = new ErrorResponse(null, null);
		basicResponse.setError(error);
		SuccessResponse message = new SuccessResponse(messageProperties.getCatergoryServiceRetrieveSuccessMessage(),
				messageProperties.getCategoryServiceRetrieveSuccesCode());
		basicResponse.setMessage(message);
		
		basicResponse.setData(categoryServiceResponseObj);

		return basicResponse;
	}

	private List<CategoryServiceResponseDto> toCategoryResponseDto(List<CategoryService> categoryServiceList) {
		List<CategoryServiceResponseDto> categoryServiceResponseList = new ArrayList<>();
		for (CategoryService categoryService : categoryServiceList) {
			CategoryServiceResponseDto response = categoryServiceResponse.transfer(categoryService,
					CategoryServiceResponseDto.class);
			categoryServiceResponseList.add(response);
		}
		return categoryServiceResponseList;

	}

	@Override
	public void findByUserRole(String userRole) {
		
		List<CategoryServiceRoleMapping> categoryRoleList =categoryServiceRoleMappingService.findByUserRole(userRole);
		for(CategoryServiceRoleMapping categoryRole :categoryRoleList) {
			//categoryDAO.findByModuleId(categoryRole.getModuleId())
		}
		
	}

}
