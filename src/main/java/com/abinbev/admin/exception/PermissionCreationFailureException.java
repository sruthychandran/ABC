package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class PermissionCreationFailureException extends SaveFailureException {
	private static final long serialVersionUID = 1L;
	public PermissionCreationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
	
	}

	

}
