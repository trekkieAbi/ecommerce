package com.ecommerce.main.model;

public class Authority {
	private Integer id;
	private String name;
	private Integer roleId;
	
	

	public Authority(Integer id, String name, Integer roleId) {
		super();
		this.id = id;
		this.name = name;
		this.roleId = roleId;
	}
	
	

	public Authority() {
		super();
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



	public Integer getRoleId() {
		return roleId;
	}



	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
