package com.abinbev.admin.dao;



import java.util.List;

import com.abinbev.admin.entity.User;





public interface UserDAO  {

	public User save(User user);
	
	List<User> getAllUsers();
	
	public void deleteUser(String uuid);
	public User findByEmail(String emailId);
	public User findByUuid(String uuid);

}
