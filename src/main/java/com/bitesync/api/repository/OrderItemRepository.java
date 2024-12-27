package com.bitesync.api.repository;

import com.bitesync.api.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
  Optional<OrderItem> findById(Long id);
}
