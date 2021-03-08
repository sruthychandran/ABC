package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class RoleUpdationFailureException extends UpdationFailureException {
	private static final long serialVersionUID = 1L;

	public RoleUpdationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
		// TODO Auto-generated constructor stub
	}

}
