package com.abinbev.admin.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestAlertException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleBadRequest(final BadRequestAlertException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError handleResourceNotFound(final UserNotFoundException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError handleResourceRoleNotFound(final RoleNotFoundException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(CategoryServiceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError handleResourceCategoryNotFound(final CategoryServiceNotFoundException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(EmailExistException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError handleEmailExist(final EmailExistException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(UserCreationFailureException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleUserCreationFailure(final UserCreationFailureException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(RoleCreationFailureException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleRoleCreationFailure(final RoleCreationFailureException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(CategoryServiceCreationFailureException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleCategoryCreationFailure(final CategoryServiceCreationFailureException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(UserUpdationFailureException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleUserUpdationFailure(final UserUpdationFailureException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(RoleUpdationFailureException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleRoleUpdationFailure(final RoleUpdationFailureException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	@ExceptionHandler(CategoryServiceUpdationFailureException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleRoleUpdationFailure(final CategoryServiceUpdationFailureException exception,
			final HttpServletRequest request) {

		return setExceptionModel(exception);
	}

	private ApiError setExceptionModel(Exception ex) {
		ApiError error = new ApiError();
		error.setMessage(ex.getMessage());
		return error;
	}

}
