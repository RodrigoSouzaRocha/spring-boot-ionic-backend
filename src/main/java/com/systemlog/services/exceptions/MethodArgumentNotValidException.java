package com.systemlog.services.exceptions;

public class MethodArgumentNotValidException extends RuntimeException {
	private static final long serialVersionUID = 1l;
	
	public MethodArgumentNotValidException(String msg) {
		super(msg);
	}
	
	public MethodArgumentNotValidException(String msg, Throwable cause) {
		super (msg, cause);
		
	}	

}
