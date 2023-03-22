package com.ecommerce.main.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.ecommerce.main.mapper.RoleAuthorityMapper;
import com.ecommerce.main.mapper.RoleMapper;
import com.ecommerce.main.model.Authority;
import com.ecommerce.main.model.Role;
import com.ecommerce.main.model.User;

public class CustomUserDetails implements UserDetails {


	/**
	 *
	 */
	private static final long serialVersionUID = 1103739528678264212L;
	@Autowired(required = true)
	private final User user;
	
	
	
	private RoleAuthorityMapper roleAuthorityMapper;
	
	private RoleMapper roleMapper;
	
	



	
	@Autowired
	public CustomUserDetails(User user, RoleAuthorityMapper roleAuthorityMapper,RoleMapper roleMapper) {
		super();
		this.user = user;
		
		this.roleAuthorityMapper = roleAuthorityMapper;
		this.roleMapper=roleMapper;
		
	}
	
	


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

		Integer retrievedRoleId = user.getRoleId();
		Role retrievedRole=roleMapper.findById(retrievedRoleId);
			 ArrayList<Authority> retrievedAuthorities= roleAuthorityMapper.getAuthorityByRole(retrievedRole);
			if (!retrievedAuthorities.isEmpty()) {
				grantedAuthorities
						.addAll(retrievedAuthorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
								.collect(Collectors.toList()));
			}

		
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
