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
    public void setSteelGrade(String steelGrade) {this.steelGrade = steelGrade;}
    public void setMaterial(String material) {
        this.material = material;
    }
    public void setMaintenanceNeeded(boolean maintenanceNeeded) {
        this.maintenanceNeeded = maintenanceNeeded;
    }
    public void setLastMaintenanceDate(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }
    public void setMaintenanceIntervalDays(int maintenanceIntervalDays) {
        this.maintenanceIntervalDays = maintenanceIntervalDays;
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

            LocalDate nextMaintenanceDate = lastMaintenance.plusDays(maintenanceIntervalDays);
            LocalDate today = LocalDate.now();

            return (int) ChronoUnit.DAYS.between(today, nextMaintenanceDate);

        } catch (DateTimeParseException e) {
            return 0;
        }
    }
    @Override
    public String descriptionDetails() {
        String maintenanceStatus = needsMaintenance() ? "MAINTENANCE REQUIRED" : "Good Condition";
        return String.format("""
                        %s\
                        Type: %s
                        Steel Grade: %s
                        Material: %s
                        Maintenance Status: %s (%d days remaining)""",
                super.descriptionDetails(),
                toolType,
                steelGrade,
                material,
                maintenanceStatus,
                getDaysUntilMaintenanceDue());
    }
    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }

}
