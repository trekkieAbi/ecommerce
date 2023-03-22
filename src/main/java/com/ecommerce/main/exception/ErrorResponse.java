package com.ecommerce.main.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class ErrorResponse {
	private int code;
	private String description;
	public ErrorResponse(int code, String description) {
		super();
		this.code = code;
		this.description = description;
	}
	

}
