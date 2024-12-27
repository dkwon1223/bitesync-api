package com.bitesync.api.service;

import com.bitesync.api.entity.InventoryItem;

import java.util.List;

public interface InventoryItemService {
  InventoryItem saveInventoryItem(InventoryItem inventoryItem);
  InventoryItem getInventoryItemById(Long id);
  List<InventoryItem> getAllInventoryItems();
}
