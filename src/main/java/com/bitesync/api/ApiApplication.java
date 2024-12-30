package com.bitesync.api;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.entity.User;
import com.bitesync.api.repository.InventoryItemRepository;
import com.bitesync.api.repository.MenuItemRepository;
import com.bitesync.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
@AllArgsConstructor
public class ApiApplication implements CommandLineRunner {

	private UserRepository userRepository;
	private InventoryItemRepository inventoryItemRepository;
	private MenuItemRepository menuItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {

		// Example user
		User user = new User("dingus", "fart");
		user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
		userRepository.save(user);

		// Example inventory
		InventoryItem[] inventoryItems = new InventoryItem[] {
				new InventoryItem("Buns", "https://example.com/images/buns.jpg", 50, new BigDecimal("1.50"), "Bakery"),
				new InventoryItem("Cheese", "https://example.com/images/cheese.jpg", 30, new BigDecimal("2.00"), "Dairy"),
				new InventoryItem("Lettuce", "https://example.com/images/lettuce.jpg", 100, new BigDecimal("0.75"), "Produce"),
				new InventoryItem("Tomatoes", "https://example.com/images/tomatoes.jpg", 75, new BigDecimal("1.25"), "Produce"),
				new InventoryItem("Beef Patty", "https://example.com/images/beef-patty.jpg", 40, new BigDecimal("3.50"), "Meat"),
				new InventoryItem("Buns", "https://example.com/images/buns.jpg", 50, new BigDecimal("1.50"), "Bakery"),
				new InventoryItem("Lettuce", "https://example.com/images/lettuce.jpg", 100, new BigDecimal("0.75"), "Produce"),
				new InventoryItem("Tomatoes", "https://example.com/images/tomatoes.jpg", 75, new BigDecimal("1.25"), "Produce"),
				new InventoryItem("Vegetable Patty", "https://example.com/images/veggie-patty.jpg", 60, new BigDecimal("3.00"), "Meat Alternatives"),
				new InventoryItem("Pickles", "https://example.com/images/pickles.jpg", 150, new BigDecimal("0.50"), "Condiments")
		};


		for (InventoryItem inventoryItem : inventoryItems) {
			inventoryItem.setUser(user);
			inventoryItemRepository.save(inventoryItem);
		}

		// Example menu items
		MenuItem[] menuItems = new MenuItem[] {
				new MenuItem("Cheeseburger", "https://example.com/images/cheeseburger.jpg", "A delicious cheeseburger with fresh lettuce, tomato, and cheese.", new BigDecimal("9.99"), "Burgers", true),
				new MenuItem("Veggie Burger", "https://example.com/images/veggie-burger.jpg", "A plant-based veggie burger with fresh vegetables.", new BigDecimal("8.99"), "Burgers", true)
		};

		for (MenuItem menuItem : menuItems) {
			menuItem.setUser(user);
			menuItemRepository.save(menuItem);
		}
	}
}
