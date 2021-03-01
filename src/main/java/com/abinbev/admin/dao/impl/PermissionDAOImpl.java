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
import com.abinbev.admin.dao.PermissionDAO;
import com.abinbev.admin.entity.Permission;

@Repository
public class PermissionDAOImpl implements PermissionDAO {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	MessageProperties messageProperties;

	@Override
	public Permission save(Permission permission) {
		Permission result = mongoTemplate.save(permission);

		return result;

	}

	@Override
	public void deletePermission(String permissionId) {

		Query query = new Query();
		query.addCriteria(Criteria.where("permissionId").is(permissionId));
		Permission permission = mongoTemplate.findOne(query, Permission.class);

		mongoTemplate.save(permission);

	}

	@Override
	public Page<Permission> getAllPermissions(Pageable pageable) {

		Query query = new Query().with(pageable);

		List<Permission> permissionList = mongoTemplate.find(query, Permission.class);

		Page<Permission> permissionPage = PageableExecutionUtils.getPage(permissionList, pageable,
				() -> mongoTemplate.count(query, Permission.class));

		return permissionPage;

	}

	@Override
	public Permission findByPermissionId(String permissionId) {
		Query query = new Query();

		query.addCriteria(Criteria.where("permissionId").is(permissionId));
		return mongoTemplate.findOne(query, Permission.class);
	}

	@Override
	public Permission findById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Permission.class);
	}

}
