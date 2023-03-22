package com.ecommerce.main.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.main.exception.JwtAuthenticationEntryPoint;
import com.ecommerce.main.filter.JwtTokenAuthorizationFilter;
import com.ecommerce.main.security.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class MyConfig {
	private static final String[] PUBLIC_URLS = { "/user/login", "/user/create" };
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenAuthorizationFilter jwtTokenAuthorizationFilter;
	/*
	 * @Autowired private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	 */

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf().disable().cors().disable().authorizeHttpRequests().antMatchers(PUBLIC_URLS).permitAll()
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtTokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.customUserDetailService);
		authenticationProvider.setPasswordEncoder(this.passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	@Bean
	public ModelMapper getMapper() {
		return  new ModelMapper();
	}

}
