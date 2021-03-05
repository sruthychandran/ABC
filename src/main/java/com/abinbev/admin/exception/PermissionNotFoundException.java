package com.abinbev.admin.exception;

public class PermissionNotFoundException extends Exception implements ErrorCode{

	public PermissionNotFoundException( final String message) {
		super(message);
	}
	@Override
	public String getErrorCode() {
		
		return "10004";
	}
}
