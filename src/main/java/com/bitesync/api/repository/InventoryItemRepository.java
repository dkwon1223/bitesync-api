package com.bitesync.api.repository;

import com.bitesync.api.entity.InventoryItem;
import org.springframework.data.repository.CrudRepository;

public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long> {

}
