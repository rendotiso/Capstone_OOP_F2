package Model.Entities;

import Model.Data.Maintainable;
import Model.Enums.Category;
import Model.Enums.Location;

public class Tool extends Item implements Maintainable {
    private String toolType;
    private String material;
    private String size;
    private boolean maintenanceNeeded;

    public Tool(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String toolType, String size, String material, boolean maintenanceNeeded) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("TOOLS"), location);
        this.toolType = toolType;
        this.material = material;
        this.size = size;
        this.maintenanceNeeded = true;
    }

    //GETTERS
    public String getToolType() {
        return toolType;
    }
    public String getMaterial() {
        return material;
    }
    public String getSize(){ return size;}

    //SETTERS
    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
    public void setSize(String size) {this.size = size;}
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
