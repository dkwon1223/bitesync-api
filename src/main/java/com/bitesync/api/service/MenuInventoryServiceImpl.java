package com.bitesync.api.service;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.entity.MenuInventory;
import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.repository.InventoryItemRepository;
import com.bitesync.api.repository.MenuInventoryRepository;
import com.bitesync.api.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuInventoryServiceImpl implements MenuInventoryService {

  private InventoryItemRepository inventoryItemRepository;
  private MenuItemRepository menuItemRepository;
  private MenuInventoryRepository menuInventoryRepository;

  @Override
  public List<MenuInventory> getMenuInventory(Long userId, Long menuItemId) {
    MenuItem targetMenuItem  = MenuItemServiceImpl.unwrapMenuItem(menuItemRepository.findById(menuItemId), userId, menuItemId);
    return targetMenuItem.getMenuInventoryItems();
  }

  @Override
  public MenuInventory createMenuInventory(Long userId, Long inventoryItemId, Long menuItemId, MenuInventory menuInventory) {
    InventoryItem targetInventoryItem = InventoryItemServiceImpl.unwrapInventoryItem(inventoryItemRepository.findById(inventoryItemId), userId, inventoryItemId);
    MenuItem targetMenuItem = MenuItemServiceImpl.unwrapMenuItem(menuItemRepository.findById(menuItemId), userId, menuItemId);

    menuInventory.setRequiredInventoryItem(targetInventoryItem);
    menuInventory.setRequiredMenuItem(targetMenuItem);

    menuInventoryRepository.save(menuInventory);
    return menuInventory;
  }
}
