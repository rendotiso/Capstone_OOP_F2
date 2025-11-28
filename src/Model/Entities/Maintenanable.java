package Model.Entities;

public interface Maintenanable {
    public boolean needsMaintenance();
    public void doMaintenance();
    public String getLastMaintenanceDates();
    public void setLastMaintenanceDates();
}
