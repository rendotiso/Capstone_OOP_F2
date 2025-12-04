package Model.Entities;
package Controller;
import Model.Enums.Category;
import Model.Enums.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public class FileHandler {
        private static final String DATA_DIR = ".inventory_data";
        private static final String FILE_PATH = DATA_DIR + "/inventory.txt";
        private static final String DELIMITER = "|||";

        // EXCEPTION HANDLING: Checked exceptions with meaningful messages
        public void saveItems(List<Item> items) throws IOException {
            ensureDataDirectory();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Item item : items) {
                    String line = convertToLine(item);
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                throw new IOException("Failed to save inventory: " + e.getMessage(), e);
            }
        }

        public List<Item> loadItems() throws IOException {
            List<Item> items = new ArrayList<>();
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return items; // Return empty list if file doesn't exist
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        Item item = parseLine(line);
                        if (item != null) {
                            items.add(item);
                        }
                    }
                }
            } catch (IOException e) {
                throw new IOException("Failed to load inventory: " + e.getMessage(), e);
            } catch (Exception e) {
                throw new IOException("Invalid data format in file", e);
            }

            return items;
        }

        private void ensureDataDirectory() throws IOException {
            File dir = new File(DATA_DIR);
            if (!dir.exists() && !dir.mkdir()) {
                throw new IOException("Cannot create data directory: " + DATA_DIR);
            }
        }

        private String convertToLine(Item item) {
            StringBuilder sb = new StringBuilder();
            sb.append(item.getCategory().name()).append(DELIMITER);
            sb.append(item.getName()).append(DELIMITER);
            sb.append(item.getDescription()).append(DELIMITER);
            sb.append(item.getQuantity()).append(DELIMITER);
            sb.append(item.getPurchasePrice()).append(DELIMITER);
            sb.append(item.getPurchaseDate()).append(DELIMITER);
            sb.append(item.getVendor()).append(DELIMITER);
            sb.append(item.getLocation().name());

            // POLYMORPHISM: Different handling based on actual type
            switch (item) {
                case Clothing c -> {
                    sb.append(DELIMITER).append(c.getSize());
                    sb.append(DELIMITER).append(c.getCondition());
                    sb.append(DELIMITER).append(c.getFabricType());
                }
                case Electronics e -> {
                    sb.append(DELIMITER).append(e.getWarrantyPeriod());
                    sb.append(DELIMITER).append(e.getBrand());
                    sb.append(DELIMITER).append(e.getModel());
                    sb.append(DELIMITER).append(e.getLastMaintenanceDate());
                }
                case Food f -> {
                    sb.append(DELIMITER).append(f.getExpiryDate());
                    sb.append(DELIMITER).append(f.getIsCanned());
                    sb.append(DELIMITER).append(f.getIsPerishable());
                }
                case Tool t -> {
                    sb.append(DELIMITER).append(t.getToolType());
                    sb.append(DELIMITER).append(t.getMaterial());
                    sb.append(DELIMITER).append(t.getMaintenanceFrequencyDays());
                    sb.append(DELIMITER).append(t.getLastMaintenanceDate());
                }
                case Miscellaneous m -> {
                    sb.append(DELIMITER).append(m.getItemType());
                    sb.append(DELIMITER).append(m.getUsage());
                    sb.append(DELIMITER).append(m.getIsCondition());
                }
                default -> {
                }
            }

            return sb.toString();
        }

        private Item parseLine(String line) {
            try {
                String[] parts = line.split("\\Q" + DELIMITER + "\\E", -1);
                Category category = Category.valueOf(parts[0]);

                String name = parts[1];
                String description = parts[2];
                int quantity = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);
                String purchaseDate = parts[5];
                String vendor = parts[6];
                Location location = Location.valueOf(parts[7]);

                switch (category) {
                    case Clothing:
                        char size = parts[8].charAt(0);
                        String condition = parts[9];
                        String fabric = parts[10];
                        return new Clothing(name, description, quantity, price, purchaseDate,
                                vendor, location, condition, fabric, size);

                    case Electronics:
                        String warranty = parts[8];
                        String brand = parts[9];
                        String model = parts[10];
                        String lastMaintenance = parts.length > 11 ? parts[11] : "";
                        Electronics e = new Electronics(name, description, quantity, price, purchaseDate,
                                vendor, location, warranty, brand, model);
                        if (!lastMaintenance.isEmpty()) {
                            e.setLastMaintenanceDate(lastMaintenance);
                        }
                        return e;

                    case Food:
                        String expiry = parts[8];
                        boolean isCanned = Boolean.parseBoolean(parts[9]);
                        boolean isPerishable = Boolean.parseBoolean(parts[10]);
                        return new Food(name, description, quantity, price, purchaseDate,
                                vendor, location, expiry, isCanned, isPerishable);

                    case Tools:
                        String toolType = parts[8];
                        String material = parts[9];
                        int maintenanceFrequencyDays = Integer.parseInt(parts[10]);
                        String toolMaintenance = parts.length > 11 ? parts[11] : "";
                        Tool t = new Tool(name, description, quantity, price, purchaseDate,
                                vendor, location, toolType, material, maintenanceFrequencyDays);
                        if (!toolMaintenance.isEmpty()) {
                            t.setLastMaintenanceDate(toolMaintenance);
                        }
                        return t;

                    case Miscellaneous:
                        String itemType = parts[8];
                        String usage = parts[9];
                        boolean isCondition = Boolean.parseBoolean(parts[10]);
                        return new Miscellaneous(name, description, quantity, price, purchaseDate,
                                vendor, location, itemType, usage, isCondition);
                }
            } catch (Exception e) {
                System.err.println("Error parsing line: " + line);
                e.printStackTrace();
            }
            return null;
        }
    }
}
