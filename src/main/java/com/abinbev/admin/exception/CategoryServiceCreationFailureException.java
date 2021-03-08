package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class CategoryServiceCreationFailureException extends SaveFailureException {
	private static final long serialVersionUID = 1L;

	public CategoryServiceCreationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
		
	}

}
