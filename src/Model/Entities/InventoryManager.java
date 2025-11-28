package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public class InventoryManager {
    private Item[] items;

    public InventoryManager(Item[] items){
        this.items=items;
    }

    public Item addItem(Item item){
        int s=items.length;
        items[s++]=item;
    }

    public Item removeItem(Item item){

    }

    public Item[] getAll(){
        return items;
    }

    public Item[] getItembyCategory(Category category){
        Item[] itemList;
        int c=0;
        for(Item item:items) c++;
        itemList=new Item[c+1];
        c=0;
        for(Item i:items){
            if(Item.getCategory()==category){
                itemList[c++]=i;
            }
        }
        return itemList;
    }

    public Item[] getItembyLocation(Location location){
        Item[] itemList;
        int c=0;
        for(Item item:items) c++;
        itemList=new Item[c+1];
        c=0;
        for(Item i:items){
            if(Item.getLocation()==location){
                itemList[c++]=i;
            }
        }
        return itemList;
    }

    public int getItemCount(){
        int i=0;
        for(Item item:items) i++;
        return i;
    }
}
