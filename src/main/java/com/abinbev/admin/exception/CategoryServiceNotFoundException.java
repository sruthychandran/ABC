package com.abinbev.admin.exception;

public class CategoryServiceNotFoundException extends Exception  implements ErrorCode{
	private static final long serialVersionUID = 1L;

	public CategoryServiceNotFoundException(final String message) {
		super(message);
	}
	@Override
	public String getErrorCode() {
		
		return "10006";
	}
}
