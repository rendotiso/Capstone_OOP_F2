package Model.Data;

import Model.Entities.*;
import Model.Data.FileHandler;
import Model.Enums.Category;
import Model.Enums.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// This Class handles the CRUD operations ; ensure usage of Exception Handling
public class InventoryManager {
    private List<Item> items;
    private final FileHandler fileHandler;

    public InventoryManager(List<Item> items, FileHandler fileHandler) {
        this.items = items;
        this.fileHandler = fileHandler;
    }

    // ADDITEM
    public void addItem(Item item){
        items.add(item);
        return;
    }

  //DELETEITEM
    public void removeItem(Item item){
        items.remove(item);
        return;
    }

  //UPDATEITEM
  public void updateItem(int index, Item updatedItem) {
      if (index >= 0 && index < items.size()) {
          items.set(index, updatedItem);
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
    public List<Item> getbyLocation(Location location) {
        return items.stream().filter(item -> item.getLocation() == location).collect(Collectors.toList());
    }


    // save and load file
}
