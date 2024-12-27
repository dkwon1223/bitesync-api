package com.bitesync.api.controller;

import com.bitesync.api.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/order/item")
public class OrderItemController {

  private OrderItemService orderItemService;
}
