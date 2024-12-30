package com.bitesync.api.controller;

import com.bitesync.api.entity.MenuInventory;
import com.bitesync.api.service.MenuInventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu-inventory")
@AllArgsConstructor
public class MenuInventoryController {
  private MenuInventoryService menuInventoryService;

  @PostMapping("/user/{userId}/inventory-item/{inventoryItemId}/menu-item/{menuItemId}")
  public ResponseEntity<MenuInventory> createMenuInventory(@PathVariable Long userId, @PathVariable Long inventoryItemId, @PathVariable Long menuItemId, @RequestBody MenuInventory menuInventory) {

  }
}
