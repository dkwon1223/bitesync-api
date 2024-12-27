package com.bitesync.api.controller;

import com.bitesync.api.entity.Order;
import com.bitesync.api.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

  private OrderService orderService;

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
  }
}
