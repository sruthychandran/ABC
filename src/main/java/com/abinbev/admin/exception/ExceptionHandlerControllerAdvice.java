package com.abinbev.admin.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

	
	
	@ExceptionHandler(BadRequestAlertException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError handleBadRequest(final BadRequestAlertException exception,
			final HttpServletRequest request) {

		/*
		 * ApiError error = new ApiError(); error.setMessage(exception.getMessage());
		 */
		
		return  setExceptionModel(exception);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError  handleResourceNotFound(final UserNotFoundException exception,
			final HttpServletRequest request) {

		/*
		 * ApiError error = new ApiError(); error.setMessage(exception.getMessage());
		 */
		return  setExceptionModel(exception);
	}

	
	@ExceptionHandler(EmailExistException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError  handleEmailExist(final EmailExistException exception,
			final HttpServletRequest request) {

		/*
		 * ApiError error = new ApiError(); error.setMessage(exception.getMessage());
		 * setExceptionModel(exception);
		 */
		return setExceptionModel(exception);
	}
	 
	private ApiError setExceptionModel(Exception ex) {
		ApiError error = new ApiError();
		error.setMessage(ex.getMessage());
		return error;
	}







}
