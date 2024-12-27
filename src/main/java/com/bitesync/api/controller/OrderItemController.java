package com.bitesync.api.controller;

import com.bitesync.api.entity.OrderItem;
import com.bitesync.api.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/order/item")
public class OrderItemController {

  private OrderItemService orderItemService;

  @GetMapping("/all")
  public ResponseEntity<List<OrderItem>> getAll() {
    return new ResponseEntity<>(orderItemService.findAllOrderItems(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderItem> getById(@PathVariable Long id) {
    return new ResponseEntity<>(orderItemService.findOrderItemById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
    return new ResponseEntity<>(orderItemService.save(orderItem), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
    return new ResponseEntity<>(orderItemService.updateOrderItem(id, orderItem), HttpStatus.OK);
  }
}
