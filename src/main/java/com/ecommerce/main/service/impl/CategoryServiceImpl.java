package com.ecommerce.main.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.main.dto.CategoryDto;
import com.ecommerce.main.exception.ResourceNotFoundException;
import com.ecommerce.main.mapper.CategoryMapper;
import com.ecommerce.main.mapper.UserMapper;
import com.ecommerce.main.model.Category;
import com.ecommerce.main.model.User;
import com.ecommerce.main.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ModelMapper modelMapper;
	
	

	@Override
	public Integer createCategory(CategoryDto categoryDto)  {
		Integer returnValue=0;
		
		Category newCategory=new Category();
		newCategory.setName(categoryDto.getName());
		newCategory.setDescription(categoryDto.getDescription());
		newCategory.setUserId(categoryDto.getUserId());
		Optional<User> user=userMapper.findApprovedUserById(newCategory.getUserId());
		if(user.get().equals(null)) {
			return returnValue;
		}
		else {
		List<Category> categories=categoryMapper.getAllCategoryByUser(newCategory.getUserId());
		for (Category category2 : categories) {
			if(category2.getName().equalsIgnoreCase(newCategory.getName())) {
				return  returnValue;
			}
		}
		returnValue=categoryMapper.saveCategory(newCategory);
		}
		
		return returnValue;
	}

	@Override
	public String deleteCategory(Integer id,Principal principal) {
		String message="";
		Optional<Category> category=categoryMapper.findById(id);
		if(!category.isEmpty()) {
			String userName=principal.getName();
			User user=userMapper.findApprovedUserByEmail(userName);
			if(user.getId().equals(category.get().getUserId())) {
			Integer returnValue=this.categoryMapper.deleteCategory(category.get());
			if(returnValue>0) {
			message="category deleted successfully!!";
			}
			}
		}
		return message;
	}

	@Override
	public List<CategoryDto> getAllCategory(Integer userId) {
		Optional<User> user=userMapper.findApprovedUserById(userId);
		if(user.isEmpty()) {
			throw new ResourceNotFoundException("there is no user with the id "+userId);
		}
		ArrayList<Category> categories=categoryMapper.getAllCategoryByUser(userId);
		
		return categories.stream().map(category->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> category=categoryMapper.findById(id);
		CategoryDto categoryDto=this.modelMapper.map(category.get(), CategoryDto.class);
		return categoryDto;
	}

	@Override
	public Integer updateCategory(CategoryDto categoryDto,Principal principal) {
		Optional<User>  retrievedUser=userMapper.findApprovedUserById(categoryDto.getUserId());
		Integer resultValue=0;
		Optional<Category> category=this.categoryMapper.findById(categoryDto.getId());
		String username=principal.getName();
		User user=userMapper.findApprovedUserByEmail(username);
		if(user.getId().equals(categoryDto.getUserId())) {
		if((!category.isEmpty())&&(retrievedUser.get().getId().equals(category.get().getUserId()))) {
			ArrayList<Category> categories=categoryMapper.getAllCategoryByUser(user.getId());
			List<String> categoriesName=new ArrayList<String>();
			
			outerLoop:for (Category category2 : categories) {
				if(category2.getId().equals(categoryDto.getId())) {
					break outerLoop;
				}
				categoriesName.add(category2.getName());
				
			}
			if((!categoriesName.isEmpty())&&categoriesName.contains(categoryDto.getName())) {
				return resultValue;
			}
		Category retrievedCategory=category.get();
		
			retrievedCategory.setName(categoryDto.getName());

		
			retrievedCategory.setDescription(categoryDto.getDescription());
		
		resultValue=categoryMapper.updateCategory(retrievedCategory);
		}
		}
		
		
		return resultValue;
	}
	
	

}
