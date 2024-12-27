package com.bitesync.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inventory_item")
public class InventoryItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @NotBlank(message = "name cannot be blank")
  @Column(name = "name")
  private String name;

  @Column(name = "img_url")
  private String imgUrl;

  @NonNull
  @NotNull(message = "quantity cannot be null")
  @PositiveOrZero(message = "quantity must be 0 or greater")
  @Column(name = "quantity")
  private Integer quantity;

  @NonNull
  @NotNull(message = "unit price cannot be null")
  @Positive(message = "unit price must be greater than 0")
  @Column(name = "unit_price")
  private BigDecimal unitPrice;

  @NonNull
  @NotBlank(message = "category cannot be blank")
  @Column(name = "category")
  private String category;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
