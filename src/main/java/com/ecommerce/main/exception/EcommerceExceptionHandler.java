package com.ecommerce.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class EcommerceExceptionHandler {
	
	public static ResponseEntity<ErrorResponse> handleExceptionForResourceNotFound(){
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_PROBLEM_JSON)
				.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),"Resource not found!!!"));
	}

}
