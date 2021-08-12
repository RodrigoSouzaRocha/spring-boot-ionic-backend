package com.systemlog.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.systemlog.security.UserSpringSecurity;

public class UserService {

	public static UserSpringSecurity authenticated () {
		
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
