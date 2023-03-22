package com.ecommerce.main.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.main.mapper.AuthorityMapper;
import com.ecommerce.main.mapper.RoleAuthorityMapper;
import com.ecommerce.main.mapper.RoleMapper;
import com.ecommerce.main.model.Authority;
import com.ecommerce.main.model.Role;
import com.ecommerce.main.model.RoleAuthority;
import com.ecommerce.main.service.RoleAuthorityService;
@Service
public class RoleAuthorityServiceImpl implements RoleAuthorityService {
	@Autowired
	private RoleAuthorityMapper roleAuthorityMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Override
	public Integer createRoleAuthority(RoleAuthority roleAuthority) {
		Integer resultValue=0;
		if(checkWhetherRoleAuthorityExists(roleAuthority)) {
			if(roleAuthorityMapper.checkWhetherDuplicateRoleAuthority(roleAuthority)==null) {
				resultValue=roleAuthorityMapper.saveRoleAuthority(roleAuthority);
			}else {
				return resultValue; 
			}
		}
		return resultValue;
	}

	@Override
	public Integer deleteRoleAuthority(Integer roleAuthorityId) {
		Integer resultValue=0;
		RoleAuthority retrievedRoleAuthority=roleAuthorityMapper.getRoleAuthorityById(roleAuthorityId);
		if(retrievedRoleAuthority!=null) {
			resultValue=roleAuthorityMapper.deleteRoleAuthority(retrievedRoleAuthority);
			
		}
		return resultValue;
	}

	@Override
	public ArrayList<Authority> getAllAuthorityByRole(Integer roleId) throws Exception {
		Role retrievedRole=roleMapper.findById(roleId);
		if(retrievedRole!=null) {
			ArrayList<Authority> authorities=roleAuthorityMapper.getAuthorityByRole(retrievedRole);
			return authorities;
		}
		else {
			throw new Exception("No role found!!!");
		}
	}
	
	private Boolean checkWhetherRoleAuthorityExists(RoleAuthority roleAuthority) {
		Boolean status=false;
		Role role=roleMapper.findById(roleAuthority.getRoleId());
		Optional<Authority> authority=authorityMapper.findById(roleAuthority.getAuthorityId());
		if(role!=null && !authority.isEmpty()) {
			status=true;
		}
		return status;
	}

}
