package com.abinbev.admin.exception;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotFoundException(final String message) {
		super(message);
	}
}
