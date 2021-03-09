package com.abinbev.admin.utility;

import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionErrorResponse {


	//private Date timestamp;

	  private int status;

	  private String error;

	  private String message;

	  private String errorKey;



	  public static ExceptionErrorResponse create(String message, String error, String errorKey,
	      int httpStatus) {
	    ExceptionErrorResponse errorResponse = new ExceptionErrorResponse();
	    errorResponse.setMessage(message);
	    errorResponse.setError(error);
	    errorResponse.setErrorKey(errorKey);
	    errorResponse.setStatus(httpStatus);
	   // errorResponse.setTimestamp(new Date());
	    return errorResponse;
	  }

}
