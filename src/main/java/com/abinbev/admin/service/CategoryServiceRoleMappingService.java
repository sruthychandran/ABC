package com.abinbev.admin.service;

import com.abinbev.admin.entity.CategoryServiceRoleMapping;

public interface CategoryServiceRoleMappingService {

	public CategoryServiceRoleMapping save(CategoryServiceRoleMapping categoryServiceRoleMapping);

	CategoryServiceRoleMapping findByModuleId(String moduleId);

}
