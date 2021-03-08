package com.abinbev.admin.dao.impl;

import java.util.Date;
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
import com.abinbev.admin.dao.RoleDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;
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
	public Role deleteRole(Role role) {

		/*
		 * Query query = new Query();
		 * query.addCriteria(Criteria.where("roleId").is(roleId)); Role role =
		 * mongoTemplate.findOne(query, Role.class);
		 */

		role.setStatus("inactive");
		role.setModifiedDate(new Date());
		Role result =mongoTemplate.save(role);
		return result;

	}
	

	@Override
	public Page<Role> getAllRoles(Pageable pageable) {

		Query query = new Query().with(pageable);
		query.addCriteria(Criteria.where("status").is(messageProperties.getActiveStatus()));

		List<Role> roleList = mongoTemplate.find(query, Role.class);

		Page<Role> rolePage = PageableExecutionUtils.getPage(roleList, pageable,
				() -> mongoTemplate.count(query, Role.class));

		return rolePage;

	}

	@Override
	public Role findByRoleId(String roleId) {
		Query query = new Query();

		query.addCriteria(Criteria.where("roleId").is(roleId));
		return mongoTemplate.findOne(query, Role.class);
	}

	@Override
	public Role findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Role.class);
	}

}
