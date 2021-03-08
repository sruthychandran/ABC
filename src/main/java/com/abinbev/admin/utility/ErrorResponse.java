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
public class ErrorResponse {


	//private Date timestamp;

	  private int status;

	  private String error;

	  private String message;

	  private String errorKey;

	//  private Map<String, Object> details;

	  public static ErrorResponse create(String message, String error, String errorKey,
	      int httpStatus) {
	    ErrorResponse errorResponse = new ErrorResponse();
	    errorResponse.setMessage(message);
	    errorResponse.setError(error);
	    errorResponse.setErrorKey(errorKey);
	    errorResponse.setStatus(httpStatus);
	   // errorResponse.setTimestamp(new Date());
	    return errorResponse;
	  }

}
