package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class UserCreationFailureException extends SaveFailureException {
	private static final long serialVersionUID = 1L;

	public UserCreationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

}
