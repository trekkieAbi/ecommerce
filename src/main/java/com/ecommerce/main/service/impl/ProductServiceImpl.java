package com.ecommerce.main.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.main.dto.ProductDto;
import com.ecommerce.main.mapper.CategoryMapper;
import com.ecommerce.main.mapper.ProductMapper;
import com.ecommerce.main.mapper.UserMapper;
import com.ecommerce.main.model.Category;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.model.User;
import com.ecommerce.main.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Integer createproduct(ProductDto productDto, Principal principal) {
		Integer resultValue = 0;
		Optional<User> retrievedUser = userMapper.findApprovedUserById(productDto.getUserId());
		if (!retrievedUser.get().equals(null)) {
			Optional<Category> retrievedCategory = categoryMapper.findById(productDto.getCategoryId());
			if (!retrievedCategory.isEmpty()) {
				if (retrievedCategory.get().getUserId().equals(retrievedUser.get().getId())) {
					ArrayList<Category> categories = categoryMapper.getAllCategoryByUser(productDto.getUserId());
					if (!categories.isEmpty()) {
						for (Category category : categories) {
							if (category.getId().equals(retrievedCategory.get().getId())) {
								ArrayList<Product> products = productMapper
										.getAllProductByCategory(retrievedCategory.get().getId());
								if (!products.isEmpty()) {
									for (Product product : products) {
										if (product.getName().contentEquals(productDto.getName())) {
											return resultValue;
										}
									}
								}
								Product product = new Product();
								product.setName(productDto.getName());
								product.setDescription(productDto.getDescription());
								product.setPrice(productDto.getPrice());
								product.setCategoryId(productDto.getCategoryId());
								product.setUserId(productDto.getUserId());

								resultValue = this.productMapper.saveProduct(product);
								return resultValue;

							}

						}
					}
				}
			}

		}
		return resultValue;

	}

	@Override
	public Integer deleteProduct(Integer productId, Principal principal) {
		Integer resultValue = 0;
		Optional<Product> retrievedProduct = productMapper.findById(productId);
		User user = userMapper.findApprovedUserByEmail(principal.getName());
		if (user.getId().equals(retrievedProduct.get().getUserId())) {
			if (!retrievedProduct.equals(null)) {
				resultValue = productMapper.deleteProduct(retrievedProduct.get());

			} else {
				return resultValue;
			}
		}
		return resultValue;
	}

	@Override
	public List<ProductDto> getAllProductByCategory(Integer categoryId) {
		ArrayList<Product> productDtos = this.productMapper.getAllProductByCategory(categoryId);
		if (productDtos.isEmpty()) {
			return null;
		}
		return productDtos.stream().map(product -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public ProductDto getProductById(Integer id) {
		Optional<Product> retrievedProduct = this.productMapper.findById(id);
		if(retrievedProduct.isEmpty()) {
			return null;
		}

		return this.modelMapper.map(retrievedProduct, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProductByUser(Integer userId) {
		Optional<User> retrievedUser = userMapper.findApprovedUserById(userId);
		if (retrievedUser.get().equals(null)) {
			return null;
		}
		ArrayList<Product> products = this.productMapper.getAllProductByUser(retrievedUser.get());
		return products.stream().map(product -> this.modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Integer updateProduct(ProductDto productDto, Principal principal) {
		Integer resultValue = 0;
		Optional<Product> retrievedProduct = productMapper.findById(productDto.getId());
		if ((retrievedProduct.isEmpty()) && (!(userMapper.findApprovedUserByEmail(principal.getName()).getId()
				.equals(productDto.getUserId())))) {
			return resultValue;
		}

		retrievedProduct.get().setDescription(productDto.getDescription());
		retrievedProduct.get().setName(productDto.getName());
		retrievedProduct.get().setPrice(productDto.getPrice());
		if (!productDto.getCategoryId().equals(retrievedProduct.get().getCategoryId())) {
			ArrayList<Category> categories = categoryMapper.getAllCategoryByUser(retrievedProduct.get().getUserId());
			if (!categories.isEmpty()) {
				OuterLoop: for (Category category : categories) {
					if (category.getId().equals(productDto.getCategoryId())) {
						retrievedProduct.get().setCategoryId(category.getId());
						break OuterLoop;

					}
				}
			} else {
				return resultValue;
			}
		}
		resultValue = productMapper.updateProduct(retrievedProduct.get());

		return resultValue;
	}

}
