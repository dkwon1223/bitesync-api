package com.bitesync.api.repository;

import com.bitesync.api.entity.InventoryItem;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long> {
  Optional<InventoryItem> findByUserIdAndId(Long userId, Long id);
  @Transactional
  void deleteInventoryItemByUserIdAndId(Long userId, Long inventoryItemId);
}
