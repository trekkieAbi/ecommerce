package com.ecommerce.main.service;

import java.security.Principal;
import java.util.List;


import com.ecommerce.main.dto.ProductDto;

public interface ProductService {
	Integer createproduct(ProductDto productDto,Principal principal);
	Integer deleteProduct(Integer productId,Principal principal);
	
	List<ProductDto> getAllProductByCategory(Integer categoryId);
	ProductDto getProductById(Integer id);
	List<ProductDto>getAllProductByUser(Integer userId);
	Integer updateProduct(ProductDto productDto,Principal principal);
	

}
