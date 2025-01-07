package com.bitesync.api.service;

import com.bitesync.api.entity.MenuInventory;

import java.util.List;

public interface MenuInventoryService {
  MenuInventory createMenuInventory(Long userId, Long inventoryItemId, Long menuItemId, MenuInventory menuInventory);
  List<MenuInventory> getMenuInventory(Long userId, Long menuItemId);
  MenuInventory updateMenuInventory(Long menuInventoryId, MenuInventory menuInventory);
  void deleteMenuInventory(Long menuInventoryId);
}
