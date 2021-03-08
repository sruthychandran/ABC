package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class UserAlreadyExistsException extends AlreadyExistException {
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

}
