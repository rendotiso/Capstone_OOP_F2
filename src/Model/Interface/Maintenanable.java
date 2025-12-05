package Model.Entities;

public interface Maintenanable {
    public boolean needsMaintenance();
    public int getDaysUntilMaintenanceDue();
}
