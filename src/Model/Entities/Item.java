package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public abstract class Item {
    private String name;
    private String description;
    private int quantity;
    private double purchasePrice;
    private String purchaseDate;
    private String vendor;
    private Category category;
    private Location location;

    public Item(String name,String description, int quantity, double purchasePrice, String purchaseDate, String vendor,Category category, Location location){
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.vendor = vendor;
        this.category = category;
        this.location = location;
    }
    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
    
    public String getVendor() {
        return vendor;
    }

    public Category getCategory() {
        return category;
    }

    public Location getLocation() {
        return location;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    
    public void setCategory(Category category){
        this.category = category;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }

    public abstract void updateQuantity(int n);
    public abstract double calculateValue();

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
