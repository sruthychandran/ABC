package com.abinbev.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.abinbev.admin.utility.ErrorCodes.ErrorCodeMessage;


@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends ErrorKeyException {

  private static final long serialVersionUID = -4414200396495084352L;

  public NotFoundException(ErrorCodeMessage errorCodeMessage) {
    super(errorCodeMessage);
  }

}
