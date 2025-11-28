package Model.Entities;

public class Electronics extends Item implements Maintenanable {
    private String warrantyPeriod;
    private String brand;
    private String model;
    private String lastMaintenanceDate;


    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setLastMaintenanceDate(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    @Override
    public boolean needsMaintenance(){

    }

    @Override
    public void doMaintenance(){

    }

    @Override
    public String getLastMaintenanceDate(){

    }

    @Override
    public void setLastMaintenanceDate(String ){

    }

    @Override
    public double calculateValue(){

    }
}
