package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;
import Model.Interface.Maintainable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Electronic extends Item implements Maintainable {
    private String warrantyPeriod;
    private String brand;
    private String model;

    //maintenance attributes
    private boolean maintenanceNeeded;
    private LocalDate lastMaintenanceDate;

    public Electronic(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String warrantyPeriod, String brand, String model, boolean maintenanceNeeded) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("Electronics"), location);
        setWarrantyPeriod(warrantyPeriod);
        setBrand(brand);
        setModel(model);
        this.maintenanceNeeded = maintenanceNeeded;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.lastMaintenanceDate = LocalDate.parse(purchaseDate, formatter);
        } catch (Exception e) {
            this.lastMaintenanceDate = LocalDate.now();
        }
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
    public boolean getMaintenanceNeeded() {
        return maintenanceNeeded;
    }
    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    //SETTERS
    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod != null ? warrantyPeriod.trim() : "";
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }


    //METHODS
    @Override
    public int getDaysUntilMaintenanceDue(){
        if (lastMaintenanceDate == null) {
            return 0;
        }
        LocalDate nextMaintenanceDate = lastMaintenanceDate.plusDays(365);
        LocalDate today = LocalDate.now();

        if (today.isBefore(nextMaintenanceDate)) {
            return (int) ChronoUnit.DAYS.between(today, nextMaintenanceDate);
        } else {
            return 0;
        }
    }
    @Override
    public boolean needsMaintenance(){
        return getDaysUntilMaintenanceDue() <= 0;
    }
    @Override
    public String descriptionDetails() {  return super.descriptionDetails() + String.format(" | Brand: %s, Model: %s", brand, model); }
    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
