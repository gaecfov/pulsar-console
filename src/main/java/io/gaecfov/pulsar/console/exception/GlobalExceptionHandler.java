package io.gaecfov.pulsar.console.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
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


  @ExceptionHandler(value = BindException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String badCredentialsExceptionHandler(BindException exception) {
    List<ObjectError> allErrors = exception.getAllErrors();
    StringBuilder stringBuilder = new StringBuilder();
    for (ObjectError error : allErrors) {
      stringBuilder.append(error.getDefaultMessage()).append("\n");
    }
    return stringBuilder.toString();
  }

  @ExceptionHandler(value = SystemRuntimeException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String systemRuntimeExceptionHandler(SystemRuntimeException e) {
    return e.getMessage();
  }

}
