package com.bitesync.api.service;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.entity.User;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.InventoryItemRepository;
import com.bitesync.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

  private InventoryItemRepository inventoryItemRepository;
  private UserRepository userRepository;

  @Override
  public InventoryItem getInventoryItemById(Long userId, Long inventoryItemId) throws EntityNotFoundException {
    Optional<InventoryItem> inventoryItem = inventoryItemRepository.findByUserIdAndId(userId, inventoryItemId);
    return unwrapInventoryItem(inventoryItem, userId, inventoryItemId);
  }

  @Override
  public List<InventoryItem> getAllInventoryItems() {
    return (List<InventoryItem>) inventoryItemRepository.findAll();
  }

  @Override
  public List<InventoryItem> getUserInventoryItems(Long userId) {
    User user = userRepository.findById(userId).get();
    return user.getInventoryItems();
  }

  @Override
  public InventoryItem saveInventoryItem(Long userId, InventoryItem inventoryItem) {
    User user = userRepository.findById(userId).get();
    inventoryItem.setUser(user);
    return inventoryItemRepository.save(inventoryItem);
  }

  @Override
  public InventoryItem updateInventoryItem(Long id, InventoryItem inventoryItem) {
    return inventoryItemRepository.findById(id)
        .map(existingItem -> {
          existingItem.setName(inventoryItem.getName());
          existingItem.setImageUrl(inventoryItem.getImageUrl());
          existingItem.setQuantity(inventoryItem.getQuantity());
          existingItem.setUnitPrice(inventoryItem.getUnitPrice());
          existingItem.setCategory(inventoryItem.getCategory());
          existingItem.setUpdatedAt(inventoryItem.getUpdatedAt());
          return inventoryItemRepository.save(existingItem);
        })
        .orElseThrow(() -> new EntityNotFoundException(id, InventoryItem.class));
  }

  @Override
  public void deleteInventoryItem(Long id) {
    inventoryItemRepository.deleteById(id);
  }

  static InventoryItem unwrapInventoryItem(Optional<InventoryItem> entity, Long userId, Long inventoryItemId) {
    if(entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(inventoryItemId, InventoryItem.class);
    }
  }
}
