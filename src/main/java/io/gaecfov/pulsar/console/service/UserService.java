package io.gaecfov.pulsar.console.service;

import io.gaecfov.pulsar.console.entity.User;
import io.gaecfov.pulsar.console.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author zhangqin
 */
@Service
public class UserService {


  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> listAll() {
    return userRepository.findAll();
  }

  public User create(User user) {
    return userRepository.save(user);
  }

  public User getByName(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  public User getById(Long userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public void modify(User user) {
    userRepository.save(user);
  }
}
