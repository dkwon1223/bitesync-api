package com.bitesync.api.service;

import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.entity.User;
import com.bitesync.api.exception.MenuItemNotFoundException;
import com.bitesync.api.repository.MenuItemRepository;
import com.bitesync.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

  private MenuItemRepository menuItemRepository;
  private UserRepository userRepository;
  private UserServiceImpl userServiceImpl;

  @Override
  public List<MenuItem> findAllMenuItems() {
    return (List<MenuItem>) menuItemRepository.findAll();
  }

  @Override
  public MenuItem findMenuItemByUserIdAndMenuItemId(Long userId, Long menuItemId) throws MenuItemNotFoundException {
    Optional<User> user = userRepository.findById(userId);
    User targetUser = userServiceImpl.unwrapUser(user, userId);
    Optional<MenuItem> menuItem = menuItemRepository.findMenuItemByUserIdAndId(menuItemId, targetUser.getId());
    return unwrapMenuItem(menuItem, userId, menuItemId);
  }

  @Override
  public List<MenuItem> findMenuItemsByUserId(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    User targetUser = userServiceImpl.unwrapUser(user, userId);
    return targetUser.getMenuItems();
  }

  @Override
  public MenuItem saveMenuItem(Long userId, MenuItem menuItem) {
    Optional<User> user = userRepository.findById(userId);
    User targetUser = userServiceImpl.unwrapUser(user, userId);
    menuItem.setUser(targetUser);
    return menuItemRepository.save(menuItem);
  }

  @Override
  public MenuItem updateMenuItem(Long userId, Long menuItemId, MenuItem menuItem) {
    return menuItemRepository.findMenuItemByUserIdAndId(userId, menuItemId)
      .map(existingItem -> {
        existingItem.setName(menuItem.getName());
        existingItem.setImageUrl(menuItem.getImageUrl());
        existingItem.setDescription(menuItem.getDescription());
        existingItem.setPrice(menuItem.getPrice());
        existingItem.setCategory(menuItem.getCategory());
        existingItem.setAvailable(menuItem.getAvailable());
        return menuItemRepository.save(existingItem);
      })
      .orElseThrow(() -> new MenuItemNotFoundException(userId, menuItemId));
  }

  @Override
  public void deleteMenuItem(Long userId, Long menuItemId) {
    menuItemRepository.deleteMenuItemByUserIdAndId(userId, menuItemId);
  }

  static MenuItem unwrapMenuItem(Optional<MenuItem> entity, Long userId, Long menuItemId) {
    if (entity.isPresent()) {
      return entity.get();
    } else {
      throw new MenuItemNotFoundException(userId, menuItemId);
    }
  }

}
