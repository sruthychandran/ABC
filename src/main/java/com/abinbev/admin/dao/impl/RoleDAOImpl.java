package com.abinbev.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.config.MessageProperties;
import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.Role;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class RoleDAOImpl implements RoleDAO {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	MessageProperties messageProperties;

	@Override
	public Role save(Role role) {
		Role result = mongoTemplate.save(role);

		return result;

	}

	@Override
	public void deleteRole(String roleId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("roleId").is(roleId));
		Role role = mongoTemplate.findOne(query, Role.class);

		role.setStatus("disable");
		mongoTemplate.save(role);

	}

	@Override
	public List<Role> getAllRoles() {

		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(messageProperties.getActiveStatus()));
		return mongoTemplate.find(query, Role.class);
	}

	@Override
	public Role findByRoleId(String roleId) {
		Query query = new Query();

		query.addCriteria(Criteria.where("roleId").is(roleId));
		return mongoTemplate.findOne(query, Role.class);
	}

}
