package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;

public class Tool extends Item implements Model.Entities.Maintenanable {
    private String toolType;
    private boolean requiresMaintenance;
    private String material;
    private boolean maintenanceNeeded;

    public Tool(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String toolType, boolean requiresMaintenance, String material, boolean maintenanceNeeded) {
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("Tools"), location);
        this.toolType = toolType;
        this.requiresMaintenance = requiresMaintenance;
        this.material = material;
        this.maintenanceNeeded = maintenanceNeeded;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean getRequiresMaintenance() {
        return requiresMaintenance;
    }

    public void setRequiresMaintenance(boolean requiresMaintenance) {
        this.requiresMaintenance = requiresMaintenance;
    }

    @Override
    public boolean needsMaintenance() {
        return requiresMaintenance;
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
