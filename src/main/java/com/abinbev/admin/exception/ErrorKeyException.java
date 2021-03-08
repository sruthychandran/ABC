package com.abinbev.admin.exception;

import java.util.Map;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;

import lombok.Getter;

public class ErrorKeyException extends RuntimeException {

  private static final long serialVersionUID = -3208360588008083316L;

  @Getter
  private final ErrorCodeMessage errorCodeMessage;
  
 

  public ErrorKeyException(ErrorCodeMessage errorCodeMessage) {
    super(errorCodeMessage.getErrorMessage());
    this.errorCodeMessage = errorCodeMessage;
  }
  
  
}
