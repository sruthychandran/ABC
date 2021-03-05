package com.abinbev.admin.exception;

import org.springframework.beans.factory.annotation.Value;

public class RoleNotFoundException extends Exception implements ErrorCode {
	private static final long serialVersionUID = 1L;
	
	

	public RoleNotFoundException(final String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		
		return "10003";
	}
}
