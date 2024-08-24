package io.gaecfov.pulsar.console.security;

import io.gaecfov.pulsar.console.entity.User;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zhangqin
 */
public class PulsarUserDetails implements Serializable, UserDetails {

  /**
   * Returns the authorities granted to the user. Cannot return <code>null</code>.
   *
   * @return the authorities, sorted by natural key (never <code>null</code>)
   */
  private final User user;

  public PulsarUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return the password
   */
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  /**
   * Returns the username used to authenticate the user. Cannot return
   * <code>null</code>.
   *
   * @return the username (never <code>null</code>)
   */
  @Override
  public String getUsername() {
    return user.getUsername();
  }
}
