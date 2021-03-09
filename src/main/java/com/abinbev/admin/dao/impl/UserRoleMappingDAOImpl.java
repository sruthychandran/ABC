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
import com.abinbev.admin.dao.UserRoleMappingDAO;
import com.abinbev.admin.entity.CategoryService;
import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.UserRoleMapping;
import com.abinbev.admin.entity.Role;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRoleMappingDAOImpl implements UserRoleMappingDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	MessageProperties messageProperties;

	@Override
	public UserRoleMapping save(UserRoleMapping userRole) {
		UserRoleMapping result = mongoTemplate.save(userRole);

		return result;

	}
	@Override
	public List<UserRoleMapping> findByUserRole(String userRole) {
		Query query = new Query();
        query.addCriteria(Criteria.where("userRoles").is(userRole));
		return mongoTemplate.find(query, UserRoleMapping.class);
	}


}
