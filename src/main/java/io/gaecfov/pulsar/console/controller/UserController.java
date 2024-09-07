package io.gaecfov.pulsar.console.controller;

import io.gaecfov.pulsar.console.entity.User;
import io.gaecfov.pulsar.console.request.ChangePasswordRequest;
import io.gaecfov.pulsar.console.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void createUser(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userService.create(user);
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<User> listUsers() {
    return userService.listAll();
  }

  @DeleteMapping("/{userId}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public void deleteUser(@PathVariable("userId") Long userId) {
    userService.remove(userId);
  }

  @PutMapping("/{userId}/password")
  public void changePassword(@PathVariable("userId") Long userId,
    @Valid @RequestBody ChangePasswordRequest password) {
    User user = userService.getById(userId);
    if (passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordEncoder.encode(password.getNewPassword()));
      userService.modify(user);
    } else {
      throw new BadCredentialsException("original password is incorrect");
    }
  }
}
