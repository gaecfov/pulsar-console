package io.gaecfov.pulsar.console.entity;

import jakarta.persistence.Column;
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
@Entity(name = "pc_instance")
@Data
public class Instance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty(message = "instance name cannot be null")
  private String name;
  private String serviceUrl;
  @NotEmpty(message = "instance web service url cannot be null")
  private String webServiceUrl;

  //Authentication
  private boolean authenticationEnabled;
  @Column(length = 500)
  private String authenticationToken;

  //TLS
  private boolean tlsEnabled;
  private String tlsType;
  //PEM
  @Column(columnDefinition = "TEXT")
  private String tlsCert;
  @Column(columnDefinition = "TEXT")
  private String tlsKey;
  @Column(columnDefinition = "TEXT")
  private String tlsTrustCert;
  //JKS
  private String tlsKeyStoreType;
  @Column(columnDefinition = "TEXT")
  private String tlsKeyStore;
  private String tlsKeyStorePassword;
  private String tlsTrustStoreType;
  @Column(columnDefinition = "TEXT")
  private String tlsTrustStore;
  private String tlsTrustStorePassword;
  @CreationTimestamp
  private LocalDateTime createdAt;
}