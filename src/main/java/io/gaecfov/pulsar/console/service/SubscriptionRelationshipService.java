package io.gaecfov.pulsar.console.service;

import io.gaecfov.pulsar.console.entity.SubscriptionRelationship;
import io.gaecfov.pulsar.console.repository.SubscriptionRelationshipRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author zhangqin
 */
@Service
public class SubscriptionRelationshipService {


  private final SubscriptionRelationshipRepository subscriptionRelationshipRepository;

  public SubscriptionRelationshipService(
    SubscriptionRelationshipRepository subscriptionRelationshipRepository) {
    this.subscriptionRelationshipRepository = subscriptionRelationshipRepository;
  }

  @Transactional
  public void deleteByInstanceIdAndTopic(Long instanceId, String topic) {
    subscriptionRelationshipRepository.deleteByInstanceIdAndTopic(instanceId, topic);
  }

  public void saveAll(List<SubscriptionRelationship> list) {
    subscriptionRelationshipRepository.saveAll(list);
  }
}
