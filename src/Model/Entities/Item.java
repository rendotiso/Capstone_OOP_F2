package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public abstract class Item {
    private String name;
    private String description;
    private int quantity;
    private double purchasePrice;
    String purchaseDate;
    private String vendor;
    private Category category;
    private Location location;

    public Item(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Category category, Location location) {

    }

    public static boolean getCategory() {
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


    public abstract void updateQuantity(int n);

    public abstract double calculateValue();

}
