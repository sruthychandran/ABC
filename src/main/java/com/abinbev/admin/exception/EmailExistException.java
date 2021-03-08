package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class EmailExistException extends AlreadyExistException{
	private static final long serialVersionUID = 1L;
	public EmailExistException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
		// TODO Auto-generated constructor stub
	}

	

	
}
