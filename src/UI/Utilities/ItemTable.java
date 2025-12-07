package UI.Utilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ItemTable extends JScrollPane {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private static final String[] DEFAULT_COLUMN_NAMES = {"Name", "Qty", "Location", "Vendor", "Price", "Details"};

    public ItemTable() {
        this(DEFAULT_COLUMN_NAMES);
    }

    public ItemTable(String[] columnNames) {
        table = new JTable();
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Return proper class for each column
                if (columnNames[columnIndex].equalsIgnoreCase("Qty")) {
                    return Integer.class;
                } else if (columnNames[columnIndex].equalsIgnoreCase("Price")) {
                    return Double.class;
                }
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        setupTableAppearance();
        setupTableRenderers();

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFillsViewportHeight(true);
        
        setViewportView(table);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void setupTableAppearance() {

        table.setRowHeight(30); // Initial row height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(100, 149, 237));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120); // Name
        columnModel.getColumn(1).setPreferredWidth(50);  // Qty
        columnModel.getColumn(2).setPreferredWidth(100); // Location
        columnModel.getColumn(3).setPreferredWidth(120); // Vendor
        columnModel.getColumn(4).setPreferredWidth(80);  // Price
        columnModel.getColumn(5).setPreferredWidth(300); // Details
    }

    private void setupTableRenderers() {

        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                if (value instanceof Number) {
                    ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    ((JLabel) component).setHorizontalAlignment(SwingConstants.LEFT);
                }

                return component;
            }
        });

        table.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustRowHeights();
            }
        });
    }

    public void adjustRowHeights() {
        for (int row = 0; row < table.getRowCount(); row++) {
            int maxHeight = 30;

            for (int col = 0; col < table.getColumnCount(); col++) {
                Object cellValue = table.getValueAt(row, col);
                if (cellValue != null) {
                    String cellText = cellValue.toString();
                    if (!cellText.isEmpty()) {

                        JTextArea tempTextArea = new JTextArea(cellText);
                        tempTextArea.setLineWrap(true);
                        tempTextArea.setWrapStyleWord(true);
                        tempTextArea.setFont(table.getFont());

                        int columnWidth = table.getColumnModel().getColumn(col).getWidth();
                        tempTextArea.setSize(columnWidth - 10, Integer.MAX_VALUE); // Subtract padding

                        int textHeight = tempTextArea.getPreferredSize().height + 8; // Add padding
                        maxHeight = Math.max(maxHeight, textHeight);
                    }
                }
            }

            table.setRowHeight(row, Math.min(maxHeight, 200));
        }
    }

    public void setColumnWidths(int... widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < Math.min(widths.length, columnModel.getColumnCount()); i++) {
            columnModel.getColumn(i).setPreferredWidth(widths[i]);
            columnModel.getColumn(i).setMinWidth(widths[i] / 2);
        }
    }

    public void setDefaultColumnWidths() {
        setColumnWidths(150, 60, 120, 150, 80, 250);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
        adjustRowHeights();
    }

    public void setData(Object[][] data) {
        clearTable();
        for (Object[] row : data) {
            addRow(row);
        }
    }

    public void removeRow(int row) {
        if (row >= 0 && row < tableModel.getRowCount()) {
            tableModel.removeRow(row);
        }
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public Object getValueAt(int row, int column) {
        return tableModel.getValueAt(row, column);
    }

    public void setSelectionListener(java.awt.event.MouseListener listener) {
        table.addMouseListener(listener);
    }

    public void setColumnNames(String[] columnNames) {
        tableModel.setColumnIdentifiers(columnNames);
    }
}