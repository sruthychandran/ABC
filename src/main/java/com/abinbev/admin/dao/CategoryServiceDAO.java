package com.abinbev.admin.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.entity.CategoryService;






public interface CategoryServiceDAO  {

public CategoryService	save(CategoryService CategoryService) ;

}
