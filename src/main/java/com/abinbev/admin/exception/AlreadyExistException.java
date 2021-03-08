package com.abinbev.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;


@ResponseStatus(code = HttpStatus.CONFLICT)
public class AlreadyExistException extends ErrorKeyException {

  private static final long serialVersionUID = -4414200396495084352L;

  public AlreadyExistException(ErrorCodeMessage errorCodeMessage) {
    super(errorCodeMessage);
  }

}
