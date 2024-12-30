package com.bitesync.api.service;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.entity.MenuInventory;
import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.entity.Order;
import com.bitesync.api.entity.OrderItem;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.exception.InsufficientInventoryException;
import com.bitesync.api.repository.InventoryItemRepository;
import com.bitesync.api.repository.MenuInventoryRepository;
import com.bitesync.api.repository.MenuItemRepository;
import com.bitesync.api.repository.OrderItemRepository;
import com.bitesync.api.repository.OrderRepository;
import jakarta.transaction.Transactional;
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
  private InventoryItemRepository inventoryItemRepository;
  private MenuInventoryRepository menuInventoryRepository;

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
  @Transactional
  public OrderItem save(Long userId, Long orderId, Long menuItemId, OrderItem orderItem) {

      MenuItem targetMenuItem = MenuItemServiceImpl.unwrapMenuItem(menuItemRepository.findById(menuItemId), userId, menuItemId);
      BigDecimal price = targetMenuItem.getPrice();
      Integer quantity = orderItem.getQuantity();

      orderItem.setSubtotal(price.multiply(BigDecimal.valueOf(quantity)));
      orderItem.setMenuItem(targetMenuItem);

      Order targetOrder = OrderServiceImpl.unwrapOrder(orderRepository.findById(orderId), orderId);
      orderItem.setOrder(targetOrder);

      updateInventory(targetMenuItem, quantity);

      updateOrderTotal(targetOrder);

      orderItemRepository.save(orderItem);

      return orderItem;

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
    OrderItem orderItem = unwrapOrderItem(orderItemRepository.findById(id), id);
    MenuItem menuItem = orderItem.getMenuItem();

    List<MenuInventory> menuInventoryList = menuInventoryRepository.findByRequiredMenuItemId(menuItem.getId());
    Integer orderQuantity =  orderItem.getQuantity();

    for (MenuInventory menuInventory : menuInventoryList) {
      InventoryItem inventoryItem = menuInventory.getRequiredInventoryItem();
      int restoreQuantity = menuInventory.getQuantityNeeded() * orderQuantity;
      inventoryItem.setQuantity(inventoryItem.getQuantity() + restoreQuantity);
      inventoryItemRepository.save(inventoryItem);
    }
    orderItemRepository.deleteById(id);
  }

  static OrderItem unwrapOrderItem(Optional<OrderItem> entity, Long id) {
    if(entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(id, OrderItem.class);
    }
  }

  private void updateOrderTotal(Order order) {

    List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

    BigDecimal total = orderItems.stream()
        .map(OrderItem::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    order.setTotal(total);

    orderRepository.save(order);
  }

  private void updateInventory(MenuItem targetMenuItem, Integer orderQuantity) {
    List<MenuInventory> menuInventoryItems = menuInventoryRepository.findByRequiredMenuItem(targetMenuItem);

    for (MenuInventory menuInventory : menuInventoryItems) {
      InventoryItem inventoryItem = menuInventory.getRequiredInventoryItem();
      Integer quantityNeeded = (int) Math.ceil(menuInventory.getQuantityNeeded());
      Integer totalQuantityConsumed = quantityNeeded * orderQuantity;

      if (inventoryItem.getQuantity() < totalQuantityConsumed) {
        throw new InsufficientInventoryException(inventoryItem, totalQuantityConsumed);
      }
    }

    for (MenuInventory menuInventory : menuInventoryItems) {
      InventoryItem inventoryItem = menuInventory.getRequiredInventoryItem();
      Integer quantityNeeded = (int) Math.ceil(menuInventory.getQuantityNeeded());
      Integer totalQuantityConsumed = quantityNeeded * orderQuantity;

      inventoryItem.setQuantity(inventoryItem.getQuantity() - totalQuantityConsumed);
      inventoryItemRepository.save(inventoryItem);
    }
  }
}
