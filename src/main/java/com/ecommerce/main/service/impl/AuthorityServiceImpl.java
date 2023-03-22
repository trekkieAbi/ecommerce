package com.ecommerce.main.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.main.mapper.AuthorityMapper;

import com.ecommerce.main.model.Authority;

import com.ecommerce.main.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityMapper authorityMapper;

	@Override
	public Integer createAuthority(Authority authority) throws Exception {
		Optional<Authority> authority2=authorityMapper.findByName(authority.getName().toLowerCase());
				if(!authority2.isEmpty()) {
					throw new Exception("authority with the given name already exists!!!");
			
		}
		authority.setName(authority.getName().toLowerCase());
		authorityMapper.saveAuthority(authority);
		System.out.println(authority.getId());
		return 1;
	}

	@Override
	public Integer deleteAuthority(Integer id) throws Exception {

		Optional<Authority> authority = authorityMapper.findById(id);
		if (authority.isEmpty()) {
			throw new Exception("No authority with the given id exists");
		}
		Integer resultRow = authorityMapper.deleteAuthority(authority.get());
		return resultRow;
	}

	@Override
	public Integer updateAuthority(Authority authority) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Authority> getAllAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Authority getAuthorityByKey(Integer authId) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @Override public Integer deleteAuthority(Integer id) { Authority
	 * authority=authorityMapper.findById(id); Integer affectedRow=0; try {
	 * if(authority==null) { throw new
	 * ResourceNotFoundException("No authority with the given id "+id+" exists!!!");
	 * } else {
	 * 
	 * 
	 * affectedRow=authorityMapper.deleteAuthority(id); return affectedRow; }
	 * 
	 * }catch(ResourceNotFoundException exception) { exception.printStackTrace(); }
	 * return affectedRow;
	 * 
	 * 
	 * }
	 * 
	 * @Override public Integer updateAuthority(Authority authority) { Authority
	 * retrievedAuthority=authorityMapper.findById(authority.getId()); Integer
	 * affectedRow=0;
	 * 
	 * if(retrievedAuthority==null) { throw new
	 * ResourceNotFoundException("Authority with the given id "+authority.getId()
	 * +" does not exist!!"); } else {
	 * affectedRow=authorityMapper.updateAuthority(authority); }
	 * 
	 * 
	 * 
	 * return affectedRow; }
	 * 
	 * @Override public List<Authority> getAllAuthority() { List<Authority>
	 * authorities=authorityMapper.getAllAuthority(); return authorities; }
	 * 
	 * @Override public Authority getAuthorityByKey(Integer authId) { Authority
	 * authority=authorityMapper.findById(authId); return authority; }
	 */

	/*
	 * @Override public List<Authority> getAllAuthorityByRole(Integer roleId) {
	 * List<Authority> authorities=new ArrayList<>();
	 * authorities=authorityMapper.getAllAuthorityByRole(roleId);
	 * 
	 * 
	 * 
	 * return authorities; }
	 */

}
