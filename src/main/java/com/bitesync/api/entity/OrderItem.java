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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "order_item")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @NotNull(message = "order item quantity cannot be null")
  @Positive(message = "order item quantity must be greater than 0")
  @Column(name = "quantity")
  private Integer quantity;

  @NonNull
  @NotNull(message = "order item subtotal cannot be null")
  @Positive(message = "order item subtotal must be greater than 0")
  @Column(name = "subtotal")
  private BigDecimal subtotal;

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(referencedColumnName = "id", name = "order_id")
  private Order order;

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(referencedColumnName = "id", name = "menu_item_id")
  private MenuItem menuItem;
}
