package io.gaecfov.pulsar.console.response;

import lombok.Data;

/**
 * @author zhangqin
 */
@Data
public class CurrentUser {

  private Long userId;
  private String username;
  private String role;
}
