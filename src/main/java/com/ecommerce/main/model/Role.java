package com.ecommerce.main.model;

public class Role {

	private Integer id;

//	@NotEmpty(message = "role names should not be blank")
//	@NotNull
	private String name;

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
