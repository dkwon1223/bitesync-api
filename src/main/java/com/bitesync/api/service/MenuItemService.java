package com.bitesync.api.service;

import com.bitesync.api.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
  MenuItem saveMenuItem(Long userId, MenuItem menuItem);
  List<MenuItem> findAllMenuItems();
  MenuItem findMenuItemByUserIdAndMenuItemId(Long userId, Long menuItemId);
  List<MenuItem> findMenuItemsByUserId(Long userId);
  MenuItem updateMenuItem(Long userId, Long menuItemId, MenuItem menuItem);
  void deleteMenuItem(Long userId, Long menuItemId);
}
