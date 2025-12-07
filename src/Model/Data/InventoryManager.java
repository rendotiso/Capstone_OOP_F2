package Model.Data;

import Model.Entities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This Class handles the CRUD operations ; ensure usage of Exception Handling
public class InventoryManager {
    private List<Item> items;
    private final FileHandler fileHandler;

    public InventoryManager() {
        items = new ArrayList<>();
        this.fileHandler = new FileHandler("inventory.txt");
        loadFromFile();
    }

    // ADDITEM
    public void addItem(Item item) throws IOException {
        if(item == null) throw new IllegalArgumentException("Item cannot be null");
        items.add(item);
        savetoFile();
    }

    //DELETEITEM
    public boolean removeItem(Item item) throws IOException {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        boolean removed = items.remove(item);
        if (!removed) {
            throw new IllegalArgumentException("Item not found in inventory");
        }

        savetoFile();
        return removed;
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

    public boolean searchItem(Item item, String searchTerm) {
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
    public List<Item> searchItems(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>(items); // Return all items if search is empty
        }

        String term = searchTerm.toLowerCase().trim();
        List<Item> filteredItems = new ArrayList<>();

        for (Item item : items) {
            if (searchItem(item, term)) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
