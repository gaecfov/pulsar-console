package io.gaecfov.pulsar.console.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @author zhangqin
 */
@Entity(name = "pc_user")
@Data
public class User implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty(message = "username cannot be null")
  private String username;
  @NotEmpty(message = "password cannot be null")
  private String password;
  @NotEmpty(message = "role cannot be null")
  private String role;
  @CreationTimestamp
  private LocalDateTime createdAt;
}