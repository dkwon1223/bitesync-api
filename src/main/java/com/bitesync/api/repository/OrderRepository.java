package com.bitesync.api.repository;

import com.bitesync.api.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
  Optional<Order> findById(Long orderId);
  List<Order> findByUserId(Long userId);
}
