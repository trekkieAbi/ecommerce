package com.ecommerce.main.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ecommerce.main.model.Role;

@Mapper
public interface RoleMapper {

	Integer saveRole(Role role);

	Integer deleteRole(Integer id);

	Integer updateRole(Role role);

	ArrayList<Role> getAllRole();

	Role findById(Integer id);

	Role findByName(String name);

}
