package io.gaecfov.pulsar.console.security;

import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhangqin
 */
@Data
public class JwtUserInfo {

  private String username;
  private List<? extends GrantedAuthority> authorities;
}
