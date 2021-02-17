package com.abinbev.admin.exception;

public class EmailExistException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmailExistException(final String message) {
		super(message);
	}
}
