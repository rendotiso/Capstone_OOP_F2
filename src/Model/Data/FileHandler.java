package Model.Data;

import Model.Enums.Category;
import Model.Entities.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
        private static final String filePath = ".inventory.csv";

        // save data
        public void saveData(List<Item> items) throws IOException{
            if(items == null) throw new IllegalArgumentException("Item List is empty.");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (Item item : items) {
                    String line = convertToLine(item);
                    bw.write(line);
                    bw.newLine();
                }
                bw.flush();
            } catch (IOException e){
                System.err.println("Failed to save data " + e.getMessage());
            }
        }

        //load data
        public List<Item> loadData() throws IOException{
            List<Item> items = new ArrayList<>(); String curr; File file = new File(filePath);

            if(!file.exists()) return items;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                while ((curr = reader.readLine()) != null) {
                    if (!curr.trim().isEmpty()) {
                        Item item = convertToItem(curr);
                        if (item != null) {
                            items.add(item);
                        }
                    }
                }
            } catch (IOException e){
                System.err.println("Failed to load data " + e.getMessage());
            }
            return items;
        }


        //HELPER METHOD FOR SAVE DATA
        private String convertToLine(Item item){
            String item1 = item.getName()+","+item.getDescription()+","+item.getQuantity()+","+item.getPurchasePrice()+
                    ","+item.getVendor()+","+item.getCategory()+","+item.getLocation();

            return switch (item) {
                case Clothing clothing -> item1 + "," + clothing.getCondition() + "," + clothing.getFabricType() +
                        "," + clothing.getSize();
                case Electronic electronic -> item1 + "," + electronic.getWarrantyPeriod() + "," + electronic.getBrand() +
                                "," + electronic.getModel() + "," + electronic.getMaintenanceNeeded() +
                                "," + electronic.getLastMaintenanceDate();
                case Food food -> item1 + "," + food.getExpiryDate() + "," +
                        food.getIsCanned() + "," + food.getIsPerishable();
                case Miscellaneous miscellaneous -> item1 = "," + miscellaneous.getItemType();
                default -> item1;
            };
        }


        // HELPER METHOD FOR LOAD DATA
        private Item convertToItem(String curr){
            String[] items = curr.split("\\Q" + "," + "\\E");
            try{
                Category category = Category.valueOf(items[0]);
                String name = items[1];
                String description = items[2];
                int quantity = Integer.parseInt(items[3]);
                double price = Double.parseDouble(items[4]);
                String purchaseDate = items[5];
                String vendor = items[6];
                String location = items[7];
                boolean maintenance;

                switch (category){
                   case CLOTHING :
                       String size = items[8]; String condition = items[9]; String fabric = items[10];
                       return new Clothing(name,description,quantity, price,purchaseDate, vendor, location, size, condition, fabric);
                   case ELECTRONICS:
                       String warranty = items[8]; String brand = items[9]; String model = items[10]; String warrantyPeriod = items[11]; maintenance = Boolean.parseBoolean(items[12]);
                       return new Electronic(name,description,quantity,price,purchaseDate,vendor,location,warrantyPeriod,brand,model,maintenance);
                   case FOOD:
                       String expiry = items[8]; boolean isCanned = Boolean.parseBoolean(items[9]); boolean isPerishable = Boolean.parseBoolean(items[10]);
                       return new Food(name, description, quantity, price, purchaseDate, vendor, location, expiry, isCanned, isPerishable);
                   case TOOLS:
                       String toolType = items[8]; String steelGrade = items[9]; String material = items[10]; maintenance = Boolean.parseBoolean(items[12]);
                       return  new Tool(name, description, quantity, price, purchaseDate, vendor, location, toolType, steelGrade, material, maintenance);
                   case MISCELLANEOUS:
                       String itemType = items[8]; String usage = items[9]; boolean isCondition = Boolean.parseBoolean(items[10]);
                       return new Miscellaneous(name, description, quantity, price, purchaseDate, vendor, location, itemType, usage, isCondition);
               }
            } catch (Exception e){
                System.err.println("Error parsing line: " + curr);
                e.printStackTrace();
            }
            return null;
        }
}
