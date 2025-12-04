package UI.Utilities;

import Model.Entities.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ItemTable extends AbstractTableModel {
    private List<Item> items;
    private final String[] columns = {"No.", "Name", "Category", "Quantity", "Price", "Location", "Details", "Value"};

    public ItemTable(List<Item> items) {
        this.items = items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() { return items.size(); }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public String getColumnName(int column) { return columns[column]; }

    @Override
    public Object getValueAt(int row, int column) {
        Item item = items.get(row);
        return switch (column) {
            case 0 -> row + 1;
            case 1 -> item.getName();
//            case 2 -> item.getCategory();
            case 3 -> item.getQuantity();
            case 4 -> String.format("$%.2f", item.getPurchasePrice());
//            case 5 -> item.getLocation();
//            case 6 -> item.getDetails();
            case 7 -> String.format("$%.2f", item.calculateValue());
            default -> "";
        };
    }

    public Item getItemAt(int row) {
        return items.get(row);
    }
}