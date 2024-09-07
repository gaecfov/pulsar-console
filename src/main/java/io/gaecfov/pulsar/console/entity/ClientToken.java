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
import java.util.Date;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author zhangqin
 */

@Entity(name = "pc_client_token")
@Data
public class ClientToken implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty(message = "name cannot be null")
  private String name;
  private String description;
  @NotEmpty(message = "token cannot be null")
  @Lob
  @Column(columnDefinition = "TEXT")
  private String token;
  private Date expiration;
  private Long clientCertificateId;
  @CreationTimestamp
  private LocalDateTime createdAt;
}