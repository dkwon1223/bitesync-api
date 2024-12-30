package com.bitesync.api.exception;

import com.bitesync.api.entity.InventoryItem;

public class InsufficientInventoryException extends RuntimeException {
  public InsufficientInventoryException(InventoryItem inventoryItem, Integer totalQuantityConsumed) {
    super(String.format(
        "Insufficient inventory for item: %s. Available: %d, Needed: %d",
        inventoryItem.getName(),
        inventoryItem.getQuantity(),
        totalQuantityConsumed
    ));
  }
}
