package com.bitesync.api.service;

import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.exception.EntityNotFoundException;
import com.bitesync.api.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

  private MenuItemRepository menuItemRepository;

  @Override
  public List<MenuItem> findAllMenuItems() {
    return (List<MenuItem>) menuItemRepository.findAll();
  }

  @Override
  public MenuItem findMenuItemById(Long id) {
    Optional<MenuItem> menuItem = menuItemRepository.findById(id);
    return unwrapMenuItem(menuItem, id);
  }

  @Override
  public MenuItem saveMenuItem(MenuItem menuItem) {
    return menuItemRepository.save(menuItem);
  }

  static MenuItem unwrapMenuItem(Optional<MenuItem> entity, Long id) {
    if (entity.isPresent()) {
      return entity.get();
    } else {
      throw new EntityNotFoundException(id, MenuItem.class);
    }
  }

}
