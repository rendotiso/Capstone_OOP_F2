package Model.Entities;

import Model.Data.Maintainable;
import Model.Enums.Category;
import Model.Enums.Location;

public class Tool extends Item implements Maintainable {
    private String toolType;
    private String material;
    private String steelGrade;
    private String lastMaintenanceDate;

    public Tool(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String toolType, String steelGrade, String material, String lastMaintenanceDate){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("TOOLS"), location);
        this.toolType = toolType;
        this.material = material;
        this.steelGrade = steelGrade;
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    //GETTERS
    public String getToolType() {
        return toolType;
    }
    public String getMaterial() {
        return material;
    }
    public String getSteelGrade(){ return steelGrade;}
    public String getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    //SETTERS
    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
    public void setSteelGrade(String steelGrade) {this.steelGrade = steelGrade;}
    public void setMaterial(String material) {
        this.material = material;
    }
    // maintendancedate

    //METHODS
    //HAS MISSING METHODS ; CHECK CLASSDIAGRAM ; FIX RTETURN STATEMENTS;
    @Override
    public boolean needsMaintenance() {
        return true;
    }

    @Override
    public int getDaysUntilMaintenanceDue() {
        return 0;
    }

    @Override
    public String descriptionDetails() {
        return "";
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
