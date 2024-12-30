package com.bitesync.api.repository;

import com.bitesync.api.entity.MenuInventory;
import com.bitesync.api.entity.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuInventoryRepository extends CrudRepository<MenuInventory, Long> {
  List<MenuInventory> findByRequiredMenuItem(MenuItem menuItem);
}
