package com.ecommerce.main;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@ComponentScan({"com.ecommerce.main.config","com.ecommerce.main.security","com.ecommerce.main.filter","com.ecommerce.main.util","com.ecommerce.main.exception","com.ecommerce.main.controller","com.ecommerce.main.service","com.ecommerce.main.mapper","com.ecommerce.main.model"})
@ComponentScan("com.ecommerce.main.*")
@MapperScan("com.ecommerce.main.mapper")
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}


