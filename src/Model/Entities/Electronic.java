package Model.Entities;

import Model.Enums.Category;
import Model.Interface.Maintainable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Electronic extends Item implements Maintainable {
    private String warrantyPeriod;
    private String brand;
    private String model;
    private boolean maintenanceNeeded;
    private String lastMaintenanceDate;

    public Electronic(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String warrantyPeriod, String brand, String model, boolean maintenanceNeeded, String lastMaintenanceDate) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.ELECTRONICS, location);
        setWarrantyPeriod(warrantyPeriod);
        setBrand(brand);
        setModel(model);
        this.maintenanceNeeded = maintenanceNeeded;
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
    public boolean getMaintenanceNeeded() {
        return maintenanceNeeded;
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

    //METHODS
    @Override
    public int getDaysUntilMaintenanceDue() {
        if (lastMaintenanceDate == null || lastMaintenanceDate.isEmpty()) {
            return 0;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate lastMaintenance = LocalDate.parse(lastMaintenanceDate, formatter);
            LocalDate nextMaintenanceDate = lastMaintenance.plusDays(365);
            LocalDate today = LocalDate.now();
            if (today.isBefore(nextMaintenanceDate)) {
                return (int) ChronoUnit.DAYS.between(today, nextMaintenanceDate);
            } else {
                return 0;
            }
        } catch (DateTimeParseException e) {
            return 0;
        }
    }

    @Override
    public boolean needsMaintenance(){
        return getDaysUntilMaintenanceDue() <= 0;
    }
    @Override
    public String descriptionDetails() {
        String maintain;
        if(needsMaintenance()) maintain = "Yes!";
        else maintain = "No";
        return super.descriptionDetails() + String.format("Brand: %s\nModel: %s\n" +
            "Maintenance Needed: %s \nDays Until Maintenance: %d", brand, model, maintain, getDaysUntilMaintenanceDue());
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
