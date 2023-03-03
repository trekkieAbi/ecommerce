package com.ecommerce.main.service;

import com.ecommerce.main.model.User;

public interface UserService {
	User createUserService(User user);

	Boolean deleteUserService(Integer uId);

	User updateUserService(User user);

	User getUserByEmail(String email);

}
