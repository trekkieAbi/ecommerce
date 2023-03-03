package com.ecommerce.main.exception;

public class ResourceNotFoundException extends RuntimeException {
	private String message;

	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}
	

}
