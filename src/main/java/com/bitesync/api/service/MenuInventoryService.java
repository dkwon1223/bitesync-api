package com.bitesync.api.service;

import com.bitesync.api.entity.MenuInventory;

public interface MenuInventoryService {
  MenuInventory createMenuInventory(Long userId, Long inventoryItemId, Long menuItemId, MenuInventory menuInventory);
}
