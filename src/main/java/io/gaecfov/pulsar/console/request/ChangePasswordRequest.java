package io.gaecfov.pulsar.console.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author zhangqin
 */
@Data
public class ChangePasswordRequest {

  @NotEmpty(message = "The original password cannot be null")
  private String oldPassword;

  @NotEmpty(message = "The new password cannot be null")
  private String newPassword;
}
