package com.bitesync.api.service;

import com.bitesync.api.entity.Order;
import com.bitesync.api.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;

  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }
}
