package com.abinbev.admin.dao;


import java.util.List;

import com.abinbev.admin.entity.Role;






public interface RoleDAO  {

	public Role save(Role role) ;
	
	public void deleteRole(String roleId) ;
	
	public List<Role> getAllRoles();
}
