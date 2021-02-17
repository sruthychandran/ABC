package com.abinbev.admin.dao.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;





@Repository
public class CategoryServiceDAOImpl implements  CategoryServiceDAO  {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public CategoryService save(CategoryService CategoryService) {
		CategoryService result = mongoTemplate.save(CategoryService);

		return result;
		
	}

	@Override
	public List<CategoryService> getAllCategoryServices() {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(true));
		return mongoTemplate.find(query, CategoryService.class);
	}

	@Override
	public void deleteCategoryService(String categoryId) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("categoryId").is(categoryId));
		CategoryService categoryService = mongoTemplate.findOne(query, CategoryService.class);
		
		categoryService.setStatus("disable");
		mongoTemplate.save(categoryService);
	}

	@Override
	public CategoryService findByCategoryId(String categoryId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("categoryId").is(categoryId));
		return  mongoTemplate.findOne(query, CategoryService.class);
		
	}

	



}
