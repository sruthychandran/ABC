package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class CategoryServiceNotFoundException extends NotFoundException {
	public CategoryServiceNotFoundException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

}
