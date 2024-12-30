package com.bitesync.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.awt.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_inventory")
public class MenuInventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @NotNull(message = "quantity needed cannot be null")
  @Column
  private BigDecimal quantityNeeded;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id", name = "inventory_item_id")
  private InventoryItem inventoryItem;

  @ManyToOne
  @JoinColumn(referencedColumnName = "id", name = "menu_item_id")
  private MenuItem menuItem;
}
