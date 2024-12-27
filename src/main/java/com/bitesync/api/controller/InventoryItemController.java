package com.bitesync.api.controller;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.service.InventoryItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/inventory")
public class InventoryItemController {

  private InventoryItemService inventoryItemService;

  @GetMapping("/all")
  public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
    return new ResponseEntity<>(inventoryItemService.getAllInventoryItems(), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/item/{inventoryItemId}")
  public ResponseEntity<InventoryItem> getInventoryItem(@PathVariable Long userId, @PathVariable Long inventoryItemId) {
    return new ResponseEntity<>(inventoryItemService.getInventoryItemById(userId, inventoryItemId), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<InventoryItem>> getUserInventoryItems(@PathVariable Long userId) {
    return new ResponseEntity<>(inventoryItemService.getUserInventoryItems(userId), HttpStatus.OK);
  }

  @PostMapping("/user/{userId}")
  public ResponseEntity<InventoryItem> createInventoryItem(@Valid @RequestBody InventoryItem inventoryItem, @PathVariable Long userId) {
    return new ResponseEntity<>(inventoryItemService.saveInventoryItem(userId, inventoryItem), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InventoryItem> updateInventoryItem(@PathVariable Long id, @Valid @RequestBody InventoryItem inventoryItem) {
    return new ResponseEntity<>(inventoryItemService.updateInventoryItem(id, inventoryItem), HttpStatus.OK);
  }

  @DeleteMapping("/user/{userId}/item/{inventoryItemId}")
  public ResponseEntity<InventoryItem> deleteInventoryItem(@PathVariable Long userId, @PathVariable Long inventoryItemId) {
    inventoryItemService.deleteInventoryItem(userId, inventoryItemId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
