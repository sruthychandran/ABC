package com.abinbev.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
import com.abinbev.admin.requestDto.TestCategoryDto;
import com.abinbev.admin.requestDto.TestModuleDto;
import com.abinbev.admin.requestDto.TestSubModuleDto;
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
			response = categoryServiceResponse.transfer(categoryServiceObj, CategoryServiceResponseDto.class);

			response.setMessage(messageProperties.getUpdationMessage());
		} else {
			throw new CategoryServiceUpdationFailureException(
					messageProperties.getCategoryServiceUpdateFailureMessage());
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
		if (categoryServiceList != null) {
			for (CategoryService result : categoryServiceList) {
				categoryServiceResponseList
						.add(categoryServiceResponse.transfer(result, CategoryServiceResponseDto.class));
			}
		}
		return categoryServiceResponseList;

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
	public CategoryServiceResponseDto findById(String id) throws CategoryServiceNotFoundException {
		CategoryService result = categoryDAO.findById(id);
		CategoryServiceResponseDto response = categoryServiceResponse.transfer(result,
				CategoryServiceResponseDto.class);
		return response;
	}

	@Override
	public HashMap<String, List<Object>> findModulesByCategoryId(String categoryId) {
		// getCategoryById
		// check whether module exist
		// yes= add moduleDetails,
		// check whether submodule exist,
		// yes= add subModuleDetails
		// loop continued
		TestCategoryDto tcd = new TestCategoryDto();
		List<TestModuleDto> tmdList = new ArrayList<>();
		List<CategoryService> categoryServiceList = categoryDAO.findByCategoryId(categoryId);
		TestModuleDto tmd = new TestModuleDto();
		for (CategoryService categoryList : categoryServiceList) {

			if (categoryList.getModuleId() != null) {
			
				tmd.setModuleId(categoryList.getModuleId());
				tmd.setModuleName(categoryList.getModuleName());
				if (tmdList.contains(tmd)) {
					int t = tmdList.indexOf(tmd);
					tmd = tmdList.get(t);
					System.out.println("ffffffffffffffffffffff" + t);
				} else {
					tmdList.add(tmd);
					System.out.println("GGGGGGGGGGGGGGGGGGGGggFALSE");
				}

				if (categoryList.getSubModuleId() != null) {
					System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                  TestSubModuleDto tsmd =  new TestSubModuleDto();
                  tsmd.setSubModuleId(categoryList.getSubModuleId());
                  tsmd.setSubModuleName(categoryList.getSubModuleName());
                   if(tmd.getSubModules() != null) {
                	   System.out.println("qwertyuiop>>>>>>>>>>>>>>>>>>"+tmd.getSubModules().size());
                   }
                  //tmd.getSubModules().add(tsmd);
				}

			}

		}

		/*
		 * List<CategoryService> distinctElements = categoryServiceList.stream()
		 * .filter(distinctByKey(cust ->
		 * cust.getModuleId())).collect(Collectors.toList());
		 * 
		 * Map<String, List<CategoryService>> groupByModuleIdMap =
		 * categoryServiceList.stream().collect(Collectors.groupingBy(CategoryService::
		 * getModuleId));
		 * 
		 * groupByModuleIdMap.keySet(); for( String g:groupByModuleIdMap.keySet()) {
		 * List<CategoryService> d = groupByModuleIdMap.get(g); for(CategoryService c :
		 * d) { c. } }
		 */

		 System.out.println("rrrrrrrrrrrrrrrrrrrrrrrr"+tmd );

		return null;
	}

	// Utility function
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
