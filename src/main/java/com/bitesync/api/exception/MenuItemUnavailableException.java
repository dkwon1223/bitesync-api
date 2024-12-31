package com.bitesync.api.exception;

public class MenuItemUnavailableException extends RuntimeException {
  public MenuItemUnavailableException(Long menuItemId, String menuItemName) {
    super("The menu item: " + menuItemName + " with menu item id: " + menuItemId + " is currently unavailable.");
  }
}
