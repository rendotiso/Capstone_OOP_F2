package UI.Utilities;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ItemTable {
    private static ItemTable instance;
    private final DefaultTableModel allItemsTableModel;
    private final List<ItemData> allItems;
    private final List<ItemData> temporaryItems; // For Tools panel local storage

    public static class ItemData {
        private final String category;
        private final String name;
        private final int quantity;
        private final String location;
        private final String vendor;
        private final double price;
        private final String details;

        public ItemData(String category, String name, int quantity, String location,
                        String vendor, double price, String details) {
            this.category = category;
            this.name = name;
            this.quantity = quantity;
            this.location = location;
            this.vendor = vendor;
            this.price = price;
            this.details = details;
        }

        // Getters
        public String getCategory() { return category; }
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public String getLocation() { return location; }
        public String getVendor() { return vendor; }
        public double getPrice() { return price; }
        public String getDetails() { return details; }
        public String getFormattedPrice() { return String.format("$%.2f", price); }

        @Override
        public String toString() {
            return String.format("%s,%s,%d,%s,%s,%.2f,%s",
                    category, name, quantity, location, vendor, price, details);
        }
    }

    private ItemTable() {
        String[] columnNames = {"No.", "Category", "Name", "Quantity", "Location", "Price", "Details"};
        allItemsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: // No.
                    case 3: // Quantity
                        return Integer.class;
                    case 5: // Price
                        return Double.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        allItems = new ArrayList<>();
        temporaryItems = new ArrayList<>();
    }

    public static ItemTable getInstance() {
        if (instance == null) {
            instance = new ItemTable();
        }
        return instance;
    }

    // Add to main table (AllItemsPanel)
    public void addItem(ItemData item) {
        allItems.add(item);
        updateTableModel();
    }

    // Add to temporary storage (Tools panel)
    public void addTemporaryItem(ItemData item) {
        temporaryItems.add(item);
    }

    // Get temporary items
    public List<ItemData> getTemporaryItems() {
        return new ArrayList<>(temporaryItems);
    }

    // Get temporary items count
    public int getTemporaryItemsCount() {
        return temporaryItems.size();
    }

    // Clear temporary items
    public void clearTemporaryItems() {
        temporaryItems.clear();
    }

    // Transfer all temporary items to main table
    public void transferTemporaryToMain() {
        if (temporaryItems.isEmpty()) {
            return;
        }
        allItems.addAll(temporaryItems);
        temporaryItems.clear();
        updateTableModel();
    }

    // Transfer specific temporary item to main table
    public void transferTemporaryItemToMain(int index) {
        if (index >= 0 && index < temporaryItems.size()) {
            allItems.add(temporaryItems.get(index));
            temporaryItems.remove(index);
            updateTableModel();
        }
    }

    // Remove from main table
    public void removeItem(int index) {
        if (index >= 0 && index < allItems.size()) {
            allItems.remove(index);
            updateTableModel();
        }
    }

    // Remove from temporary storage
    public void removeTemporaryItem(int index) {
        if (index >= 0 && index < temporaryItems.size()) {
            temporaryItems.remove(index);
        }
    }

    // Update item in main table
    public void updateItem(int index, ItemData newItem) {
        if (index >= 0 && index < allItems.size()) {
            allItems.set(index, newItem);
            updateTableModel();
        }
    }

    // Update item in temporary storage
    public void updateTemporaryItem(int index, ItemData newItem) {
        if (index >= 0 && index < temporaryItems.size()) {
            temporaryItems.set(index, newItem);
        }
    }

    private void updateTableModel() {
        allItemsTableModel.setRowCount(0);
        for (int i = 0; i < allItems.size(); i++) {
            ItemData item = allItems.get(i);
            allItemsTableModel.addRow(new Object[]{
                    i + 1, // No.
                    item.getCategory(),
                    item.getName(),
                    item.getQuantity(),
                    item.getLocation(),
                    item.getPrice(), // Store as double for sorting
                    item.getDetails()
            });
        }
    }

    public void searchItems(String searchText) {
        allItemsTableModel.setRowCount(0);

        if (searchText == null || searchText.trim().isEmpty()) {
            updateTableModel();
            return;
        }

        String searchLower = searchText.toLowerCase().trim();
        int displayedIndex = 1;
        for (int i = 0; i < allItems.size(); i++) {
            ItemData item = allItems.get(i);
            if (item.getName().toLowerCase().contains(searchLower) ||
                    item.getCategory().toLowerCase().contains(searchLower) ||
                    item.getLocation().toLowerCase().contains(searchLower) ||
                    item.getDetails().toLowerCase().contains(searchLower) ||
                    item.getVendor().toLowerCase().contains(searchLower)) {
                allItemsTableModel.addRow(new Object[]{
                        displayedIndex++,
                        item.getCategory(),
                        item.getName(),
                        item.getQuantity(),
                        item.getLocation(),
                        item.getPrice(),
                        item.getDetails()
                });
            }
        }
    }

    public DefaultTableModel getAllItemsTableModel() {
        return allItemsTableModel;
    }

    public List<ItemData> getAllItems() {
        return new ArrayList<>(allItems);
    }

    public List<ItemData> getItemsByCategory(String category) {
        List<ItemData> result = new ArrayList<>();
        for (ItemData item : allItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return result;
    }
}