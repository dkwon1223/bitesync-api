package com.bitesync.api.controller;

import com.bitesync.api.entity.Order;
import com.bitesync.api.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {

  private OrderService orderService;

  @GetMapping("/all")
  public ResponseEntity<List<Order>> getAllOrders() {
    return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
    return new ResponseEntity<>(orderService.updateOrder(id, order), HttpStatus.OK);
  }
}
