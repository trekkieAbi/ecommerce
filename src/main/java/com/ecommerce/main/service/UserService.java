package com.ecommerce.main.service;

import java.util.List;

import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.User;


public interface UserService {
	Integer createUser(UserDto user) throws Exception;


	Integer updateUser(User user);

	Boolean approveUser(Integer userId);
	
	Boolean rejectUser(Integer userId);
	List<User> getAllApprovedUser();
	Boolean softDeleteUser(Integer userId);
	List<User> getAllUser();
	
	  User  getUserFromUserDto(UserDto userDto);
	  User findApprovedUserById(Integer id);
	 User findApprovedUserByEmail(String email);
}
