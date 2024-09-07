package io.gaecfov.pulsar.console.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author zhangqin
 */

@Entity(name = "pc_client_certificate")
@Data
public class ClientCertificate implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty(message = "name cannot be null")
  private String name;
  private String description;
  @NotEmpty(message = "algorithm cannot be null")
  private String algorithm;
  @NotEmpty(message = "certificateType cannot be null")
  private String certificateType;
  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] privateKey;
  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] publicKey;
  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] secretKey;
  @CreationTimestamp
  private LocalDateTime createdAt;
}