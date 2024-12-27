package com.bitesync.api.repository;

import com.bitesync.api.entity.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {
  Optional<MenuItem> findById(Long id);
  MenuItem findMenuItemByUserIdAndId(Long userId, Long menuItemId);
}
