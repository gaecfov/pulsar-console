package io.gaecfov.pulsar.console.controller;

import io.gaecfov.pulsar.console.entity.Instance;
import io.gaecfov.pulsar.console.service.InstanceService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqin
 */
@RestController
@RequestMapping("/instance")
public class InstanceController {

  final InstanceService instanceService;

  public InstanceController(InstanceService instanceService) {
    this.instanceService = instanceService;
  }


  @GetMapping
  public List<Instance> listAll() {
    return instanceService.listAll();
  }

  @PostMapping
  public void create(@Valid @RequestBody Instance instance) {
    instanceService.save(instance);
  }

  @PutMapping("/{instanceId}")
  public void modify(@PathVariable("instanceId") Long instanceId,
    @Valid @RequestBody Instance instance) {
    instance.setId(instanceId);
    instanceService.save(instance);
  }

  @GetMapping("/{instanceId}")
  public Instance get(@PathVariable("instanceId") Long instanceId) {
    return instanceService.getById(instanceId).orElse(null);
  }

  @DeleteMapping("/{instanceId}")
  public void delete(@PathVariable("instanceId") Long instanceId) {
    instanceService.delete(instanceId);
  }
}
