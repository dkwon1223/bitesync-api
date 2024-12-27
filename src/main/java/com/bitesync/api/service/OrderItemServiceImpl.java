package com.bitesync.api.service;

import com.bitesync.api.entity.OrderItem;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {

  private OrderItemRepository orderItemRepository;

  @Override
  public List<OrderItem> findAllOrderItems() {
    return (List<OrderItem>) orderItemRepository.findAll();
  }

  @Override
  public OrderItem findOrderItemById(Long id) {
    Optional<OrderItem> orderItem = orderItemRepository.findById(id);
    return unwrapOrderItem(orderItem, id);
  }

  @Override
  public OrderItem save(OrderItem orderItem) {
    return orderItemRepository.save(orderItem);
  }

  static OrderItem unwrapOrderItem(Optional<OrderItem> entity, Long id) {
    if(entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(id, OrderItem.class);
    }
  }
}
