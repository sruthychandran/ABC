package com.abinbev.admin.dao;


import java.util.List;

import com.abinbev.admin.entity.CategoryService;






public interface CategoryServiceDAO  {

public CategoryService	save(CategoryService CategoryService) ;

public List<CategoryService> getAllCategoryServices();


public CategoryService findByCategoryId(String categoryId);



}
