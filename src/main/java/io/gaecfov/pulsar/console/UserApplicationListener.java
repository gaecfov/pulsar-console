package io.gaecfov.pulsar.console;

import io.gaecfov.pulsar.console.entity.User;
import io.gaecfov.pulsar.console.service.PasswordGenerator;
import io.gaecfov.pulsar.console.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zhangqin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

  final UserService userService;
  final PasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    seedDefaultUser();
  }

  private void seedDefaultUser() {
    User admin = userService.getByName("admin");
    if (admin == null) {
      admin = new User();
      admin.setUsername("admin");
      String password = PasswordGenerator.generatePassword(12);
      admin.setPassword(passwordEncoder.encode(password));
      admin.setRole("ADMIN");
      userService.create(admin);
      log.info("Successfully added a default superuser: name = {}, password = {}",
          admin.getUsername(), password);
    }
  }
}
