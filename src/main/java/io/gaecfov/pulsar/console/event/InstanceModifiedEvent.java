package io.gaecfov.pulsar.console.event;

import io.gaecfov.pulsar.console.entity.Instance;
import org.springframework.context.ApplicationEvent;

/**
 * @author zhangqin
 */
public class InstanceModifiedEvent extends ApplicationEvent {

  public InstanceModifiedEvent(Instance instance) {
    super(instance);
  }

  public Instance getInstance() {
    return (Instance) getSource();
  }
}
