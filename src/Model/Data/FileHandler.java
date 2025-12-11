package Model.Data;

import Model.Enums.Category;
import Model.Entities.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static String filePath = ".inventory.txt";

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

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    Item item = convertToItem(line);
                    items.add(item);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line: " + line);
                    e.printStackTrace();
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
            case Food food -> base + "," + food.getExpiryDate() + "," +
                    food.getDietaryInfo() + "," + food.getIsPerishable();
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
                    boolean maintenanceNeeded = Boolean.parseBoolean(items[11]);
                    // Handle old data that might not have lastMaintenanceDate
                    String lastMaintenanceDate = (items.length > 12 && !items[12].isEmpty()) ? items[12] : "";
                    // Handle old data that might not have maintenanceIntervalDays
                    int maintenanceIntervalDays = (items.length > 13 && !items[13].isEmpty()) ? Integer.parseInt(items[13]) : 0;
                    return new Electronic(name, description, quantity, price, purchaseDate, vendor, location,
                            warranty, brand, model, maintenanceNeeded, lastMaintenanceDate, maintenanceIntervalDays);
                case FOOD:
                    String expiry = items[8];
                    // Check if the data is old format (isCanned boolean) or new format (dietaryInfo string)
                    String dietaryInfo;
                    if (items.length > 9) {
                        // Try to parse as boolean (old format), if fails use as string (new format)
                        try {
                            boolean isCanned = Boolean.parseBoolean(items[9]);
                            dietaryInfo = isCanned ? "Canned" : "N/A";
                        } catch (Exception e) {
                            dietaryInfo = items[9];
                        }
                    } else {
                        dietaryInfo = "N/A";
                    }
                    boolean isPerishable = (items.length > 10) ? Boolean.parseBoolean(items[10]) : false;
                    return new Food(name, description, quantity, price, purchaseDate, vendor, location, expiry, dietaryInfo, isPerishable);
                case TOOLS:
                    String toolType = items[8];
                    String steelGrade = items[9];
                    String material = items[10];
                    boolean maintenanceNeededTool = Boolean.parseBoolean(items[11]);
                    String lastMaintenanceDateTool = items[12];
                    int maintenanceIntervalDaysTool = Integer.parseInt(items[13]);
                    return new Tool(name, description, quantity, price, purchaseDate, vendor, location, toolType, steelGrade, material, maintenanceNeededTool, lastMaintenanceDateTool, maintenanceIntervalDaysTool);
                case MISCELLANEOUS:
                    String itemType = items[8];
                    String usage = items[9];
                    String Condition = items[10];
                    return new Miscellaneous(name, description, quantity, price, purchaseDate, vendor, location, itemType, usage, Condition);
            }
        } catch (Exception e) {
            System.err.println("Error parsing line: " + curr);
            e.printStackTrace();
        }
        return null;
    }

}