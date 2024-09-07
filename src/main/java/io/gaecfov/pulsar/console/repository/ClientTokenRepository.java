package io.gaecfov.pulsar.console.repository;

import io.gaecfov.pulsar.console.entity.ClientToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangqin
 */
public interface ClientTokenRepository extends JpaRepository<ClientToken, Long> {

}