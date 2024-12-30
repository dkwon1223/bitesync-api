package com.bitesync.api;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.entity.MenuInventory;
import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.entity.User;
import com.bitesync.api.repository.InventoryItemRepository;
import com.bitesync.api.repository.MenuInventoryRepository;
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
	private MenuInventoryRepository menuInventoryRepository;


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
				new InventoryItem("Buns", "https://example.com/images/buns.jpg", 10, new BigDecimal("1.50"), "Bakery"),
				new InventoryItem("Cheese", "https://example.com/images/cheese.jpg", 10, new BigDecimal("2.00"), "Dairy"),
				new InventoryItem("Lettuce", "https://example.com/images/lettuce.jpg", 10, new BigDecimal("0.75"), "Produce"),
				new InventoryItem("Tomatoes", "https://example.com/images/tomatoes.jpg", 10, new BigDecimal("1.25"), "Produce"),
				new InventoryItem("Beef Patty", "https://example.com/images/beef-patty.jpg", 10, new BigDecimal("3.50"), "Meat"),
		};


		for (InventoryItem inventoryItem : inventoryItems) {
			inventoryItem.setUser(user);
			inventoryItemRepository.save(inventoryItem);
		}

		// Example menu items
		MenuItem burger = new MenuItem("Cheeseburger", "https://example.com/images/cheeseburger.jpg", "A delicious cheeseburger with fresh lettuce, tomato, and cheese.", new BigDecimal("9.99"), "Burgers", true);
		burger.setUser(user);
		menuItemRepository.save(burger);

		MenuInventory[] requiredIngredients = new MenuInventory[] {
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
		};

		for(int i = 0; i < inventoryItems.length; i++) {
			requiredIngredients[i].setRequiredInventoryItem(inventoryItems[i]);
			requiredIngredients[i].setRequiredMenuItem(burger);
			menuInventoryRepository.save(requiredIngredients[i]);
		}
	}
}
