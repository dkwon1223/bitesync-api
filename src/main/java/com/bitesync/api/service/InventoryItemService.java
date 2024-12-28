package com.bitesync.api.service;

import com.bitesync.api.entity.InventoryItem;

import java.util.List;

public interface InventoryItemService {
  InventoryItem saveInventoryItem(Long userId, InventoryItem inventoryItem);
  InventoryItem getInventoryItemById(Long userId, Long inventoryItemId);
  List<InventoryItem> getAllInventoryItems();
  List<InventoryItem> getUserInventoryItems(Long userId);
  InventoryItem updateInventoryItem(Long inventoryItemId, Long userId, InventoryItem inventoryItem);
  void deleteInventoryItem(Long userId, Long inventoryItemId);
}
