package io.gaecfov.pulsar.console.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {

    final String authorizationHeader = request.getHeader("Authorization");
    String jwt = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
    }
    if (!StringUtils.hasLength(jwt)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      JwtUserInfo userInfo = JwtUtil.parseToken(jwt);
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          userInfo.getUsername(), null, userInfo.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception ex) {
      log.error("Check Jwt Failed, {}", ex.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    filterChain.doFilter(request, response);
  }
}