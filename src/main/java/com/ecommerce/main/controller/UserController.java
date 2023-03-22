package com.ecommerce.main.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.ecommerce.main.mapper.RoleAuthorityMapper;
import com.ecommerce.main.mapper.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.main.dto.UserDto;

import com.ecommerce.main.mapper.UserMapper;
import com.ecommerce.main.model.User;
import com.ecommerce.main.security.CustomUserDetailService;
import com.ecommerce.main.security.CustomUserDetails;
import com.ecommerce.main.service.UserService;
import com.ecommerce.main.util.ApiResponse;
import com.ecommerce.main.util.JwtHelper;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private UserMapper userMapper;
	

	
	@Autowired
	private RoleAuthorityMapper roleAuthorityMapper;
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private CustomUserDetailService customUserDetailsService;

	@Autowired(required = true)
	private AuthenticationManager authenticationManager;

	@PostMapping("/create")
	ResponseEntity<ApiResponse> RegisterUser(@RequestBody UserDto userDto) throws Exception {
		Integer result = this.service.createUser(userDto);
		if (result > 0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("User created successfully", Boolean.TRUE),
					HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("UserName or email exists already!!!", Boolean.FALSE),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@PutMapping("/delete/{userId}")

	ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		Boolean resultStatus = this.service.softDeleteUser(userId);
		if (resultStatus) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully!!!", true),
					HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("something went wrong!!", false),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/read/approve/{userId}")
	@PreAuthorize("hasAuthority('Read_Approved_User')")
	ResponseEntity<?> getApprovedUserById(@PathVariable Integer userId) {
		 return Optional.ofNullable(service.findApprovedUserById(userId))
			       .map(ResponseEntity::ok)
			       .orElseGet(ResponseEntity.notFound()::build);

	}
	
	@GetMapping("/read/approve")
	@PreAuthorize("hasAuthority('Read_All_Approved_User')")
	ResponseEntity<?> getApprovedAllUser() {
		List<User> users = this.service.getAllApprovedUser();
		if (!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("No approved users found!!!", false), HttpStatus.NOT_FOUND);

	}
	
	

	@PutMapping("/approve/{userId}")
	@PreAuthorize("hasAuthority('approve_user')")
	ResponseEntity<ApiResponse> approveUser(@PathVariable Integer userId, Authentication authentication) {

		Boolean resultStatus = service.approveUser(userId);
		if (resultStatus) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("user approved successfully!!!", true),
					HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("something went wrong!!", false),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping("/reject/{userId}")
	@PreAuthorize("hasAuthority('reject_user')")
	ResponseEntity<ApiResponse> rejectUser(@PathVariable("userId") Integer userId) {
		Boolean resultStatus = this.service.rejectUser(userId);

		if (resultStatus) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("user rejected successfully", true), HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("something went wrong!!", false),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody UserDto userDto) throws Exception {
		String userName = userDto.getEmail();
		String password = userDto.getPassword();
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDto.getEmail());
		authenticate(userName, password, userDetails.getAuthorities());
		User retrievedUser = userMapper.findApprovedUserByEmail(userName);
		String jwtToken = this.jwtHelper
				.generateToken(new CustomUserDetails(retrievedUser, roleAuthorityMapper,roleMapper));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwtToken);
		ApiResponse apiResponse = new ApiResponse("Token generated successfully", true);
		return ResponseEntity.ok().headers(headers).body(apiResponse);

	}

	private void authenticate(String userName, String password, Collection<? extends GrantedAuthority> authorities)
			throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userName, password, authorities);
		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (DisabledException e) {
			throw new Exception("user is disabled!!!");
		}
	}

}
