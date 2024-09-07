package io.gaecfov.pulsar.console.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author zhangqin
 */
@Data
public class ClientCertificateCreateRequest {

  @NotEmpty(message = "name cannot be null")
  private String name;
  private String description;
  @NotEmpty(message = "algorithm cannot be null")
  private String algorithm;
  @NotEmpty(message = "certificateType cannot be null")
  private String certificateType;
}