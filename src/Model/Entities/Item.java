package Model.Entities;

import Model.Enums.Category;

public abstract class Item {
    private String name;
    private String description;
    private int quantity;
    private double purchasePrice;
    private String purchaseDate;
    private String vendor;
    private Category category;
    private String location;

    public Item(String name, String description, int quantity, double purchasePrice, String
            purchaseDate, String vendor, Category category, String location) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.vendor = vendor;
        this.category = category;
        this.location = location;
    }

    //GETTERS
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getPurchaseDate() { return purchaseDate;}
    public double getPurchasePrice() {
        return purchasePrice;
    }
    public String getVendor() {
        return vendor;
    }
    public Category getCategory() { return category; }
    public String getLocation() { return location; }

    //SETTERS
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
    public void setCategory(Category category) { this.category = category; }
    public void setLocation(String location) { this.location = location; }

    //Global Method
    public String descriptionDetails() {
        if (description == null || description.trim().isEmpty()) {
            description = "No Description";
        }
        if(purchaseDate == null || purchaseDate.trim().isEmpty()){
            purchaseDate = "Unknown";
        }
        return String.format("%s\n------------------------\nVendor: %s\n", description, vendor);
    }

    //ABSTRACT METHODS
    public abstract double calculateValue();

}
