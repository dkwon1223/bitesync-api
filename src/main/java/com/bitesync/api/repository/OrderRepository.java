package com.bitesync.api.repository;

import com.bitesync.api.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
