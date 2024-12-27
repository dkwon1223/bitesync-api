package com.bitesync.api.exception;

public class MenuItemNotFoundException extends RuntimeException {
  public MenuItemNotFoundException(Long userId, Long menuItemId) {
    super("The menu item with user id: '" + userId + "' and menu item id: '" + menuItemId + "' does not exist in our records");
  }
}
