package com.abinbev.admin.dao;

import java.util.List;

import com.abinbev.admin.entity.CategoryServiceRoleMapping;

public interface CategoryServiceRoleMappingDAO {

	public CategoryServiceRoleMapping save(CategoryServiceRoleMapping categoryServiceRoleMapping);

	public List<CategoryServiceRoleMapping> findByUserRole(String userRole);

	public CategoryServiceRoleMapping findByModuleId(String moduleId);

}
