package com.abinbev.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.CategoryServiceDAO;
import com.abinbev.admin.entity.CategoryService;

@Repository
public class CategoryServiceDAOImpl implements CategoryServiceDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	MessageProperties messageProperties;

	@Override
	public CategoryService save(CategoryService CategoryService) {
		CategoryService result = mongoTemplate.save(CategoryService);

		return result;

	}

	@Override
	public List<CategoryService> getAllCategoryServices() {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(messageProperties.getActiveStatus()));
		return mongoTemplate.find(query, CategoryService.class);
	}

	@Override
	public List<CategoryService> findByCategoryId(String categoryId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("categoryId").is(categoryId));
		return mongoTemplate.find(query, CategoryService.class);

	}
	@Override
	public CategoryService findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, CategoryService.class);

	}

}
