package com.abinbev.admin.exception;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(final String message) {
		super(message);
	}
}
