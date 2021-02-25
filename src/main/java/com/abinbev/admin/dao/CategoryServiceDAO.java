package com.abinbev.admin.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.entity.CategoryService;






public interface CategoryServiceDAO  {

public CategoryService	save(CategoryService CategoryService) ;

public Page<CategoryService> getAllCategoryServices(Pageable pageable);


public List<CategoryService> findByCategoryId(String categoryId);

public CategoryService findById(String id);



}
