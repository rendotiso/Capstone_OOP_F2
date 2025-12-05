package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;
import Model.Interface.Maintainable;

public class Electronic extends Item implements Maintainable {
    private String warrantyPeriod;
    private String brand;
    private String model;
    private String lastMaintenanceDate;

    public Electronic(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String warrantyPeriod, String brand, String model, String lastMaintenanceDate){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("ELECTRONICS"), location);
        this.warrantyPeriod = warrantyPeriod;
        this.brand = brand;
        this.model = model;
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    //GETTERS
    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public String getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    //SETTERS
    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setLastMaintenanceDate(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    // LACKING IMPLEMENT
    // public int getDaysUntilMaintenanceDue();
    // DESCRIPTIONDETAILS();
    @Override
    public boolean needsMaintenance() {
        return true;
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
