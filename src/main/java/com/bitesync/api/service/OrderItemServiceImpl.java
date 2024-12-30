package com.bitesync.api.service;

import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.entity.Order;
import com.bitesync.api.entity.OrderItem;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.MenuItemRepository;
import com.bitesync.api.repository.OrderItemRepository;
import com.bitesync.api.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {

  private OrderItemRepository orderItemRepository;
  private OrderRepository orderRepository;
  private MenuItemRepository menuItemRepository;

  @Override
  public List<OrderItem> findAllOrderItems() {
    return (List<OrderItem>) orderItemRepository.findAll();
  }

  @Override
  public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
    Order targetOrder = OrderServiceImpl.unwrapOrder(orderRepository.findById(orderId), orderId);
    return orderItemRepository.findByOrderId(targetOrder.getId());
  }

  @Override
  public OrderItem findOrderItemById(Long id) {
    Optional<OrderItem> orderItem = orderItemRepository.findById(id);
    return unwrapOrderItem(orderItem, id);
  }

  @Override
  public OrderItem save(Long userId, Long orderId, Long menuItemId, OrderItem orderItem) {

    MenuItem targetMenuItem = MenuItemServiceImpl.unwrapMenuItem(menuItemRepository.findById(menuItemId), userId, menuItemId);
    BigDecimal price = targetMenuItem.getPrice();
    BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());

    orderItem.setSubtotal(price.multiply(quantity));
    orderItem.setMenuItem(targetMenuItem);

    Order targetOrder = OrderServiceImpl.unwrapOrder(orderRepository.findById(orderId), orderId);
    targetOrder.setTotal(price.add(orderItem.getSubtotal()));
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
