package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class RoleCreationFailureException extends SaveFailureException {
	private static final long serialVersionUID = 1L;

	public RoleCreationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
		
	}

}
