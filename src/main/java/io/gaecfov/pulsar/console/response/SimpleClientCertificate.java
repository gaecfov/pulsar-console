package io.gaecfov.pulsar.console.response;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zhangqin
 */
@Data
public class SimpleClientCertificate {

  private Long id;
  private String name;
  private String description;
  private String algorithm;
  private String certificateType;
  private LocalDateTime createdAt;
}
