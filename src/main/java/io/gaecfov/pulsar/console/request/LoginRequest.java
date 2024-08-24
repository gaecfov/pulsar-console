package io.gaecfov.pulsar.console.request;

import lombok.Data;

/**
 * @author zhangqin
 */
@Data
public class LoginRequest {

  private String username;
  private String password;
}
