package com.ecommerce.main.service;

import java.util.List;

import com.ecommerce.main.model.Role;

public interface RoleService {
	Integer createRole(Role role);
	Integer deleteRole(Integer roleId);
	Integer updateRole(Role role);
	List<Role> getRole();
	Role getRoleByKey(Integer roleId);
	
	
	

}
