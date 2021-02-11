package com.abinbev.admin.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.abinbev.admin.entity.Role;






public interface RoleDAO  {

	public Role save(Role role) ;
}
