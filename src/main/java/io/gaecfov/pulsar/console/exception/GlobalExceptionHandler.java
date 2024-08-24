package io.gaecfov.pulsar.console.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zhangqin
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = BadCredentialsException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String badCredentialsExceptionHandler(BadCredentialsException e) {
    return e.getMessage();
  }
}
