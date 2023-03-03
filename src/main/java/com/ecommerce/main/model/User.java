package com.ecommerce.main.model;

public class User {
	private Integer id;

	private String userName;

	private String email;

	private String contactNumber;

	private String password;

	private String status;

	private Boolean isEnable = Boolean.FALSE;
	
	private Integer roleId;
	
	

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public User(Integer id, String userName, String email, String contactNumber, String password, String status,
			Boolean isEnable, Integer roleId) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.password = password;
		this.status = status;
		this.isEnable = isEnable;
		this.roleId = roleId;
	}

	public User() {
		super();
	}

}
