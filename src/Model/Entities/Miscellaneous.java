package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public class Miscellaneous extends Item {

    private String itemType;
    private String usage;
    private String condition;

    public Miscellaneous(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String itemType, String usage, String condition) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.MISCELLANEOUS, location);
        this.itemType = itemType;
        this.usage = usage;
        this.condition = condition;
    }

    //GETTERS
    public String getItemType() {
        return itemType;
    }
    public String getUsage() {
        return usage;
    }
    public String getCondition() {
        return condition;
    }

    //SETTERS
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public void setUsage(String usage) {
        this.usage = usage;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }

    //METHODS
    // MISSING METHODS
    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
