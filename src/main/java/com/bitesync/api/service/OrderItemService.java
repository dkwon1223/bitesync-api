package com.bitesync.api.service;

import com.bitesync.api.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
  OrderItem save(Long userId, Long orderId, Long menuItemId, OrderItem orderItem);
  List<OrderItem> findAllOrderItems();
  List<OrderItem> findOrderItemsByOrderId(Long orderId);
  OrderItem findOrderItemById(Long id);
  OrderItem updateOrderItem(Long id, OrderItem orderItem);
  void deleteOrderItem(Long id);
}
