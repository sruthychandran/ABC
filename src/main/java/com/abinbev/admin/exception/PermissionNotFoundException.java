package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class PermissionNotFoundException extends NotFoundException{

	public PermissionNotFoundException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
	
	}

	
}
