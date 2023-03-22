package com.ecommerce.main.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ecommerce.main.model.Authority;
import com.ecommerce.main.model.Role;
import com.ecommerce.main.model.RoleAuthority;

@Mapper
public interface RoleAuthorityMapper {
	Integer saveRoleAuthority(RoleAuthority roleAuthority);
	Integer deleteRoleAuthority(RoleAuthority roleAuthority);
	ArrayList<Authority> getAuthorityByRole(Role role);
	RoleAuthority checkWhetherDuplicateRoleAuthority(RoleAuthority roleAuthority);
	RoleAuthority getRoleAuthorityById(Integer roleAuthorityId);

	
}
