package com.ecommerce.main.service;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.main.model.Authority;


public interface AuthorityService {
	
	Integer createAuthority(Authority authority);
	Integer deleteAuthority(Integer id);
	Integer updateAuthority(Authority authority);
	List<Authority> getAllAuthority();
	List<Authority> getAllAuthorityByRole(Integer roleId);
	Authority getAuthorityByKey(Integer authId);

}
