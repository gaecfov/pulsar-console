package io.gaecfov.pulsar.console.controller;

import io.gaecfov.pulsar.console.entity.SubscriptionRelationship;
import io.gaecfov.pulsar.console.repository.SubscriptionRelationshipRepository;
import io.gaecfov.pulsar.console.service.SubscriptionRelationshipService;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqin
 */
@RestController
@RequestMapping("/subscription-relationship")
public class SubscriptionRelationshipController {

  private final SubscriptionRelationshipService subscriptionRelationshipService;
  private final SubscriptionRelationshipRepository subscriptionRelationshipRepository;

  public SubscriptionRelationshipController(
    SubscriptionRelationshipService subscriptionRelationshipService,
    SubscriptionRelationshipRepository subscriptionRelationshipRepository) {
    this.subscriptionRelationshipService = subscriptionRelationshipService;
    this.subscriptionRelationshipRepository = subscriptionRelationshipRepository;
  }

  @PostMapping("/saveAll")
  public void saveAll(@RequestBody List<SubscriptionRelationship> list) {
    subscriptionRelationshipService.saveAll(list);
  }

  @DeleteMapping
  public void deleteByTopic(@RequestParam("instanceId") Long instanceId,
    @RequestParam("topic") String topic) {
    subscriptionRelationshipService.deleteByInstanceIdAndTopic(instanceId, topic);
  }

  @GetMapping("/search")
  public Page<SubscriptionRelationship> search(SubscriptionRelationship subscriptionRelationship,
    @Param("pageSize") int pageSize, @RequestParam("page") int page) {
    ExampleMatcher matcher = ExampleMatcher.matching()
      .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
      .withIgnoreNullValues();
    Example<SubscriptionRelationship> example = Example.of(
      subscriptionRelationship, matcher);
    return subscriptionRelationshipRepository.findAll(example,
      Pageable.ofSize(pageSize).withPage(page));
  }
}
