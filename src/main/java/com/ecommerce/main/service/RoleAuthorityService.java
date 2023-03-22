package com.ecommerce.main.service;

import java.util.ArrayList;

import com.ecommerce.main.model.Authority;
import com.ecommerce.main.model.RoleAuthority;

public interface RoleAuthorityService {
	Integer createRoleAuthority(RoleAuthority roleAuthority);
	Integer deleteRoleAuthority(Integer roleAuthorityId);
	ArrayList<Authority> getAllAuthorityByRole(Integer roleId) throws Exception;

}
