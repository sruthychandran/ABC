package com.abinbev.admin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.abinbev.admin.entity.User;

public interface UserDAO {

	public User save(User user);

	Page<User> getAllUsers(Pageable pageable);

	public User findByEmail(String emailId);

	public void deleteAll();
}
