package io.gaecfov.pulsar.console.repository;

import io.gaecfov.pulsar.console.entity.ClientCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangqin
 */
public interface ClientCertificateRepository extends JpaRepository<ClientCertificate, Long> {

}