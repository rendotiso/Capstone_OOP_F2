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

    // Maintenance attributes
    private boolean maintenanceNeeded;
    private String lastMaintenanceDate;
    private int maintenanceIntervalDays;

    public Electronic(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String warrantyPeriod, String brand, String model, boolean maintenanceNeeded, String lastMaintenanceDate, int maintenanceIntervalDays) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.ELECTRONICS, location);
        setWarrantyPeriod(warrantyPeriod);
        setBrand(brand);
        setModel(model);
        setLastMaintenanceDate(lastMaintenanceDate);
        setMaintenanceNeeded(maintenanceNeeded);
        setMaintenanceIntervalDays(maintenanceIntervalDays);
    }

    //GETTERS
    public String getWarrantyPeriod() { return warrantyPeriod; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public boolean getMaintenanceNeeded() { return maintenanceNeeded; }
    public String getLastMaintenanceDate() { return lastMaintenanceDate; }
    public int getMaintenanceIntervalDays(){ return maintenanceIntervalDays; }

    //SETTERS
    public void setWarrantyPeriod(String warrantyPeriod) { this.warrantyPeriod = warrantyPeriod; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setLastMaintenanceDate(String lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }
    public void setMaintenanceNeeded(boolean maintenanceNeeded){ this.maintenanceNeeded = maintenanceNeeded;}
    public void setMaintenanceIntervalDays(int maintenanceIntervalDays) { this.maintenanceIntervalDays = maintenanceIntervalDays; }

    //METHODS
    @Override
    public boolean needsMaintenance() {
        if (maintenanceNeeded) {
            return true;
        }
        if (maintenanceIntervalDays <= 0) {
            return false;
        }
        return getDaysUntilMaintenanceDue() <= 0;
    }

    @Override
    public int getDaysUntilMaintenanceDue() {
        if (maintenanceIntervalDays <= 0) {
            return Integer.MAX_VALUE;
        }
        if (lastMaintenanceDate == null || lastMaintenanceDate.trim().isEmpty()) {
            return 0;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate lastMaintenance = LocalDate.parse(lastMaintenanceDate, formatter);

            LocalDate nextMaintenanceDate = lastMaintenance.plusDays(maintenanceIntervalDays);
            LocalDate today = LocalDate.now();

            return (int) ChronoUnit.DAYS.between(today, nextMaintenanceDate);

        } catch (DateTimeParseException e) {
            return 0;
        }
    }

    @Override
    public String descriptionDetails() {
        String statusMessage;
        int daysRemaining = getDaysUntilMaintenanceDue();
        if (maintenanceNeeded) {
            statusMessage = "Urgent! Maintenance Needed!";
        }
        else if (maintenanceIntervalDays > 0) {
            if (daysRemaining < 0) {
                statusMessage = "OVERDUE (" + Math.abs(daysRemaining) + " days late)";
            } else if (daysRemaining == 0) {
                statusMessage = "DUE TODAY";
            } else {
                statusMessage = "Good (" + daysRemaining + " days remaining)";
            }
        }
        else {
            statusMessage = "No Schedule Set";
        }

        return String.format("""
                        %s\
                        Brand: %s
                        Model: %s
                        Warranty: %s
                        Status: %s""",
                super.descriptionDetails(),
                brand,
                model,
                warrantyPeriod,
                statusMessage);
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}