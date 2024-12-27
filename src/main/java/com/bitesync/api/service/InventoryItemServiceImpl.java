package com.bitesync.api.service;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.InventoryItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

  private InventoryItemRepository inventoryItemRepository;

  @Override
  public InventoryItem saveInventoryItem(InventoryItem inventoryItem) {
    return inventoryItemRepository.save(inventoryItem);
  }

  @Override
  public InventoryItem getInventoryItemById(Long id) {
    Optional<InventoryItem> inventoryItem = inventoryItemRepository.findById(id);
    return unwrapInventoryItem(inventoryItem, id);
  }

  @Override
  public List<InventoryItem> getAllInventoryItems() {
    return (List<InventoryItem>) inventoryItemRepository.findAll();
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

  static InventoryItem unwrapInventoryItem(Optional<InventoryItem> entity, Long id) {
    if(entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(id, InventoryItem.class);
    }
  }
}
