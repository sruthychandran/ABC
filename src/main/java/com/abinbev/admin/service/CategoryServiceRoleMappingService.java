package com.abinbev.admin.service;

import java.util.List;

import com.abinbev.admin.entity.CategoryServiceRoleMapping;

public interface CategoryServiceRoleMappingService {

	public CategoryServiceRoleMapping save(CategoryServiceRoleMapping categoryServiceRoleMapping);

	CategoryServiceRoleMapping findByModuleId(String moduleId);

	public List<CategoryServiceRoleMapping> findByUserRole(String userRole);

}
