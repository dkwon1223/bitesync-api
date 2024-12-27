package com.bitesync.api.service;

import com.bitesync.api.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
  MenuItem saveMenuItem(MenuItem menuItem);
  List<MenuItem> findAllMenuItems();
  MenuItem findMenuItemById(Long id);
  MenuItem updateMenuItem(Long id, MenuItem menuItem);
}
