package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class PermissionUpdationFailureException extends UpdationFailureException {
	private static final long serialVersionUID = 1L;

	public PermissionUpdationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

}
