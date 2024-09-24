package io.gaecfov.pulsar.console.repository;

import io.gaecfov.pulsar.console.entity.SubscriptionRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangqin
 */
public interface SubscriptionRelationshipRepository extends
  JpaRepository<SubscriptionRelationship, Long> {

  void deleteByInstanceIdAndTopic(Long instanceId, String topic);
}