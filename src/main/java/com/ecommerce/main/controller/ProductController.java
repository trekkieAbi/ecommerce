package com.ecommerce.main.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.main.dto.ProductDto;
import com.ecommerce.main.dto.UserDto;
import com.ecommerce.main.mapper.UserMapper;
import com.ecommerce.main.model.User;
import com.ecommerce.main.service.ProductService;
import com.ecommerce.main.util.ApiResponse;


@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserMapper userMapper;
	
	@PostMapping("/create")
	@PreAuthorize("hasAuthority('create_product')")
	ResponseEntity<?> createProduct(@RequestBody ProductDto productDto,Principal principal){
	User user=getAuthenticatedUser(principal);
	if(user.getId().equals(productDto.getUserId())) {
		
		Integer resultValue=this.productService.createproduct(productDto,principal);
		if(resultValue>0) {
			return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
		}
	}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/delete/{prodId}")
	@PreAuthorize("hasAuthority('delete_product')")
	ResponseEntity<?> deleteProduct(@PathVariable Integer prodId,Principal principal ){
		Integer resultValue=productService.deleteProduct(prodId,principal);
		if(resultValue>0) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("product deleted successfully!!!",true),HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	
	@GetMapping("/read/{catId}")
	@PreAuthorize("hasAuthority('view_product')")
	ResponseEntity<?> getAllProductByCategory(@PathVariable Integer catId ){
	
		List<ProductDto> productDtos=this.productService.getAllProductByCategory(catId);
		if(!productDtos.isEmpty()) {
			return new ResponseEntity<>(productDtos,HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("there is no categories created by the given user!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasAuthority('update_product')")
	ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto,Principal principal){
		Integer resultValue=this.productService.updateProduct(productDto, principal);
		if(resultValue>0) {
			return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong!!!", false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/read/{prodId}")
	ResponseEntity<?> getProductById(@PathVariable Integer prodId){
		ProductDto productDto=this.productService.getProductById(prodId);
		if(productDto!=null) {
			return new ResponseEntity<ProductDto>(productDto,HttpStatus.FOUND);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("No product found!!",false),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	private  User getAuthenticatedUser(Principal principal) {
		UserDto userDto=new UserDto();
		String userEmail=principal.getName();
		userDto.setEmail(userEmail);
		User user=userMapper.findUserByEmailOrUserName(userDto);
		return user;
	}

}
