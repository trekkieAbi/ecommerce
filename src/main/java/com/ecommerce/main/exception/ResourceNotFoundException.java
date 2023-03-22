package com.ecommerce.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private final String message;

	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}
	

}
