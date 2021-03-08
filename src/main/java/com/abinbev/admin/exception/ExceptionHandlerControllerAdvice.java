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
import com.abinbev.admin.utility.ErrorResponse;



@ControllerAdvice
public class ExceptionHandlerControllerAdvice /* extends ResponseEntityExceptionHandler */{

	/*
	 * @Value("${status.create.failure}") String cretionFailureStatus;
	 * 
	 * @Value("${status.update.failure}") String updationFailureStatus;
	 */
	@Autowired
	  private ErrorCodes errorCodes;
	
	
	 @ExceptionHandler({ErrorKeyException.class})
	  public ResponseEntity<ErrorResponse> runtimeException(ErrorKeyException errorKeyException) {

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

	    ErrorResponse errorResponse =
	            ErrorResponse.create(message, error, errorCodeMessage.getErrorCode(), httpStatus.value());
	        return ResponseEntity.status(httpStatus).body(errorResponse);
	
	  }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

		/*
		 * @ExceptionHandler(BadRequestAlertException.class)
		 * 
		 * @ResponseStatus(value = HttpStatus.BAD_REQUEST) public @ResponseBody ApiError
		 * handleBadRequest(final BadRequestAlertException exception, final
		 * HttpServletRequest request) {
		 * 
		 * ApiError error = new ApiError(); error.setMessage(exception.getMessage());
		 * 
		 * return error; }
		 * 
		 * @ExceptionHandler(UserNotFoundException.class)
		 * 
		 * @ResponseStatus(value = HttpStatus.NOT_FOUND) public @ResponseBody ApiError
		 * handleUserNotFound(final UserNotFoundException exception, final
		 * HttpServletRequest request) {
		 * 
		 * ApiError error = new ApiError();
		 * 
		 * error.setMessage(exception.getMessage());
		 * //error.setCode(exception.getErrorCode()); return error; }
		 * 
		 * @ExceptionHandler(RoleNotFoundException.class)
		 * 
		 * @ResponseStatus(value = HttpStatus.NOT_FOUND) public @ResponseBody ApiError
		 * handleRoleNotFound(final RoleNotFoundException exception, final
		 * HttpServletRequest request) { ApiError error = new ApiError();
		 * 
		 * error.setMessage(exception.getMessage());
		 * //error.setCode(exception.getErrorCode());
		 * 
		 * return error; }
		 * 
		 * @ExceptionHandler(CategoryServiceNotFoundException.class)
		 * 
		 * @ResponseStatus(value = HttpStatus.NOT_FOUND) public @ResponseBody ApiError
		 * handleCategoryNotFound(final CategoryServiceNotFoundException exception,
		 * final HttpServletRequest request) { ApiError error = new ApiError();
		 * 
		 * error.setMessage(exception.getMessage()); //
		 * error.setCode(exception.getErrorCode()); return error; }
		 * 
		 * @ExceptionHandler(PermissionNotFoundException.class)
		 * 
		 * @ResponseStatus(value = HttpStatus.NOT_FOUND) public @ResponseBody ApiError
		 * handleRoleNotFound(final PermissionNotFoundException exception, final
		 * HttpServletRequest request) { ApiError error = new ApiError();
		 * 
		 * error.setMessage(exception.getMessage());
		 * //error.setCode(exception.getErrorCode());
		 * 
		 * return error; }
		 * 
		 * 
		 * 
		 * @ExceptionHandler(EmailExistException.class)
		 * 
		 * @ResponseStatus(value = HttpStatus.CONFLICT) public @ResponseBody ApiError
		 * handleEmailExist(final EmailExistException exception, final
		 * HttpServletRequest request) { ApiError error = new ApiError();
		 * error.setMessage(exception.getMessage());
		 * 
		 * return error; }
		 * 
		 * @ExceptionHandler(UserCreationFailureException.class) public @ResponseBody
		 * ApiError handleUserCreationFailure(final UserCreationFailureException
		 * exception, final HttpServletRequest request) { ApiError error = new
		 * ApiError(); error.setMessage(exception.getMessage());
		 * error.setCode(cretionFailureStatus); return error;
		 * 
		 * }
		 * 
		 * @ExceptionHandler(RoleCreationFailureException.class)
		 * 
		 * public @ResponseBody ApiError handleRoleCreationFailure(final
		 * RoleCreationFailureException exception, final HttpServletRequest request) {
		 * ApiError error = new ApiError(); error.setMessage(exception.getMessage());
		 * error.setCode(cretionFailureStatus); return error;
		 * 
		 * }
		 * 
		 * @ExceptionHandler(CategoryServiceCreationFailureException.class)
		 * 
		 * public @ResponseBody ApiError handleCategoryCreationFailure(final
		 * CategoryServiceCreationFailureException exception, final HttpServletRequest
		 * request) { ApiError error = new ApiError();
		 * error.setMessage(exception.getMessage());
		 * error.setCode(cretionFailureStatus); return error; }
		 * 
		 * @ExceptionHandler(UserUpdationFailureException.class)
		 * 
		 * public @ResponseBody ApiError handleUserUpdationFailure(final
		 * UserUpdationFailureException exception, final HttpServletRequest request) {
		 * ApiError error = new ApiError(); error.setMessage(exception.getMessage());
		 * error.setCode(updationFailureStatus); return error; }
		 * 
		 * @ExceptionHandler(RoleUpdationFailureException.class)
		 * 
		 * public @ResponseBody ApiError handleRoleUpdationFailure(final
		 * RoleUpdationFailureException exception, final HttpServletRequest request) {
		 * ApiError error = new ApiError(); error.setMessage(exception.getMessage());
		 * error.setCode(updationFailureStatus); return error; }
		 * 
		 * @ExceptionHandler(CategoryServiceUpdationFailureException.class)
		 * 
		 * public @ResponseBody ApiError handleCategoryUpdationFailure(final
		 * CategoryServiceUpdationFailureException exception, final HttpServletRequest
		 * request) { ApiError error = new ApiError();
		 * error.setMessage(exception.getMessage());
		 * error.setCode(updationFailureStatus); return error; }
		 */

}
