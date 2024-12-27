package com.bitesync.api.service;

import com.bitesync.api.repository.InventoryItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {
  private InventoryItemRepository inventoryItemRepository;
}
