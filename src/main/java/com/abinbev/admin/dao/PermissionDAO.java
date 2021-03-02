package com.abinbev.admin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.entity.Permission;

public interface PermissionDAO {

	public Permission save(Permission permission);

	public void deletePermission(String permissionId);

	public Page<Permission> getAllPermissions(Pageable pageable);

	Permission findByPermissionId(String roleId);

	public Permission findById(String id);
}
