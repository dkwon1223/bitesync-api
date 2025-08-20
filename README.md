# BiteSync REST API

A comprehensive restaurant inventory management system built with Spring Boot, featuring advanced business logic for menu item cost calculation, real-time inventory tracking, and order processing with automatic stock deduction.

## 🚀 Live Demo

[API Documentation](your-deployment-url-here/api)

## 📋 Overview

BiteSync provides a complete backend solution for restaurant operations, enabling owners to manage inventory, create menu items with automatic cost calculations, process orders with real-time inventory updates, and maintain data integrity across complex business relationships.

## ✨ Key Features

- **Advanced Inventory Management**: Real-time stock tracking with automatic availability calculations
- **Dynamic Menu Costing**: Automatic cost-to-make calculations based on ingredient requirements
- **Order Processing System**: Complete order lifecycle with inventory deduction and validation
- **JWT Authentication**: Secure user authentication with role-based access control
- **Complex Business Logic**: Multi-entity relationships with transactional integrity
- **Comprehensive Validation**: Custom exception handling with detailed error responses

## 🛠️ Tech Stack

**Backend Framework**
- Spring Boot 3.4.1 with Java 17
- Spring Security for authentication and authorization
- Spring Data JPA with Hibernate ORM
- H2 Database (development) / MySQL (production)

**Security & Authentication**
- JWT (JSON Web Tokens) with Auth0 library
- BCrypt password encryption
- Custom authentication filters and managers

**Build & Development**
- Maven for dependency management
- Spring Boot DevTools for hot reload
- Lombok for code generation
- Bean Validation (JSR-303) for data validation

## 🏗️ Architecture Overview

```
src/main/java/com/bitesync/api/
├── controller/          # REST API endpoints
├── entity/             # JPA entities with relationships
├── repository/         # Data access layer
├── service/           # Business logic implementation
├── security/          # Authentication & authorization
└── exception/         # Custom exception handling
```

## 📊 Domain Model

### Core Entities
- **User**: Restaurant owners with secure authentication
- **InventoryItem**: Raw ingredients with quantity tracking
- **MenuItem**: Menu offerings with dynamic cost calculations
- **MenuInventory**: Junction entity linking menu items to required ingredients
- **Order**: Customer orders with status tracking
- **OrderItem**: Individual items within orders

### Business Logic Highlights

**Automatic Cost Calculation**
```java
// Dynamic cost-to-make calculation based on ingredient requirements
private void updateMenuItemCostToMake(MenuItem menuItem) {
    List<MenuInventory> menuInventories = menuInventoryRepository
        .findByRequiredMenuItemId(menuItem.getId());
    
    BigDecimal totalCost = menuInventories.stream()
        .map(mi -> mi.getRequiredInventoryItem().getUnitPrice()
            .multiply(BigDecimal.valueOf(mi.getQuantityNeeded())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    menuItem.setCostToMake(totalCost);
}
```

**Inventory Validation & Deduction**
```java
// Real-time inventory checking with atomic updates
@Transactional
private void updateInventory(MenuItem targetMenuItem, Integer orderQuantity) {
    // Validate sufficient inventory before processing
    // Atomic inventory deduction with rollback on failure
}
```

## 🔐 Security Implementation

### JWT Authentication Flow
1. User credentials validated against BCrypt-encrypted passwords
2. JWT token generated with user claims and expiration
3. Token-based authorization for protected endpoints
4. Custom filters for authentication and exception handling

### CORS Configuration
- Configurable origins for frontend integration
- Secure header management
- Credential support for authenticated requests

## 📡 API Endpoints

### Authentication
```
POST /api/user/signup     # User registration with validation
POST /api/user/authenticate # Login with JWT token generation
```

### Inventory Management
```
GET    /api/inventory/user/{userId}                    # Get user inventory
POST   /api/inventory/user/{userId}                    # Create inventory item
PUT    /api/inventory/user/{userId}/item/{itemId}      # Update inventory
DELETE /api/inventory/user/{userId}/item/{itemId}      # Delete inventory
```

### Menu Management
```
GET    /api/menu/user/{userId}                         # Get user menu items
POST   /api/menu/user/{userId}                         # Create menu item
PUT    /api/menu/user/{userId}/item/{menuItemId}       # Update menu item
DELETE /api/menu/user/{userId}/item/{menuItemId}       # Delete menu item
```

### Order Processing
```
GET    /api/order/user/{userId}/all                    # Get user orders
POST   /api/order/user/{userId}                        # Create order
POST   /api/order-item/user/{userId}/order/{orderId}/menu-item/{menuItemId} # Add order item
```

## 🎯 Advanced Features

### Dynamic Menu Availability
Automatic menu item availability calculation based on current inventory levels:
```java
public void updateMenuItemAvailability(Long userId, Long menuItemId) {
    boolean isAvailable = menuInventoryList.stream()
        .allMatch(menuInventory -> {
            InventoryItem inventoryItem = menuInventory.getRequiredInventoryItem();
            return inventoryItem.getQuantity() >= menuInventory.getQuantityNeeded();
        });
}
```

### Transactional Order Processing
- Inventory validation before order confirmation
- Atomic stock deduction across multiple ingredients
- Automatic order total calculation
- Rollback on insufficient inventory

### Custom Exception Handling
```java
@ExceptionHandler(InsufficientInventoryException.class)
public ResponseEntity<ErrorResponse> handleInsufficientInventory(
    InsufficientInventoryException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
}
```

## 🚀 Quick Start

1. **Clone and Setup**
   ```bash
   git clone [repository-url]
   cd bitesync-api
   ./mvnw clean install
   ```

2. **Database Configuration**
   ```properties
   # application.properties
   spring.datasource.url=jdbc:h2:mem:bitesync
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Run Application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access H2 Console**
   ```
   http://localhost:8080/api/h2
   ```

## 🔧 Build & Deployment

```bash
./mvnw clean package          # Build JAR
./mvnw spring-boot:run        # Development server
./mvnw test                   # Run tests
```

## 📊 Data Validation

Comprehensive validation using Bean Validation:
- Email format validation for users
- Positive values for prices and quantities
- Custom password strength requirements
- Non-null constraints with meaningful error messages

## 🎯 Business Logic Examples

### Complex Relationship Management
```java
// Creating menu inventory relationships with cost updates
@Override
public MenuInventory createMenuInventory(Long userId, Long inventoryItemId, 
    Long menuItemId, MenuInventory menuInventory) {
    
    InventoryItem targetInventoryItem = findInventoryItem(inventoryItemId);
    MenuItem targetMenuItem = findMenuItem(menuItemId);
    
    menuInventory.setRequiredInventoryItem(targetInventoryItem);
    menuInventory.setRequiredMenuItem(targetMenuItem);
    
    updateMenuItemCostToMake(targetMenuItem); // Automatic cost calculation
    
    return menuInventoryRepository.save(menuInventory);
}
```

### Error Handling Strategy
- Custom exceptions for specific business cases
- Detailed error messages for API consumers
- HTTP status code mapping for different error types
- Validation error aggregation

## 📈 Performance Considerations

- Lazy loading for entity relationships
- Transaction boundaries for consistency
- Optimized queries with JPA repositories
- Connection pooling for database efficiency

## 🔒 Security Best Practices

- Password encryption with BCrypt
- JWT token expiration management
- SQL injection prevention with parameterized queries
- Input validation at multiple layers

---

*Built with Spring Boot 3.4.1, Spring Security, and JPA | Designed for scalable restaurant operations*
