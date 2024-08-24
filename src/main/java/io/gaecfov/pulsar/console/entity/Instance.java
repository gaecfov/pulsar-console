package io.gaecfov.pulsar.console.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

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
  private String name;
  private String serviceUrl;
  private String webServiceUrl;
}