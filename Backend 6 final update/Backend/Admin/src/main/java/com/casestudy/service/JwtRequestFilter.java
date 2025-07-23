package com.casestudy.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;


@Service
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserInfoService userservice;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authorizationHeader= request.getHeader("Authorization");
		
		String username=null;
		String jwt =null;
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt= authorizationHeader.substring(7);
			username=jwtUtil.extractUsername(jwt);
			}
		if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails currentUserDetails=userservice.loadUserByUsername(username);
			Boolean token= jwtUtil.validateToken(jwt, currentUserDetails);
			if (token) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
					new UsernamePasswordAuthenticationToken(currentUserDetails, null, currentUserDetails.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
					.buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}