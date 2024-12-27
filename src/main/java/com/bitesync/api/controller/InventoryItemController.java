package com.bitesync.api.controller;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.service.InventoryItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryItemController {

  private InventoryItemService inventoryItemService;

  @PostMapping
  public ResponseEntity<InventoryItem> createInventoryItem(@RequestBody InventoryItem inventoryItem) {
    return new ResponseEntity<>(inventoryItemService.saveInventoryItem(inventoryItem), HttpStatus.CREATED);
  }
}
