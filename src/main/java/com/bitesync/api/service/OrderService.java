package com.bitesync.api.service;

import com.bitesync.api.entity.Order;

import java.util.List;

public interface OrderService {
  List<Order> getAllOrders();
  Order getOrderById(Long id);
  Order createOrder(Order order);
}
