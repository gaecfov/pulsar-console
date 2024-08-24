package io.gaecfov.pulsar.console.security;

import io.gaecfov.pulsar.console.entity.User;
import io.gaecfov.pulsar.console.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zhangqin
 */
public class PulsarUserDetailService implements UserDetailsService {

  private final UserService userService;

  public PulsarUserDetailService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.getByName(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return new PulsarUserDetails(user);
  }
}
