package io.gaecfov.pulsar.console.service;

import io.gaecfov.pulsar.console.entity.Instance;
import io.gaecfov.pulsar.console.repository.InstanceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author zhangqin
 */
@Service
public class InstanceService {

  private final InstanceRepository instanceRepository;

  public InstanceService(InstanceRepository instanceRepository) {
    this.instanceRepository = instanceRepository;
  }


  public List<Instance> listAll() {
    return instanceRepository.findAll();
  }

  public Instance save(Instance instance) {
    return instanceRepository.save(instance);
  }

  public void delete(Long instanceId) {
    instanceRepository.deleteById(instanceId);
  }

  public Optional<Instance> getById(Long id) {
    return instanceRepository.findById(id);
  }
}
