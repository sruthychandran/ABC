package com.abinbev.admin.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.entity.Role;

public interface RoleDAO {

	public Role save(Role role);

	public Role deleteRole(Role role);

	public Page<Role> getAllRoles(Pageable pageable);

	Role findByRoleId(String roleId);

	public Role findById(String id);
}
