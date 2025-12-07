package Model.Entities;

import Model.Interface.Maintainable;
import Model.Enums.Category;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Tool extends Item implements Maintainable {
    //attributes
    private String toolType;
    private String material;
    private String steelGrade;

    //maintenance attributes
    private boolean maintenanceNeeded;
    private String lastMaintenanceDate;
    private int maintenanceIntervalDays;

    public Tool(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, String location, String toolType,
                String steelGrade, String material, boolean maintenanceNeeded, String lastMaintenanceDate, int maintenanceIntervalDays) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.TOOLS, location);
        this.toolType = toolType;
        this.steelGrade = steelGrade;
        this.material = material;
        this.maintenanceNeeded = true;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.maintenanceIntervalDays = maintenanceIntervalDays;
    }

    //GETTERS
    public String getToolType() {
        return toolType;
    }
    public String getMaterial() {
        return material;
    }
    public String getSteelGrade(){ return steelGrade;}
    public boolean getMaintenanceNeeded() {
        return maintenanceNeeded;
    }
    public String getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }
    public int getMaintenanceIntervalDays() {
        return maintenanceIntervalDays;
    }

    //SETTERS
    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
    public void setSteelGrade(String size) {this.steelGrade = steelGrade;}
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setMaintenanceNeeded(boolean maintenanceNeeded) {
        this.maintenanceNeeded = maintenanceNeeded;
    }
    public void setLastMaintenanceDate(String lastMaintenanceDate) {
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
    public String descriptionDetails() {
        String maintenanceStatus = needsMaintenance() ? "NEEDS MAINTENANCE" : "OK";
        int daysLeft = getDaysUntilMaintenanceDue();
        return String.format("%s Type: %s \n Steel Grade: %s \n Material: %s \n Maintenance: %s (%d days)",
                super.descriptionDetails(), toolType, steelGrade, material, maintenanceStatus, daysLeft);
    }
    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }

}
