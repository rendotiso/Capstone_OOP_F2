package Model.Entities;

import Model.Interface.Maintainable;
import Model.Enums.Category;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Tool extends Item implements Maintainable {
    //attributes
    private String toolType;
    private String material;
    private String steelGrade;

    //maintenance attributes
    private boolean maintenanceNeeded;
    private LocalDate lastMaintenanceDate;
    private int maintenanceIntervalDays;

    public Tool(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String toolType, String size, String material, boolean maintenanceNeeded) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("TOOLS"), location);
        this.toolType = toolType;
        this.material = material;
        this.steelGrade = size;
        this.maintenanceNeeded = true;
    }

    //GETTERS
    public String getToolType() {
        return toolType;
    }
    public String getMaterial() {
        return material;
    }
    public String getSize(){ return steelGrade;}
    public boolean getMaintenanceNeeded() {
        return maintenanceNeeded;
    }
    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
    public int getMaintenanceIntervalDays() {
        return maintenanceIntervalDays;
    }

    //SETTERS
    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
    public void setSize(String size) {this.steelGrade = size;}
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setMaintenanceNeeded(boolean maintenanceNeeded) {
        this.maintenanceNeeded = maintenanceNeeded;
    }
    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
    public void setMaintenanceIntervalDays(int days) {
        this.maintenanceIntervalDays = days;
    }

    //METHODS
    @Override
    public boolean needsMaintenance() {
        return getDaysUntilMaintenanceDue() <= 0;
    }
    @Override
    public int getDaysUntilMaintenanceDue() {
        if (lastMaintenanceDate == null) {
            return 0;
        }
        LocalDate nextMaintenanceDate = lastMaintenanceDate.plusDays(maintenanceIntervalDays);
        LocalDate today = LocalDate.now();

        if (today.isBefore(nextMaintenanceDate)) {
            return (int) ChronoUnit.DAYS.between(today, nextMaintenanceDate);
        } else {
            return 0;
        }
    }
    @Override
    public String descriptionDetails() {
        String maintenanceStatus = needsMaintenance() ? "NEEDS MAINTENANCE" : "OK";
        int daysLeft = getDaysUntilMaintenanceDue();
        return String.format("%s | Type: %s, Size: %s, Material: %s | Maintenance: %s (%d days)",
                super.descriptionDetails(), toolType, steelGrade, material, maintenanceStatus, daysLeft);
    }
    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
