package com.bitesync.api.controller;

import com.bitesync.api.service.InventoryItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryItemController {
  private InventoryItemService inventoryItemService;
}
