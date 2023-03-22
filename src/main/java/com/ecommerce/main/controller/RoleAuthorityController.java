package com.ecommerce.main.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.main.model.Authority;
import com.ecommerce.main.model.RoleAuthority;
import com.ecommerce.main.service.RoleAuthorityService;
import com.ecommerce.main.util.ApiResponse;

@RestController
@RequestMapping("/role-authority")
public class RoleAuthorityController {
	@Autowired
	private RoleAuthorityService roleAuthorityService;
	
	
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('manage_role_authority')")
	ResponseEntity<?> createRoleAuthority(@RequestBody RoleAuthority roleAuthority){
		Integer resultValue=this.roleAuthorityService.createRoleAuthority(roleAuthority);
		if(resultValue>0) {
		return new ResponseEntity<ApiResponse>(new ApiResponse("RoleAuthority created successfully", true),HttpStatus.CREATED);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!",false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@DeleteMapping("/delete/{roleAuthorityId}")
	@PreAuthorize("hasAuthority('manage_role_authority')")
	ResponseEntity<?> deleteRoleAuthority(@PathVariable Integer roleAuthorityId){
		Integer resultValue=this.roleAuthorityService.deleteRoleAuthority(roleAuthorityId);
		if(resultValue>0) {
		return new ResponseEntity<ApiResponse>(new ApiResponse("RoleAuthority deleted successfully", true),HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!",false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/read/all/{roleId}")
	@PreAuthorize("hasAuthority('manage_role_authority')")
	ResponseEntity<?> readAllAuthorityByRole(@PathVariable Integer roleId) throws Exception{
		ArrayList<Authority> authorities=this.roleAuthorityService.getAllAuthorityByRole(roleId);
		if(authorities.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("there is no authorities for the given role", false),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<Authority>>(authorities,HttpStatus.OK);
	}
	

}
