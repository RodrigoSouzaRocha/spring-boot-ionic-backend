package com.systemlog.services.exceptions;

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1l;
	
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super (msg, cause);
		
	}	

}
