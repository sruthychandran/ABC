package com.abinbev.admin.dao;

import java.util.List;

import com.abinbev.admin.entity.Role;
import com.abinbev.admin.entity.UserRoleMapping;

public interface UserRoleMappingDAO {

	public UserRoleMapping save(UserRoleMapping userRoleMapping);

	public List<UserRoleMapping> findByUserRole(String userRole);

}
