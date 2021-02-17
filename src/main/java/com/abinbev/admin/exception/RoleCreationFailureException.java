package com.abinbev.admin.exception;

public class RoleCreationFailureException extends Exception {
	private static final long serialVersionUID = 1L;

	public RoleCreationFailureException(final String message) {
		super(message);
	}
}
