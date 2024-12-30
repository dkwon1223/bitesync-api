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
				new InventoryItem("Buns", "https://example.com/images/buns.jpg", 1, new BigDecimal("1.50"), "Bakery"),
				new InventoryItem("Cheese", "https://example.com/images/cheese.jpg", 1, new BigDecimal("2.00"), "Dairy"),
				new InventoryItem("Lettuce", "https://example.com/images/lettuce.jpg", 1, new BigDecimal("0.75"), "Produce"),
				new InventoryItem("Tomatoes", "https://example.com/images/tomatoes.jpg", 1, new BigDecimal("1.25"), "Produce"),
				new InventoryItem("Beef Patty", "https://example.com/images/beef-patty.jpg", 1, new BigDecimal("3.50"), "Meat"),
		};


		for (InventoryItem inventoryItem : inventoryItems) {
			inventoryItem.setUser(user);
			inventoryItemRepository.save(inventoryItem);
		}

		// Example menu items
		MenuItem[] menuItems = new MenuItem[] {
				new MenuItem("Cheeseburger", "https://example.com/images/cheeseburger.jpg", "A delicious cheeseburger with fresh lettuce, tomato, and cheese.", new BigDecimal("9.99"), "Burgers", true),
		};

		for (MenuItem menuItem : menuItems) {
			menuItem.setUser(user);
			menuItemRepository.save(menuItem);
		}
	}
}
