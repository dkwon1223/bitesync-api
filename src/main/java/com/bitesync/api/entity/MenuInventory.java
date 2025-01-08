package com.bitesync.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "menu_inventory")
public class MenuInventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @NotNull(message = "quantity needed cannot be null")
  @Min(value = 1, message = "quantity must be at least 1")
  @Column(name = "quantity_needed")
  private Integer quantityNeeded;


  @ManyToOne(optional = false)
  @JoinColumn(referencedColumnName = "id", name = "inventory_item_id")
  private InventoryItem requiredInventoryItem;

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(referencedColumnName = "id", name = "menu_item_id")
  private MenuItem requiredMenuItem;
}
