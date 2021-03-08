package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class UserUpdationFailureException extends UpdationFailureException {
	private static final long serialVersionUID = 1L;

	public UserUpdationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

}
