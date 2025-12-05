package Model.Data;

public interface Maintainable {
    public boolean needsMaintenance();
    public int getDaysUntilMaintenanceDue();
}
