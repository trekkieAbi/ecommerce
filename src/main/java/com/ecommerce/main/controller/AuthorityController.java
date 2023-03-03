package com.ecommerce.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.main.model.Authority;
import com.ecommerce.main.service.AuthorityService;
import com.ecommerce.main.util.ApiResponse;

@RestController
@RequestMapping("/authority")
public class AuthorityController {
	@Autowired
	private AuthorityService authorityService;
	
	@PostMapping("/create")
	ResponseEntity<ApiResponse> createAuthority(@RequestBody Authority authority){
		Integer affectedRow=this.authorityService.createAuthority(authority);
		if(affectedRow>0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Authority created successfully!!!",Boolean.TRUE),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!",Boolean.FALSE),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
	}
	
	@DeleteMapping("/delete/{id}")
	ResponseEntity<ApiResponse> deleteAuthority(@PathVariable("id") Integer id){
		Integer affectedRow=this.authorityService.deleteAuthority(id);
		if(affectedRow>0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("authority deleted successfully", Boolean.TRUE),HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!",Boolean.FALSE),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PutMapping("/update")
	ResponseEntity<ApiResponse> updateAuthority(@RequestBody Authority authority){
		Integer affectedRow=this.authorityService.updateAuthority(authority);
		if(affectedRow>0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Data updated successfully", Boolean.TRUE),HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went Wrong", Boolean.FALSE),HttpStatus.INTERNAL_SERVER_ERROR);

		
		
		
	}
	
	
	
	
	@GetMapping("/readByRole")
	ResponseEntity<?> getAllAuthorityByRole(@RequestParam("roleId")Integer roleId){
		List<Authority> authorities=this.authorityService.getAllAuthorityByRole(roleId);
		if(authorities.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse("No authority with the given role!!",Boolean.FALSE),HttpStatus.OK);
		}
		return new ResponseEntity<>(authorities,HttpStatus.OK);
		
		
	}
	
	@GetMapping("/readAll")
	ResponseEntity<?> getAllAuthority(){
		List<Authority> authorities=this.authorityService.getAllAuthority();
		if(authorities.isEmpty()) {
			return new ResponseEntity<>(new ApiResponse("there is no authority!!",Boolean.FALSE),HttpStatus.OK);
		}
		return new ResponseEntity<>(authorities,HttpStatus.OK);

		
	
		
	}
	
	@GetMapping("/readById")
	ResponseEntity<?> getAuthorityById(@RequestParam("authId")Integer authId){
		Authority authority=this.authorityService.getAuthorityByKey(authId);
		if(authority==null) {
			return new ResponseEntity<>(new ApiResponse("there is no authority with the given id "+authId,Boolean.FALSE),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(authority,HttpStatus.OK);
		
		
		
	}
	
	
	
	
	

}
