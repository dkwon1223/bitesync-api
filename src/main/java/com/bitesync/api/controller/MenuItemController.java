package com.bitesync.api.controller;

import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.service.MenuItemService;
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
@RequestMapping("/menu")
public class MenuItemController {

  private MenuItemService menuItemService;

  @GetMapping("/all")
  public ResponseEntity<List<MenuItem>> getAllMenuItems() {
    return new ResponseEntity<>(menuItemService.findAllMenuItems(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
    return new ResponseEntity<>(menuItemService.findMenuItemById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
    return new ResponseEntity<>(menuItemService.saveMenuItem(menuItem), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
    return new ResponseEntity<>(menuItemService.updateMenuItem(id, menuItem), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MenuItem> deleteMenuItem(@PathVariable Long id) {
    menuItemService.deleteMenuItem(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
