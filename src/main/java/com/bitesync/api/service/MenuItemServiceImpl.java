package com.bitesync.api.service;

import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

  private MenuItemRepository menuItemRepository;

  @Override
  public MenuItem save(MenuItem menuItem) {
    return menuItemRepository.save(menuItem);
  }
}
