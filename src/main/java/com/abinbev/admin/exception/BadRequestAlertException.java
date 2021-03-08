package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class BadRequestAlertException extends ErrorKeyException {

	private static final long serialVersionUID = 1L;

	public BadRequestAlertException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);

	}

}
