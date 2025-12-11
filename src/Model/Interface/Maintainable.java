package Model.Interface;

public interface Maintainable {
    boolean needsMaintenance();
    int getDaysUntilMaintenanceDue();
}
