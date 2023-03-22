package com.ecommerce.main.mapper;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ecommerce.main.model.Authority;

@Mapper
public interface AuthorityMapper {
	Integer saveAuthority(Authority authority);
	Integer deleteAuthority(Authority authority);
	Integer updateAuthority(Authority authority);
	Optional<Authority> findById(Integer id);
	ArrayList<Authority> getAllAuthority();
	Optional<Authority> findByName(String name);
	
	
	

}
