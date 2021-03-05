package com.abinbev.admin.exception;

public class UserNotFoundException extends Exception implements ErrorCode{
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(final String message) {
		super(message);
	}
	@Override
	public String getErrorCode() {
		
		return "10005";
	}
}
