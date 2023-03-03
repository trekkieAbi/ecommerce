package com.ecommerce.main.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ecommerce.main.model.Authority;

@Mapper
public interface AuthorityMapper {
	Integer saveAuthority(Authority authority);
	Integer deleteAuthority(Integer id);
	Integer updateAuthority(Authority authority);
	Authority findById(Integer id);
	ArrayList<Authority> getAllAuthority();
	ArrayList<Authority> getAllAuthorityByRole(Integer roleId);
	Authority checkWhetherAuthorityExistsForGivenRole(Authority authority);
	
	

}
