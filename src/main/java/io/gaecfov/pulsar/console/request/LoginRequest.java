package io.gaecfov.pulsar.console.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author zhangqin
 */
@Data
public class LoginRequest {

  @NotEmpty(message = "The username cannot be null")
  private String username;
  @NotEmpty(message = "The password cannot be null")
  private String password;
}
