package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class RoleNotFoundException extends NotFoundException {
	private static final long serialVersionUID = 1L;

	public RoleNotFoundException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
	}

}
