package com.abinbev.admin.exception;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

public class CategoryRoleMappingCreationFailureException extends SaveFailureException {
	private static final long serialVersionUID = 1L;
	public CategoryRoleMappingCreationFailureException(ErrorCodeMessage errorCodeMessage) {
		super(errorCodeMessage);
	
	}

	

}
