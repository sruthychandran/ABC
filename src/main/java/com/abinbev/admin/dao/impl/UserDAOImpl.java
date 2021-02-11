package com.abinbev.admin.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;





@Repository
public class UserDAOImpl implements  UserDAO  {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public User save(User user) {
		User result = mongoTemplate.save(user);

		return result;
		
	}

}
