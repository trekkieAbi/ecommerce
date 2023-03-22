package com.ecommerce.main.security;

import com.ecommerce.main.mapper.RoleAuthorityMapper;
import com.ecommerce.main.mapper.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.main.mapper.UserMapper;
import com.ecommerce.main.model.User;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private UserMapper mapper;
	@Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
   
	@Autowired
	private RoleMapper roleMapper;



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User retrievedUser=mapper.findApprovedUserByEmail(username);
		if(retrievedUser==null) {
			throw new UsernameNotFoundException("user not found with email "+username);
		}


		CustomUserDetails customUserDetails=new CustomUserDetails(retrievedUser,roleAuthorityMapper,roleMapper);
		
		return customUserDetails;
	}
	
	
	
	

}
