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
				new InventoryItem("Tomatoes", "https://example.com/images/tomatoes.jpg", 50, new BigDecimal("0.50"), "Produce"),
				new InventoryItem("Chicken Breast", "https://example.com/images/chicken_breast.jpg", 20, new BigDecimal("5.99"), "Meat"),
				new InventoryItem("Olive Oil", "https://example.com/images/olive_oil.jpg", 10, new BigDecimal("9.99"), "Condiments"),
				new InventoryItem("Mozzarella Cheese", "https://example.com/images/mozzarella_cheese.jpg", 15, new BigDecimal("3.50"), "Dairy"),
				new InventoryItem("French Fries", "https://example.com/images/french_fries.jpg", 30, new BigDecimal("2.99"), "Frozen Foods")
		};

		for (InventoryItem inventoryItem : inventoryItems) {
			inventoryItem.setUser(user);
			inventoryItemRepository.save(inventoryItem);
		}

		// Example menu items
		MenuItem[] menuItems = new MenuItem[] {
				new MenuItem("Margherita Pizza", "https://example.com/images/margherita_pizza.jpg", "Classic pizza with tomato sauce, mozzarella, and fresh basil", new BigDecimal("12.99"), "Pizza", true),
				new MenuItem("Caesar Salad", "https://example.com/images/caesar_salad.jpg", "Crisp romaine lettuce with Caesar dressing, croutons, and Parmesan cheese", new BigDecimal("8.99"), "Salads", true),
				new MenuItem("Grilled Salmon", "https://example.com/images/grilled_salmon.jpg", "Freshly grilled salmon served with seasonal vegetables", new BigDecimal("19.99"), "Entrees", true),
				new MenuItem("Chocolate Lava Cake", "https://example.com/images/chocolate_lava_cake.jpg", "Warm chocolate cake with a gooey molten center, served with vanilla ice cream", new BigDecimal("6.99"), "Desserts", true),
				new MenuItem("Iced Coffee", "https://example.com/images/iced_coffee.jpg", "Refreshing iced coffee made with premium beans", new BigDecimal("3.99"), "Beverages", true)
		};

		for (MenuItem menuItem : menuItems) {
			menuItem.setUser(user);
			menuItemRepository.save(menuItem);
		}
	}
}
