package com.abinbev.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;
import com.abinbev.admin.responseDto.UserResponseDto;


@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public User save(User user) {
		User createUserObj = mongoTemplate.save(user);
		
		return  createUserObj;
		

	}

	@Override
	public List<User> getAllUsers() {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(true));
		return mongoTemplate.find(query, User.class);
	}

	/*
	 * @Override public void deleteUser(String emailId) {
	 * 
	 * Query query = new Query();
	 * query.addCriteria(Criteria.where("emailId").is(emailId)); User user =
	 * mongoTemplate.findOne(query, User.class);
	 * 
	 * user.setStatus("disable"); mongoTemplate.save(user);
	 * 
	 * }
	 */
	@Override
	public User findByEmail(String emailId) {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("emailId").is(emailId));
		return mongoTemplate.findOne(query, User.class);
	}
	
	
	
	public void deleteAll() {
		 mongoTemplate.remove(new Query(),User.class);
	}



}
