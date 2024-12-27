package com.bitesync.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menu_item")
public class MenuItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @NotBlank(message = "menu item name cannot be blank")
  @Column(name = "name")
  private String name;

  @NonNull
  @NotBlank(message = "menu item image url cannot be blank")
  @Column(name = "image_url")
  private String imageUrl;

  @NonNull
  @NotBlank(message = "menu item description cannot be blank")
  @Column(name = "description")
  private String description;

  @NonNull
  @NotNull(message = "menu item price cannot be null")
  @Column(name = "price")
  private BigDecimal price;

  @NonNull
  @NotBlank(message = "menu item category cannot be blank")
  @Column(name = "category")
  private String category;

  @NonNull
  @NotNull(message = "menu item availability cannot be null")
  @Column(name = "available")
  private Boolean available;
}
