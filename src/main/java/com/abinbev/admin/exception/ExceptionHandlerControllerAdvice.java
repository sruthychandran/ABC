package com.abinbev.admin.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.abinbev.admin.utility.ErrorCodes;
import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;
import com.abinbev.admin.utility.ExceptionErrorResponse;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice /* extends ResponseEntityExceptionHandler */ {

	@Autowired
	private ErrorCodes errorCodes;

	@ExceptionHandler({ ErrorKeyException.class })
	public ResponseEntity<ExceptionErrorResponse> runtimeException(ErrorKeyException errorKeyException) {

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		String error = "Internal Server Error";
		ErrorCodeMessage errorCodeMessage = errorKeyException.getErrorCodeMessage();
		if (errorKeyException instanceof NotFoundException) {
			httpStatus = HttpStatus.NOT_FOUND;
			error = "Not Found";
		} else if (errorKeyException instanceof SaveFailureException) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			error = "Save Failure";
		} else if (errorKeyException instanceof UpdationFailureException) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			error = "Update Failure";
		} else if (errorKeyException instanceof AlreadyExistException) {
			httpStatus = HttpStatus.CONFLICT;
			error = "Already Exist";
		} else if (errorKeyException instanceof BadRequestAlertException) {
			httpStatus = HttpStatus.BAD_REQUEST;
			error = "Bad Request";
		}
		String message = errorCodeMessage.getErrorMessage();
		if (message == null || message.isEmpty())
			message = "No message available";

		ExceptionErrorResponse errorResponse = ExceptionErrorResponse.create(message, error, errorCodeMessage.getErrorCode(),
				httpStatus.value());
		return ResponseEntity.status(httpStatus).body(errorResponse);

	}

}
