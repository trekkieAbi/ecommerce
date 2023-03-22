package com.ecommerce.main.mapper;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.User;

@Mapper
public interface UserMapper {
	
	Integer saveUser(User user);
	Integer approveUser(User user);
	Integer rejectUser(User user);
	User findPendingUserById(Integer id);
	ArrayList<User> getAllApprovedUser();
	Optional<User> findApprovedUserById(Integer id);
	Integer softDeleteUser(User user);
	ArrayList<User> getAllUser();
	User findUserByEmailOrUserName(UserDto userDto);
	User findApprovedUserByEmail(String email);
	Optional<User> findUserById(Integer id);
	
	Integer deleteRejectedUser(Integer userId);
	

}
