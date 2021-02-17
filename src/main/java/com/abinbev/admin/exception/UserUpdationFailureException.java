package com.abinbev.admin.exception;

public class UserUpdationFailureException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserUpdationFailureException(final String message) {
		super(message);
	}
}
