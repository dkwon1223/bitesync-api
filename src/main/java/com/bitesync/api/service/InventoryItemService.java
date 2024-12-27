package com.bitesync.api.service;

import com.bitesync.api.entity.InventoryItem;

import java.util.List;

public interface InventoryItemService {
  InventoryItem saveInventoryItem(Long userId, InventoryItem inventoryItem);
  InventoryItem getInventoryItemById(Long userId, Long inventoryItemId);
  List<InventoryItem> getAllInventoryItems();
  InventoryItem updateInventoryItem(Long id, InventoryItem inventoryItem);
  void deleteInventoryItem(Long id);
}
