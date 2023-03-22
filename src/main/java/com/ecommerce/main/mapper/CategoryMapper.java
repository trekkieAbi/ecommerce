package com.ecommerce.main.mapper;

import java.util.ArrayList;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import com.ecommerce.main.model.Category;

@Mapper
public interface CategoryMapper {
	Integer saveCategory(Category category);
	Integer updateCategory(Category category);
	Integer deleteCategory(Category category);
	Optional<Category> findById(Integer catId);
	ArrayList<Category> getAllCategoryByUser(Integer uId);

}
