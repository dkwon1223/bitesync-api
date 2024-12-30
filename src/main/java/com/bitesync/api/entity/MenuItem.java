package com.bitesync.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menu_item", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "user_id"})
})
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

  @JsonIgnore
  @ManyToOne(optional = false)
  @JoinColumn(referencedColumnName = "id", name = "user_id")
  private User user;

  @JsonIgnore
  @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems;
}
