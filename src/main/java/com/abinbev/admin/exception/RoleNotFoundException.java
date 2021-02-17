package com.abinbev.admin.exception;

public class RoleNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(final String message) {
		super(message);
	}
}
