package io.gaecfov.pulsar.console.repository;

import io.gaecfov.pulsar.console.entity.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangqin
 */
public interface InstanceRepository extends JpaRepository<Instance, Long> {

}