package com.abinbev.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public User save(User user) {
		user.setStatus(true);
		User result = mongoTemplate.save(user);

		return result;

	}

	@Override
	public List<User> getAllUsers() {
		return mongoTemplate.findAll(User.class);
	}

	@Override
	public void deleteUser(String uuid) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("uuid").is(uuid));
		User user = mongoTemplate.findOne(query, User.class);
		
		user.setStatus(false);
		mongoTemplate.save(user);

	}

}
