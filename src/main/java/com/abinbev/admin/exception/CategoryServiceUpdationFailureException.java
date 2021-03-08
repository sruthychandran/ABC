package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class CategoryServiceUpdationFailureException extends UpdationFailureException {
	private static final long serialVersionUID = 1L;

	public CategoryServiceUpdationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

}
