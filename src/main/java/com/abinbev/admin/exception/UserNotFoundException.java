package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

	private static final long serialVersionUID = 1L;

}
