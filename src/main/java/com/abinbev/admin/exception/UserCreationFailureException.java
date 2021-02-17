package com.abinbev.admin.exception;

public class UserCreationFailureException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserCreationFailureException(final String message) {
		super(message);
	}
}
