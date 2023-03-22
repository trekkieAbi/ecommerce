package com.ecommerce.main.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.exception.ResourceNotFoundException;
import com.ecommerce.main.mapper.RoleMapper;
import com.ecommerce.main.mapper.UserMapper;
import com.ecommerce.main.model.Role;
import com.ecommerce.main.model.User;
import com.ecommerce.main.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Integer createUser(UserDto user) throws Exception {
		Role retrievedRole=roleMapper.findById(user.getRoleId());
		User convertedUser=getUserFromUserDto(user);
		if(retrievedRole==null) {
			throw new ResourceNotFoundException("No Role Found!!!");
		}
		else {
			User retrievedUser=userMapper.findUserByEmailOrUserName(user);
			if(retrievedUser!=null) {
				return 0;
			}
			if(retrievedRole.getName().equalsIgnoreCase("ROLE_ADMIN"))
					{
				convertedUser.setIsEnable(Boolean.TRUE);
				convertedUser.setStatus("Approved");
				}
			else if(retrievedRole.getName().equalsIgnoreCase("ROLE_USER")) {
				convertedUser.setStatus("Requested");
				convertedUser.setIsEnable(false);
			}
			else {
				throw new Exception("invalid role!!");
			}
			convertedUser.setPassword(passwordEncoder.encode(convertedUser.getPassword()));
			Integer affectedRow=userMapper.saveUser(convertedUser);
			return affectedRow;
		
			
			
		}
	
	}

	

	@Override
	public Integer updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean approveUser(Integer userId) {
		Boolean resultStatus=false;
		User pendingUser=this.userMapper.findPendingUserById(userId);
		if(pendingUser!=null) {
			this.userMapper.approveUser(pendingUser);
			resultStatus=true;
		}
		
		
		return resultStatus;
	}

	@Override
	public Boolean rejectUser(Integer userId) {
		Boolean resultStatus=false;
		User pendingUser=userMapper.findPendingUserById(userId);
		if(pendingUser!=null) {
			userMapper.rejectUser(pendingUser);
			Optional<User> user=userMapper.findUserById(userId);
			userMapper.deleteRejectedUser(user.get().getId());
			resultStatus=true;
			
		}
		return resultStatus;
	}
	
	

	@Override
	public List<User> getAllApprovedUser() {
		List<User> users=userMapper.getAllApprovedUser();
		return users;
	}

	@Override
	public Boolean softDeleteUser(Integer userId) {
	Boolean resultStatus=false;
		
     Optional<User> retrievedUser=userMapper.findApprovedUserById(userId);
     if(!retrievedUser.isEmpty()) {
    	 Integer affectedRow =userMapper.softDeleteUser(retrievedUser.get());
    	 if(affectedRow>0) {
    		 resultStatus=true;
    	 }
    	return resultStatus; 
     }
     	return resultStatus;
	}

	@Override
	public List<User> getAllUser() {
		return null;
	}
	@Override
	public 
	 User getUserFromUserDto(UserDto userDto) {
		User user=new User();
	user.setUsername(userDto.getUserName());
	user.setEmail(userDto.getEmail());
	user.setPassword(userDto.getPassword());
	user.setContactNumber(userDto.getContactNumber());
	user.setRoleId(userDto.getRoleId());
		
		return user;
	}



	@Override
	public User findApprovedUserById(Integer id) {
		Optional<User> user=this.userMapper.findUserById(id);
		if(user.get().getRoleId().equals(8)) {
			return null;
		}
		Optional<User> resultUser=userMapper.findApprovedUserById(id);
		
		return resultUser.get();
	}



	@Override
	public User findApprovedUserByEmail(String email) {
		User user=userMapper.findApprovedUserByEmail(email);
		
		return user;
	}
	
	

	
	
}


