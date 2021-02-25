package com.abinbev.admin.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

	@Value("${status.create.failure}")
	String cretionFailureStatus;

	@Value("${status.update.failure}")
	String updationFailureStatus;

	@ExceptionHandler(BadRequestAlertException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleBadRequest(final BadRequestAlertException exception,
			final HttpServletRequest request) {

		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());

		return error;
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError handleResourceNotFound(final UserNotFoundException exception,
			final HttpServletRequest request) {

		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());

		return error;
	}

	@ExceptionHandler(RoleNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError handleResourceRoleNotFound(final RoleNotFoundException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());

		return error;
	}

	@ExceptionHandler(CategoryServiceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError handleResourceCategoryNotFound(final CategoryServiceNotFoundException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());

		return error;
	}

	@ExceptionHandler(EmailExistException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public @ResponseBody ApiError handleEmailExist(final EmailExistException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());

		return error;
	}

	@ExceptionHandler(UserCreationFailureException.class)
	public @ResponseBody ApiError handleUserCreationFailure(final UserCreationFailureException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());
		error.setCode(cretionFailureStatus);
		return error;

	}

	@ExceptionHandler(RoleCreationFailureException.class)

	public @ResponseBody ApiError handleRoleCreationFailure(final RoleCreationFailureException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());
		error.setCode(cretionFailureStatus);
		return error;

	}

	@ExceptionHandler(CategoryServiceCreationFailureException.class)

	public @ResponseBody ApiError handleCategoryCreationFailure(final CategoryServiceCreationFailureException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());
		error.setCode(cretionFailureStatus);
		return error;
	}

	@ExceptionHandler(UserUpdationFailureException.class)

	public @ResponseBody ApiError handleUserUpdationFailure(final UserUpdationFailureException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());
		error.setCode(updationFailureStatus);
		return error;
	}

	@ExceptionHandler(RoleUpdationFailureException.class)

	public @ResponseBody ApiError handleRoleUpdationFailure(final RoleUpdationFailureException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());
		error.setCode(updationFailureStatus);
		return error;
	}

	@ExceptionHandler(CategoryServiceUpdationFailureException.class)

	public @ResponseBody ApiError handleRoleUpdationFailure(final CategoryServiceUpdationFailureException exception,
			final HttpServletRequest request) {
		ApiError error = new ApiError();
		error.setMessage(exception.getMessage());
		error.setCode(updationFailureStatus);
		return error;
	}

}
