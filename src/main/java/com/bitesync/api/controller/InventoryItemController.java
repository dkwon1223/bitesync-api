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

  @GetMapping("/{id}")
  public ResponseEntity<InventoryItem> getInventoryItem(@PathVariable Long id) {
    return new ResponseEntity<>(inventoryItemService.getInventoryItemById(id), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
    return new ResponseEntity<>(inventoryItemService.getAllInventoryItems(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<InventoryItem> createInventoryItem(@Valid @RequestBody InventoryItem inventoryItem) {
    return new ResponseEntity<>(inventoryItemService.saveInventoryItem(inventoryItem), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<InventoryItem> updateInventoryItem(@Valid @PathVariable Long id, @RequestBody InventoryItem inventoryItem) {
    return new ResponseEntity<>(inventoryItemService.updateInventoryItem(id, inventoryItem), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<InventoryItem> deleteInventoryItem(@PathVariable Long id) {
    inventoryItemService.deleteInventoryItem(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
