package com.bitesync.api.service;

import com.bitesync.api.entity.Order;
import com.bitesync.api.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;

  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }
}
