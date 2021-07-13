package com.systemlog.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systemlog.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	// Tentativa de autenticação com spring security!
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			CredenciaisDTO credentials = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>());
			
			Authentication auth = authenticationManager.authenticate(authToken);
			
			return auth;
			
//			return getAuthenticationManager().authenticate(
//					new UsernamePasswordAuthenticationToken(
//							credentials.getEmail(), 
//							credentials.getPassword(), 
//							new ArrayList<>()
//							)
//					);
//			
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication auth) throws IOException, ServletException {
		String username = ((UserSpringSecurity) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		response.addHeader("Autorization", "Bearer " + token);

	}

}
