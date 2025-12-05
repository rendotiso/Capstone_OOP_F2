package Model.Entities;

import Model.Enums.Category;
import Model.Enums.Location;
import Model.Entities.Maintenanable;

public class Tool extends Item implements Maintenanable {
    private String toolType;
    private boolean requiresMaintenance;
    private String material;
    private String lastMaintenanceDate;

    public Tool(String name, String description, int quantity, double purchasePrice, String purchaseDate, String vendor, Location location, String toolType, boolean requiresMaintenance, String material, String lastMaintenanceDate){
        super(name, description, quantity, purchasePrice, purchaseDate, vendor, Category.valueOf("Tools"), location);
        this.toolType = toolType;
        this.requiresMaintenance = requiresMaintenance;
        this.material = material;
        this.lastMaintenanceDate = lastMaintenanceDate;
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
    public void doMaintenance(){
        return;
    }

    @Override
    public String getLastMaintenanceDates() {
        return lastMaintenanceDate;
    }

    @Override
    public void setLastMaintenanceDates() {
        return;
    }

    public void setLastMaintenanceDates(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    @Override
    public void updateQuantity(int n) {
        setQuantity(getQuantity() + 1);
    }

    @Override
    public double calculateValue() {
        return getPurchasePrice() * getQuantity();
    }
}
