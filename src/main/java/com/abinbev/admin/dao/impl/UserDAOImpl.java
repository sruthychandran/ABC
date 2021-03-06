package com.abinbev.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.UserDAO;
import com.abinbev.admin.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	MessageProperties messageProperties;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public User save(User user) {
		User createUserObj = mongoTemplate.save(user);

		return createUserObj;

	}

	@Override
	public Page<User> getAllUsers(Pageable pageable) {
		Query query = new Query().with(pageable);
		query.addCriteria(Criteria.where("status").is(messageProperties.getActiveStatus()));
		List<User> userList = mongoTemplate.find(query, User.class);

		Page<User> userPage = PageableExecutionUtils.getPage(userList, pageable,
				() -> mongoTemplate.count(query, User.class));

		return userPage;

	}

	@Override
	public User findByEmail(String emailId) {
		Query query = new Query();

		query.addCriteria(Criteria.where("emailId").is(emailId));
		query.addCriteria(Criteria.where("status").is(messageProperties.getActiveStatus()));
		return mongoTemplate.findOne(query, User.class);
	}

	/*
	 * public void deleteAll() { mongoTemplate.remove(new Query(), User.class); }
	 */

}
