package io.gaecfov.pulsar.console.repository;

import io.gaecfov.pulsar.console.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhangqin
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}