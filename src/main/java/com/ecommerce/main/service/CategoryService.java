package com.ecommerce.main.service;

import java.security.Principal;
import java.util.List;

import com.ecommerce.main.dto.CategoryDto;

public interface CategoryService {
	Integer createCategory(CategoryDto categoryDto);
	String deleteCategory(Integer id,Principal principal);
	List<CategoryDto> getAllCategory(Integer userId);
	CategoryDto getCategoryById(Integer id);
	Integer updateCategory(CategoryDto categoryDto,Principal principal);
	
	
	

}
