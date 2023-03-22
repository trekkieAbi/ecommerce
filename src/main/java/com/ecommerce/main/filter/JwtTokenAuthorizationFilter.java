package com.ecommerce.main.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.main.security.CustomUserDetailService;
import com.ecommerce.main.util.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.setStatus(HttpStatus.OK.value());
		}
		else {
			String requestToken=request.getHeader("Authorization");
			if(requestToken!=null && requestToken.startsWith("Bearer"));
			String userName=null;
			String jwtToken="null";
			if(requestToken!=null && requestToken.startsWith("Bearer ")) {
				jwtToken=requestToken.substring(7);
				try {
                    userName = this.jwtHelper.getUserNameFromToken(jwtToken);

					
				}catch(ExpiredJwtException e) {
					e.printStackTrace();
					
				}
			}
			else {
				System.out.println("Invalid token, not start with bearer string");
			}
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				final UserDetails userDetails=this.customUserDetailService.loadUserByUsername(userName);
				if(userDetails!=null && this.jwtHelper.validateToken(jwtToken, userDetails.getUsername())) {
					List<GrantedAuthority> authorities=jwtHelper.getAuthoritiesClaimsFromToken(jwtToken);
					Authentication authenticationToken=jwtHelper.getAuthentication(userName, authorities, request);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}else {
				SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
			
		}
	}

}
