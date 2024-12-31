package com.bitesync.api.repository;

import com.bitesync.api.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByEmail(String email);
  Optional<User> findById(Long id);
  Boolean existsByEmail(String email);
}
