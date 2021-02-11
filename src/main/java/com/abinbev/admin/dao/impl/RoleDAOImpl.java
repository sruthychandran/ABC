package com.abinbev.admin.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;





@Repository
public class RoleDAOImpl implements  RoleDAO  {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Role save(Role role) {
		Role result = mongoTemplate.save(role);

		return result;
		
	}

}
