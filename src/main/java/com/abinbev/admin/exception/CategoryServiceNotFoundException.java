package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class CategoryServiceNotFoundException extends NotFoundException {
	private static final long serialVersionUID = 1L;

	public CategoryServiceNotFoundException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

}
