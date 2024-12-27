package com.bitesync.api.repository;

import com.bitesync.api.entity.InventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long> {
  Optional<InventoryItem> findById(Long id);
}
