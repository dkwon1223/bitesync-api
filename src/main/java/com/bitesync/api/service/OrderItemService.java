package com.bitesync.api.service;

import com.bitesync.api.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
  OrderItem save(OrderItem orderItem);
  List<OrderItem> findAllOrderItems();
  OrderItem findOrderItemById(Long id);
  OrderItem updateOrderItem(Long id, OrderItem orderItem);
  void deleteOrderItem(Long id);
}
