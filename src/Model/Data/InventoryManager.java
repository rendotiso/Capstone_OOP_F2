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
    private FileHandler fileHandler;

    public InventoryManager(List<Item> items, FileHandler fileHandler) throws IOException {
        this.items = fileHandler.loadData();
        this.fileHandler = fileHandler;
    }

    // ADDITEM
    public void addItem(Item item) throws IOException {
        items.add(item);
        fileHandler.saveData(items);
        return;
    }

  //DELETEITEM
    public void removeItem(Item item) throws IOException {
        items.remove(item);
        fileHandler.saveData(items);
        return;
    }

  //UPDATEITEM
  public void updateItem(int index, Item updatedItem) throws IOException {
      if (index >= 0 && index < items.size()) {
          items.set(index, updatedItem);
          fileHandler.saveData(items);
          return;
      }
  }

  // GetAllItems, returns the List
    public List<Item> GetAllItems() {
        return items;
    }

  // search and filter (get by category, get by location)

    public List<Item> getbyCategory(Category category) {
        return items.stream().filter(item -> item.getCategory() == category).collect(Collectors.toList());
    }
    public List<Item> getbyLocation(String location) {
        return items.stream().filter(item -> Objects.equals(item.getLocation(), location)).collect(Collectors.toList());
    }
    public Item getItem(String name){
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }


    // save and load file
}
