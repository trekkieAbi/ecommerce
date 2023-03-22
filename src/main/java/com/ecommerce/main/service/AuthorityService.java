package com.ecommerce.main.service;

import java.util.List;

import com.ecommerce.main.model.Authority;


public interface AuthorityService {
	
	Integer createAuthority(Authority authority)throws Exception;
	Integer deleteAuthority(Integer id)throws Exception;
	Integer updateAuthority(Authority authority);
	List<Authority> getAllAuthority();
	Authority getAuthorityByKey(Integer authId);

}
