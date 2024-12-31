package com.bitesync.api;

import com.bitesync.api.entity.InventoryItem;
import com.bitesync.api.entity.MenuInventory;
import com.bitesync.api.entity.MenuItem;
import com.bitesync.api.entity.Order;
import com.bitesync.api.entity.OrderItem;
import com.bitesync.api.entity.User;
import com.bitesync.api.repository.InventoryItemRepository;
import com.bitesync.api.repository.MenuItemRepository;
import com.bitesync.api.repository.OrderRepository;
import com.bitesync.api.repository.UserRepository;
import com.bitesync.api.service.MenuInventoryServiceImpl;
import com.bitesync.api.service.OrderItemServiceImpl;
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
	private MenuInventoryServiceImpl menuInventoryServiceImpl;
	private OrderRepository orderRepository;
	private OrderItemServiceImpl orderItemServiceImpl;


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
		InventoryItem[] burgerIngredients = new InventoryItem[] {
				new InventoryItem("Buns", "https://example.com/images/buns.jpg", 10, new BigDecimal("1.50"), "Bakery"),
				new InventoryItem("Cheese", "https://example.com/images/cheese.jpg", 10, new BigDecimal("2.00"), "Dairy"),
				new InventoryItem("Lettuce", "https://example.com/images/lettuce.jpg", 10, new BigDecimal("0.75"), "Produce"),
				new InventoryItem("Tomatoes", "https://example.com/images/tomatoes.jpg", 10, new BigDecimal("1.25"), "Produce"),
				new InventoryItem("Beef Patty", "https://example.com/images/beef-patty.jpg", 10, new BigDecimal("3.50"), "Meat"),
		};

		InventoryItem[] pizzaIngredients = new InventoryItem[] {
				new InventoryItem("Pizza Dough", "https://example.com/images/pizza-dough.jpg", 10, new BigDecimal("2.00"), "Bakery"),
				new InventoryItem("Mozzarella Cheese", "https://example.com/images/mozzarella.jpg", 10, new BigDecimal("3.00"), "Dairy"),
				new InventoryItem("Tomato Sauce", "https://example.com/images/tomato-sauce.jpg", 10, new BigDecimal("1.50"), "Canned Goods"),
				new InventoryItem("Fresh Basil", "https://example.com/images/basil.jpg", 10, new BigDecimal("1.00"), "Produce"),
				new InventoryItem("Olive Oil", "https://example.com/images/olive-oil.jpg", 10, new BigDecimal("4.00"), "Condiments")
		};

		for (InventoryItem item : burgerIngredients) {
			item.setUser(user);
			inventoryItemRepository.save(item);
		}

		for (InventoryItem item : pizzaIngredients) {
			item.setUser(user);
			inventoryItemRepository.save(item);
		}

		// Example menu items
		MenuItem burger = new MenuItem("Cheeseburger", "https://example.com/images/cheeseburger.jpg", "A delicious cheeseburger with fresh lettuce, tomato, and cheese.", new BigDecimal("9.99"), "Burgers", true);
		MenuItem pizzaMargherita = new MenuItem("Pizza Margherita", "https://example.com/images/pizza-margherita.jpg", "Classic Italian pizza topped with fresh mozzarella, tomato sauce, basil, and a drizzle of olive oil.", new BigDecimal("12.99"),"Main Course",true
		);

		MenuItem[] menuItems = new MenuItem[] { burger, pizzaMargherita };
		for (MenuItem item : menuItems) {
			item.setUser(user);
			menuItemRepository.save(item);
		}

		// Example inventory item menu item required pairs
		MenuInventory[] burgerRequiredIngredients = new MenuInventory[] {
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
		};

		MenuInventory[] pizzaMargheritaRequiredIngredients = new MenuInventory[] {
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
				new MenuInventory(1),
		};

		for(int i = 0; i < burgerIngredients.length; i++) {
			burgerRequiredIngredients[i].setRequiredInventoryItem(burgerIngredients[i]);
			burgerRequiredIngredients[i].setRequiredMenuItem(burger);
			menuInventoryServiceImpl.createMenuInventory(user.getId(), burgerIngredients[i].getId(), burger.getId(), burgerRequiredIngredients[i]);
		}

		for(int i = 0; i < pizzaIngredients.length; i++) {
			pizzaMargheritaRequiredIngredients[i].setRequiredInventoryItem(pizzaIngredients[i]);
			pizzaMargheritaRequiredIngredients[i].setRequiredMenuItem(pizzaMargherita);
			menuInventoryServiceImpl.createMenuInventory(user.getId(), pizzaIngredients[i].getId(), pizzaMargherita.getId(), pizzaMargheritaRequiredIngredients[i]);
		}

		// Example orders and order items
		Order order = new Order("Dingus Guy");
		order.setUser(user);
		orderRepository.save(order);

		OrderItem pizzaOrder = new OrderItem(1);
		orderItemServiceImpl.save(user.getId(), order.getId(), pizzaMargherita.getId(), pizzaOrder);

		OrderItem burgerOrder = new OrderItem(1);
		orderItemServiceImpl.save(user.getId(), order.getId(), burger.getId(), burgerOrder);

	}
}
