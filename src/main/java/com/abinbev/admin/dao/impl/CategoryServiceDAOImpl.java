package com.abinbev.admin.dao.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
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



}
