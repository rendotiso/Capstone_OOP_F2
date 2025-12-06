package Model.Data;

import Model.Enums.Category;
import Model.Enums.Location;
import Model.Entities.*;
import Model.Data.InventoryManager;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandler {
        private static final String filePath = ".inventory.csv";

        // save data
        public void savedata(){
            String content = "This is some text to save to a file.";
            Path filePath = Paths.get("myFile.txt"); // Or provide an absolute path

            try {
                // Write the string to the file, creating it if it doesn't exist
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("String saved to file successfully!");
            } catch (IOException e) {
                System.err.println("Error saving string to file: " + e.getMessage());
            }
            return;
        }

        // load data
        public void loaddata(){
            return;
        }

        // helper methods below (convertToLine & backToItem)
        private String convertToLine(Item item){
            String item1=item.getName()+","+item.getDescription()+","+item.getQuantity()+","+item.getPurchasePrice()+","+item.getVendor()+","+item.getCategory()+","+item.getLocation();
            if(item instanceof Clothing){
                return item1+","+((Clothing) item).getCondition()+","+((Clothing) item).getFabricType()+","+((Clothing) item).getSize();
            } else if (item instanceof  Electronic) {
                return item1+","+((Electronic) item).getWarrantyPeriod()+","+((Electronic) item).getBrand()+","+((Electronic) item).getModel()+","+((Electronic) item).getMaintenanceNeeded()+","item.getLastMaintenanceDate();
            }else if (item instanceof  Food) {
                return item1+","+((Food) item).getExpiryDate()+","+((Food) item).getIsCanned()+","+((Food) item).getIsPerishable();
            }else if (item instanceof  Miscellaneous) {
                return item1=","+((Miscellaneous)item).getItemType();
            }
        }
}
