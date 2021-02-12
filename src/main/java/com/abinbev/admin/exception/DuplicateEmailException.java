package com.abinbev.admin.exception;

public class DuplicateEmailException extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicateEmailException(final String message) {
		super(message);
	}
}
