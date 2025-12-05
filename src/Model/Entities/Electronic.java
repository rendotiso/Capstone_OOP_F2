package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public class Electronic extends Item implements Model.Entities.Maintenanable {
    private String warrantyPeriod;
    private String brand;
    private String model;
    private boolean maintenanceNeeded;

    public Electronic(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String warrantyPeriod, String brand, String model, boolean maintenanceNeeded){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("Electronics"), location);
        this.warrantyPeriod = warrantyPeriod;
        this.brand = brand;
        this.model = model;
        this.maintenanceNeeded = maintenanceNeeded;
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

    public boolean getMaintenanceNeeded() { return maintenanceNeeded; }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMaintenanceNeeded(boolean maintenanceNeeded) {
        this.maintenanceNeeded = maintenanceNeeded;
    }

    @Override
    public boolean needsMaintenance() {
        return true;
    }

    @Override
    public int getDaysUntilMaintenanceDue() {
        return 0;
    }

    @Override
    public String descriptionDetails(){
        return "";
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
