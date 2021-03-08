package com.abinbev.admin.config;

import java.io.IOException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.abinbev.admin.utility.ErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ErrorCodesConfig {

  private static final String ERROR_CODES_JSON_FILE = "error-codes.json";

  @Bean
  public ErrorCodes errorCodes(ObjectMapper objectMapper, Validator validator) throws IOException {
    Resource resource = new ClassPathResource(ERROR_CODES_JSON_FILE);
    ErrorCodes errorCodes = objectMapper.readValue(resource.getInputStream(), ErrorCodes.class);
    Set<ConstraintViolation<ErrorCodes>> validated = validator.validate(errorCodes);
    if (!validated.isEmpty())
      throw new ConstraintViolationException(validated);
    return errorCodes;
  }

}
