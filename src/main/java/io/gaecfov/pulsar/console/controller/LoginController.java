package io.gaecfov.pulsar.console.controller;

import io.gaecfov.pulsar.console.entity.User;
import io.gaecfov.pulsar.console.request.LoginRequest;
import io.gaecfov.pulsar.console.response.CurrentUser;
import io.gaecfov.pulsar.console.security.JwtUtil;
import io.gaecfov.pulsar.console.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  @PostMapping("/login")
  public String login(@Valid @RequestBody LoginRequest request) {
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
      request.getUsername(), request.getPassword());
    Authentication authentication = authenticationManager.authenticate(auth);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return JwtUtil.generateToken(userDetails);
  }

  @GetMapping("/currentUser")
  public ResponseEntity<CurrentUser> getCurrentUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal()
      .toString();
    User user = userService.getByName(username);
    CurrentUser currentUser = new CurrentUser();
    currentUser.setUserId(user.getId());
    currentUser.setUsername(user.getUsername());
    currentUser.setRole(user.getRole());
    return ResponseEntity.ok(currentUser);
  }
}
