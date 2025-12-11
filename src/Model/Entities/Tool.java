package Model.Entities;

import Model.Interface.Maintainable;
import Model.Enums.Category;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Tool extends Item implements Maintainable {
    private String toolType;
    private String material;
    private String steelGrade;
    private boolean maintenanceNeeded;
    private String lastMaintenanceDate;
    private int maintenanceIntervalDays;

    public Tool(String name, String description, int quantity, double purchasePrice, String purchaseDate,
                String vendor, String location, String toolType, String steelGrade, String material, boolean maintenanceNeeded,
                String lastMaintenanceDate, int maintenanceIntervalDays) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.TOOLS, location);
        setToolType(toolType);
        setSteelGrade(steelGrade);
        setMaterial(material);
        setMaintenanceNeeded(maintenanceNeeded);
        setLastMaintenanceDate(lastMaintenanceDate);
        setMaintenanceIntervalDays(maintenanceIntervalDays);
    }

    //GETTERS
    public String getToolType() { return toolType; }
    public String getMaterial() { return material; }
    public String getSteelGrade() { return steelGrade; }
    public boolean getMaintenanceNeeded() { return maintenanceNeeded; }
    public String getLastMaintenanceDate() { return lastMaintenanceDate; }
    public int getMaintenanceIntervalDays() { return maintenanceIntervalDays; }

    //SETTERS
    public void setToolType(String toolType) { this.toolType = toolType; }
    public void setSteelGrade(String steelGrade) { this.steelGrade = steelGrade; }
    public void setMaterial(String material) { this.material = material; }
    public void setMaintenanceNeeded(boolean maintenanceNeeded) { this.maintenanceNeeded = maintenanceNeeded; }
    public void setLastMaintenanceDate(String lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }
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
            statusMessage = "UUrgent! Maintenance Needed!";
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
                        Type: %s
                        Steel Grade: %s
                        Material: %s
                        Status: %s""",
                super.descriptionDetails(),
                toolType,
                steelGrade,
                material,
                statusMessage);
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}