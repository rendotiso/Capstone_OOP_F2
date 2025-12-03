package Model.Entities;
package Controller;

import Controller.FileHandler;
import Model.Enums.Category;
import Model.Enums.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryManager {
    // SINGLETON PATTERN: Only one instance
    private static InventoryManager instance;
    private List<Item> items;
    private final Model.Entities.FileHandler fileHandler;

    private InventoryManager() {
        items = new ArrayList<>();
        fileHandler = new FileHandler();
        loadFromFile();
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    // CRUD Operations
    public void addItem(Item item) {
        items.add(item);
        saveToFile();
    }

    public void updateItem(int index, Item updatedItem) {
        if (index >= 0 && index < items.size()) {
            items.set(index, updatedItem);
            saveToFile();
        }
    }

    public void deleteItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            saveToFile();
        }
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    // POLYMORPHISM: Get items by category
    public List<Item> getItemsByCategory(Category category) {
        return items.stream()
                .filter(item -> item.getCategory() == category)
                .collect(Collectors.toList());
    }

    // SEARCH FUNCTIONALITY (for ViewAllPanel)
    public List<Item> searchItems(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllItems();
        }

        String searchTerm = query.toLowerCase().trim();
        return items.stream()
                .filter(item ->
                        item.getName().toLowerCase().contains(searchTerm) ||
                                item.getDescription().toLowerCase().contains(searchTerm) ||
                                item.getVendor().toLowerCase().contains(searchTerm) ||
                                item.getCategory().name().toLowerCase().contains(searchTerm) ||
                                item.getLocation().name().toLowerCase().contains(searchTerm)
                )
                .collect(Collectors.toList());
    }

    // Filter by location
    public List<Item> getItemsByLocation(Location location) {
        return items.stream()
                .filter(item -> item.getLocation() == location)
                .collect(Collectors.toList());
    }

    // Get expiring/expired food
    public List<Food> getExpiringFood() {
        return items.stream()
                .filter(item -> item instanceof Food)
                .map(item -> (Food) item)
                .filter(Food::isExpiringSoon)
                .collect(Collectors.toList());
    }

    public List<Food> getExpiredFood() {
        return items.stream()
                .filter(item -> item instanceof Food)
                .map(item -> (Food) item)
                .filter(Food::isExpired)
                .collect(Collectors.toList());
    }

    // File operations
    private void saveToFile() {
        try {
            fileHandler.saveItems(items);
        } catch (Exception e) {
            // EXCEPTION HANDLING: Graceful error handling
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        try {
            items = fileHandler.loadItems();
        } catch (Exception e) {
            System.err.println("Error loading from file: " + e.getMessage());
            items = new ArrayList<>();
        }
    }
}