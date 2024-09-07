package io.gaecfov.pulsar.console.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
import lombok.Data;

/**
 * @author zhangqin
 */
@Data
public class ClientTokenCreateRequest {

  @NotEmpty(message = "name cannot be null")
  private String name;
  private String description;
  @NotEmpty(message = "client certificate cannot be null")
  private Long clientCertificateId;
  @NotEmpty(message = "role cannot be null")
  private String role;
  private Date expiration;
}