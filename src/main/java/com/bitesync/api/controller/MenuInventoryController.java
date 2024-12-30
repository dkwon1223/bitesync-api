package com.bitesync.api.controller;

import com.bitesync.api.entity.MenuInventory;
import com.bitesync.api.service.MenuInventoryService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/menu-inventory")
@AllArgsConstructor
public class MenuInventoryController {

  private MenuInventoryService menuInventoryService;

  @GetMapping("/user/{userId}/menu/{menuId}")
  public ResponseEntity<List<MenuInventory>> getAllMenuInventory(@PathVariable Long userId, @PathVariable Long menuId) {
    return new ResponseEntity<>(menuInventoryService.getMenuInventory(userId, menuId), HttpStatus.OK);
  }

  @PostMapping("/user/{userId}/inventory-item/{inventoryItemId}/menu-item/{menuItemId}")
  public ResponseEntity<MenuInventory> createMenuInventory(@PathVariable Long userId, @PathVariable Long inventoryItemId, @PathVariable Long menuItemId, @Valid @RequestBody MenuInventory menuInventory) {
    return new ResponseEntity<>(menuInventoryService.createMenuInventory(userId, inventoryItemId, menuItemId, menuInventory), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MenuInventory> updateMenuInventory(@PathVariable Long id, @RequestBody MenuInventory menuInventory) {
    return new ResponseEntity<>(menuInventoryService.updateMenuInventory(id, menuInventory), HttpStatus.OK);
  }
}
