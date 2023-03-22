package com.ecommerce.main.model;

import java.util.ArrayList;
import java.util.List;



public class Role {

	private Integer id;

//	@NotEmpty(message = "role names should not be blank")
//	@NotNull
	private String name;
	
	private List<Authority> authorities=new ArrayList<>();

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
