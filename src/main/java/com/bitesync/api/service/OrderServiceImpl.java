package com.bitesync.api.service;

import com.bitesync.api.entity.Order;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;

  @Override
  public List<Order> getAllOrders() {
    return (List<Order>) orderRepository.findAll();
  }

  @Override
  public Order getOrderById(Long id) {
    Optional<Order> order = orderRepository.findById(id);
    return unwrapOrder(order, id);
  }

  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public Order updateOrder(Long id, Order order) {
    return orderRepository.findById(id)
        .map(existingOrder -> {
          existingOrder.setCustomerName(order.getCustomerName());
          existingOrder.setStatus(order.getStatus());
          existingOrder.setTotal(order.getTotal());
          existingOrder.setUpdatedAt(order.getUpdatedAt());
          return orderRepository.save(existingOrder);
        })
        .orElseThrow(() -> new EntityNotFoundException(id, Order.class));
  }

  @Override
  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }

  static Order unwrapOrder(Optional<Order> entity, Long id) {
    if(entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(id, Order.class);
    }
  }
}
