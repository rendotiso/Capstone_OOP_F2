package Model.Data;

import Model.Entities.*;
import Model.Data.FileHandler;
import Model.Enums.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// This Class handles the CRUD operations ; ensure usage of Exception Handling
public class InventoryManager {
    private List<Item> items;
    private final FileHandler fileHandler;

    private InventoryManager() {
        items = new ArrayList<>();
        fileHandler = new FileHandler();
        loadFromFile();
    }

    // ADDITEM
    public void addItem(Item item) throws IOException {
        items.add(item);
        savetoFile();
    }

    //DELETEITEM
    public void removeItem(Item item) throws IOException {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        boolean removed = items.remove(item);
        if (!removed) {
            throw new IllegalArgumentException("Item not found in inventory");
        }

        savetoFile();
    }

    //UPDATEITEM
    public void updateItem(int index, Item item) throws IOException {
        if (item == null) {
        throw new IllegalArgumentException("Updated item cannot be null");
    }
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
            savetoFile();
        }
    }

    public List<Item> getAllItems() {
        return items;
    }

    private boolean matchesCondition(Item item, String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return true;
        }
        String term = searchTerm.toLowerCase().trim();
        return item.getName().toLowerCase().contains(term) ||
                  item.getDescription().toLowerCase().contains(term) ||
                  item.getLocation().toLowerCase().contains(term) ||
                  item.getVendor().toLowerCase().contains(term) ||
                  String.valueOf(item.getQuantity()).contains(term) ||
                  String.valueOf(item.getPurchasePrice()).contains(term) ||
                  item.getPurchaseDate().toLowerCase().contains(term) ||
                  item.getCategory().toString().toLowerCase().contains(term);
    }

    //HELPER
    private void savetoFile() {
        try {
            fileHandler.saveData(items);
        } catch (Exception e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
    private void loadFromFile() {
        try {
            items = fileHandler.loadData();
        } catch (Exception e) {
            System.err.println("Error loading from file: " + e.getMessage());
            items = new ArrayList<>();
        }
    }
}
