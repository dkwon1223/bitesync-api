package com.bitesync.api.service;

import com.bitesync.api.entity.Order;
import com.bitesync.api.entity.OrderItem;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.OrderItemRepository;
import com.bitesync.api.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {

  private OrderItemRepository orderItemRepository;
  private OrderRepository orderRepository;
  private OrderServiceImpl orderServiceImpl;

  @Override
  public List<OrderItem> findAllOrderItems() {
    return (List<OrderItem>) orderItemRepository.findAll();
  }

  @Override
  public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
    return orderItemRepository.findByOrderId(orderId);
  }

  @Override
  public OrderItem findOrderItemById(Long id) {
    Optional<OrderItem> orderItem = orderItemRepository.findById(id);
    return unwrapOrderItem(orderItem, id);
  }

  @Override
  public OrderItem save(Long orderId, OrderItem orderItem) {
    Optional<Order> order = orderRepository.findById(orderId);
    Order targetOrder = OrderServiceImpl.unwrapOrder(order, orderId);
    orderItem.setOrder(targetOrder);
    return orderItemRepository.save(orderItem);
  }

  @Override
  public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
    return orderItemRepository.findById(id)
        .map(existingOrderItem -> {
          existingOrderItem.setQuantity(orderItem.getQuantity());
          existingOrderItem.setSubtotal(orderItem.getSubtotal());
          return orderItemRepository.save(existingOrderItem);
        })
        .orElseThrow(() -> new EntityNotFoundException(id, OrderItem.class));
  }

  @Override
  public void deleteOrderItem(Long id) {
    orderItemRepository.deleteById(id);
  }

  static OrderItem unwrapOrderItem(Optional<OrderItem> entity, Long id) {
    if(entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(id, OrderItem.class);
    }
  }
}
