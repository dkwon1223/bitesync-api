package com.bitesync.api.controller;

import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuItemController {

  private MenuItemService menuItemService;

  @PostMapping
  public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
    return new ResponseEntity<>(menuItemService.save(menuItem), HttpStatus.CREATED);
  }
}
