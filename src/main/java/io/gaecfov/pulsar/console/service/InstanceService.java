package io.gaecfov.pulsar.console.service;

import io.gaecfov.pulsar.console.entity.Instance;
import io.gaecfov.pulsar.console.event.InstanceModifiedEvent;
import io.gaecfov.pulsar.console.repository.InstanceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author zhangqin
 */
@Service
public class InstanceService {

  private final ApplicationEventPublisher eventPublisher;
  private final InstanceRepository instanceRepository;

  public InstanceService(ApplicationEventPublisher eventPublisher,
      InstanceRepository instanceRepository) {
    this.eventPublisher = eventPublisher;
    this.instanceRepository = instanceRepository;
  }

  public List<Instance> listAll() {
    return instanceRepository.findAll();
  }

  public Instance save(Instance instance) {
    Instance saved = instanceRepository.save(instance);
    eventPublisher.publishEvent(new InstanceModifiedEvent(saved));
    return saved;
  }

  public void delete(Long instanceId) {
    instanceRepository.deleteById(instanceId);
  }

  public Optional<Instance> getById(Long id) {
    return instanceRepository.findById(id);
  }
}
