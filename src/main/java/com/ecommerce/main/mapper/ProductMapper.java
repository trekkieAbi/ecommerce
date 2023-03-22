package com.ecommerce.main.mapper;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;

@Mapper
public interface ProductMapper {
	Integer saveProduct(Product product);
	Integer deleteProduct(Product product);
	Integer updateProduct(Product product);
	Optional<Product> findById(Integer prodId);
	ArrayList<Product> getAllProductByUser(User user);
	ArrayList<Product> getAllProductByCategory(Integer catId);
	

}
