package Model.Data;

import Model.Enums.Category;
import Model.Entities.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String filePath = ".inventory.txt";

    public void saveData(List<Item> items){
        if (items == null) throw new IllegalArgumentException("Item List is empty.");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("CATEGORY,NAME,DESCRIPTION,QUANTITY,PURCHASE_PRICE,PURCHASE_DATE," +
                    "VENDOR,LOCATION,ATTRIBUTE1,ATTRIBUTE2,ATTRIBUTE3,ATTRIBUTE4," +
                    "ATTRIBUTE5,ATTRIBUTE6");
            bw.newLine();
            for (Item item : items) {
                String line = convertToLine(item);
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            System.err.println("Failed to save data " + e.getMessage());
        }
    }
    public List<Item> loadData() throws IOException {
        List<Item> items = new ArrayList<>();

        File file = new File(filePath);
        if (!file.exists()) return items;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip Header
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Item item = convertToItem(line);
                if (item != null) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    //HELPER METHOD FOR SAVE DATA
    private String convertToLine(Item item) {
        String base = item.getCategory() + "," +
                item.getName() + "," +
                item.getDescription() + "," +
                item.getQuantity() + "," +
                item.getPurchasePrice() + "," +
                item.getPurchaseDate() + "," +
                item.getVendor() + "," +
                item.getLocation();

        return switch (item) {
            case Clothing clothing -> base + "," + clothing.getSize() + "," +
                    clothing.getCondition() + "," + clothing.getFabricType();
            case Electronic electronic -> base + "," + electronic.getWarrantyPeriod() + "," +
                    electronic.getBrand() + "," + electronic.getModel() + "," +
                    electronic.getMaintenanceNeeded() + "," +
                    electronic.getLastMaintenanceDate() + "," +
                    electronic.getMaintenanceIntervalDays();
            case Food food -> base + "," + food.getExpiryDate() +
                    "," + food.getIsPerishable();
            case Tool tool -> base + "," + tool.getToolType() + "," +
                    tool.getSteelGrade() + "," + tool.getMaterial() + "," +
                    tool.getMaintenanceNeeded() + "," +
                    tool.getLastMaintenanceDate() + "," +
                    tool.getMaintenanceIntervalDays();
            case Miscellaneous miscellaneous -> base + "," + miscellaneous.getItemType() + "," +
                    miscellaneous.getUsage() + "," +
                    miscellaneous.getCondition();
            default -> base;
        };
    }

    // HELPER METHOD FOR LOAD DATA
    private Item convertToItem(String curr) {
        String[] items = curr.split(",", -1);
        try {
            Category category = Category.valueOf(items[0]);
            String name = items[1];
            String description = items[2];
            int quantity = Integer.parseInt(items[3]);
            double price = Double.parseDouble(items[4]);
            String purchaseDate = items[5];
            String vendor = items[6];
            String location = items[7];
            boolean maintenanceNeeded;
            String lastMaintenanceDate;
            int maintenanceintervaldays;

            switch (category) {
                case CLOTHING:
                    String size = items[8];
                    String condition = items[9];
                    String fabric = items[10];
                    return new Clothing(name, description, quantity, price, purchaseDate, vendor, location, size, condition, fabric);
                case ELECTRONICS:
                    String warranty = items[8];
                    String brand = items[9];
                    String model = items[10];
                    maintenanceNeeded = Boolean.parseBoolean(items[11]);
                    lastMaintenanceDate = items[12];
                    maintenanceintervaldays = Integer.parseInt(items[13]);
                    return new Electronic(name, description, quantity, price, purchaseDate, vendor, location,
                            warranty, brand, model, maintenanceNeeded, lastMaintenanceDate,maintenanceintervaldays);
                case FOOD:
                    String expiry = items[8];
                    boolean isPerishable = Boolean.parseBoolean(items[9]);
                    return new Food(name, description, quantity, price, purchaseDate, vendor, location, expiry, isPerishable);
                case TOOLS:
                    String toolType = items[8];
                    String steelGrade = items[9];
                    String material = items[10];
                    maintenanceNeeded  = Boolean.parseBoolean(items[11]);
                    lastMaintenanceDate = items[12];
                    maintenanceintervaldays = Integer.parseInt(items[13]);
                    return new Tool(name, description, quantity, price, purchaseDate, vendor, location, toolType, steelGrade, material, maintenanceNeeded,lastMaintenanceDate,maintenanceintervaldays);
                case MISCELLANEOUS:
                    String itemType = items[8];
                    String usage = items[9];
                    String Condition = items[10];
                    return new Miscellaneous(name, description, quantity, price, purchaseDate, vendor, location, itemType, usage, Condition);
            }
        } catch (Exception e) {
            System.err.println("Error parsing line: " + curr);
        }
        return null;
    }

}