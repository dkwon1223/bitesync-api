package com.bitesync.api.service;

import com.bitesync.api.entity.Order;

import java.util.List;

public interface OrderService {
  List<Order> getAllOrders();
  List<Order> getOrdersByUserId(Long userId);
  Order getOrderById(Long id);
  Order createOrder(Order order);
  Order updateOrder(Long id, Order order);
  void deleteOrder(Long id);
}
