package com.ecommerce.main.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.main.mapper.RoleMapper;
import com.ecommerce.main.model.Role;
import com.ecommerce.main.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Integer createRole(Role role) {
		Integer affectedRow = 0;

		Role retrievedRole = new Role();
		retrievedRole = roleMapper.findByName(role.getName());
		if (retrievedRole != null) {
			return affectedRow;
		}
		affectedRow = roleMapper.saveRole(role);
		return affectedRow;

	}

	@Override
	public Integer deleteRole(Integer roleId) {
		Integer affectedRow = roleMapper.deleteRole(roleId);
		return affectedRow;
	}

	@Override
	public Integer updateRole(Role role) {
		Integer affectedRow = roleMapper.updateRole(role);
		return affectedRow;
	}

	@Override
	public List<Role> getRole() {
		List<Role> allRoles = roleMapper.getAllRole();

		return allRoles;
	}

	@Override
	public Role getRoleByKey(Integer roleId) {
		System.out.println("Data");
		Role retrievedRole = roleMapper.findById(roleId);

		return retrievedRole;
	}

	

}
