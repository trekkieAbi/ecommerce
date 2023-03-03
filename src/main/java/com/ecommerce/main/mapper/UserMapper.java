package com.ecommerce.main.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.model.User;

@Mapper
public interface UserMapper {
	
	Integer saveUser(UserDto userDto);
	Integer approveUser(User user);
	Integer rejectUser(User user);
	User findPendingUserById(Integer id);
	ArrayList<User> getAllApprovedUser();
	User findApprovedUserById(Integer id);
	Integer softDeleteUser(Integer id);
	ArrayList<User> getAllUser();
	

}
