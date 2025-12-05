package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;
import Model.Data.Maintainable;

public class Electronic extends Item implements Maintainable {
    private String warrantyPeriod;
    private String brand;
    private String model;
    private String lastMaintenanceDate;

    public Electronic(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String warrantyPeriod, String brand, String model, String lastMaintenanceDate){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("Electronics"), location);
        this.warrantyPeriod = warrantyPeriod;
        this.brand = brand;
        this.model = model;
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
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

    @Override
    public boolean needsMaintenance() {
        return true;
    }

    @Override
    public void doMaintenance(){
        return;
    }

    @Override
    public String getLastMaintenanceDates() {
        return lastMaintenanceDate;
    }

    @Override
    public void setLastMaintenanceDates() {
        return;
    }

    public void setLastMaintenanceDates(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    @Override
    public void updateQuantity(int n) {
        setQuantity(getQuantity() + 1);
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
