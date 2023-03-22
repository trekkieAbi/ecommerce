package com.ecommerce.main.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.main.dto.CategoryDto;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.mapper.UserMapper;
import com.ecommerce.main.model.User;
import com.ecommerce.main.service.CategoryService;
import com.ecommerce.main.util.ApiResponse;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserMapper userMapper;
	
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('create_category')")
	ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto,Authentication authentication){
	User user=getAuthenticatedUser(authentication);
	if(user.getId().equals(categoryDto.getUserId())) {
		
		Integer resultValue=this.categoryService.createCategory(categoryDto);
		if(resultValue>0) {
			return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
		}
	}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/delete/{catId}")
	@PreAuthorize("hasAuthority('delete_category')")
	ResponseEntity<?> deleteCategory(@PathVariable Integer catId,Principal principal ){
	
		String message=this.categoryService.deleteCategory(catId,principal);
		if(!message.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(message,true),HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/read/all/{userId}")
	@PreAuthorize("hasAuthority('read_all_category_by_user')")
	ResponseEntity<?> getAllCategoryByUserId(@PathVariable Integer userId,Authentication authentication ){
			String userName=authentication.getName().toString();
			User user=userMapper.findApprovedUserByEmail(userName);
			if(user.getId().equals(userId)) {
		List<CategoryDto> categories=this.categoryService.getAllCategory(userId);
		if(!categories.isEmpty()) {
			return new ResponseEntity<>(categories,HttpStatus.OK);
		}
			}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/read/{catId}")
	ResponseEntity<?> getCategoryById(@PathVariable Integer catId ){
	
		CategoryDto category=this.categoryService.getCategoryById(catId);
		if(category!=null) {
			return new ResponseEntity<>(category,HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("there is no categories created by the given user!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('update_category')")
	ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto,Principal principal){
		Integer resultValue=this.categoryService.updateCategory(categoryDto, principal);
		if(resultValue>0) {
			return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private  User getAuthenticatedUser(Authentication authentication) {
		UserDto userDto=new UserDto();
		String userEmail=authentication.getPrincipal().toString();
		userDto.setEmail(userEmail);
		User user=userMapper.findUserByEmailOrUserName(userDto);
		return user;
	}

}
