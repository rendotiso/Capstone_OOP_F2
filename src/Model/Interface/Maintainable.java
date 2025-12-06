package Model.Interface;

public interface Maintainable {
    public boolean needsMaintenance();
    public int getDaysUntilMaintenanceDue();
}
