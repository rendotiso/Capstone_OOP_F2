package Model.Entities;

public class Tools extends Item implements Maintenanable{
    private String toolType;
    private boolean requiresMaintenance;
    private String material;
    private String lastMaintenanceDate;

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
    public String getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    @Override
    public void setLastMaintenanceDate(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    @Override
    public double calculateValue(){
        return 1.5;
    }
}
