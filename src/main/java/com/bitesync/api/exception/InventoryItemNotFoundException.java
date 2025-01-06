package com.bitesync.api.exception;

public class InventoryItemNotFoundException extends RuntimeException {
  public InventoryItemNotFoundException(Long inventoryId, Long userId) {
    super("The item with inventory id: '" + inventoryId + "' and user id: '" + userId + "' does not exist in our records");
  }
}
