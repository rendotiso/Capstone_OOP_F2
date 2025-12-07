package UI.Panel;

import UI.Utilities.ItemTable;
import Model.Data.InventoryManager;
import java.util.List;
import Model.Entities.Item;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class AllItemsPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton, clearButton, refreshButton, deleteButton;
    private ItemTable itemTable;
    private InventoryManager inventoryManager;

    public AllItemsPanel() {
        inventoryManager = new InventoryManager();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"No.", "Category", "Name", "Quantity", "Location", "Price", "Details"};
        itemTable = new ItemTable(columnNames);
        itemTable.setColumnWidths(50, 100, 150, 60, 100, 80, 250);
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);
        add(itemTable, BorderLayout.CENTER);

        // Styling
        searchPanel.setBackground(new Color(70, 130, 180));
        searchField.setBackground(Color.WHITE);
        searchButton.setBackground(new Color(100, 149, 237));
        clearButton.setBackground(new Color(100, 149, 237));
        searchButton.setForeground(Color.WHITE);
        clearButton.setForeground(Color.WHITE);

        setupTableAppearance();
        setupListeners();
        loadTableData();
    }

    private JPanel createSearchPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(70, 130, 180));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        leftPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Segue UI", Font.BOLD, 14));
        searchField = new JTextField(25);
        searchField.setFont(new Font("Segue UI", Font.PLAIN, 14));
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");
        searchButton.setFont(new Font("Segue UI", Font.BOLD, 12));
        clearButton.setFont(new Font("Segue UI", Font.BOLD, 12));
        searchButton.setFocusPainted(false);
        clearButton.setFocusPainted(false);

        leftPanel.add(searchLabel);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        leftPanel.add(clearButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightPanel.setOpaque(false);

        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");
        refreshButton.setFont(new Font("Segue UI", Font.BOLD, 12));
        deleteButton.setFont(new Font("Segue UI", Font.BOLD, 12));

        refreshButton.setFocusable(false);
        deleteButton.setFocusable(false);
        Color refreshColor = new Color(0x74c69d);
        Color deleteColor = new Color(0xc10a0a);
        refreshButton.setBackground(refreshColor);
        deleteButton.setBackground(deleteColor);
        refreshButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);
        refreshButton.setOpaque(true);
        deleteButton.setOpaque(true);
        rightPanel.add(refreshButton);
        rightPanel.add(deleteButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private void setupListeners() {
        clearButton.addActionListener(e -> searchField.setText(""));
        searchButton.addActionListener(e -> searchFunction());
        clearButton.setFocusable(false);
        searchButton.setFocusable(false);
        refreshButton.setFocusable(false);
        deleteButton.setFocusable(false);

        // Add functional listeners
        refreshButton.addActionListener(e -> refreshFunction());
        deleteButton.addActionListener(e -> deleteFunction());

        // Add Enter key listener to search field
        searchField.addActionListener(e -> searchFunction());
    }

    private void searchFunction() {
        String searchText = searchField.getText().trim();
        try {
            List<Item> filteredItems = inventoryManager.searchItems(searchText);
            if (filteredItems.isEmpty() && !searchText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items found matching: " + searchText,
                        "No Results", JOptionPane.INFORMATION_MESSAGE);
            }
            updateTableWithItems(filteredItems);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching items: " + e.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            loadTableData();
        }
    }

    private void refreshFunction() {
        // Reload all items from database
        loadTableData();

        // Clear search field
        searchField.setText("");

        // Show confirmation message
        JOptionPane.showMessageDialog(this,
                "Table refreshed successfully.",
                "Refresh Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteFunction() {
        JTable table = itemTable.getTable();
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an item to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the actual item No. (displayed in column 0)
        int displayItemNo = (Integer) table.getValueAt(selectedRow, 0);

        // Convert to actual index in the list (displayItemNo - 1)
        int itemIndex = displayItemNo - 1;

        // Get the item's name for confirmation message
        String itemName = (String) table.getValueAt(selectedRow, 2);

        // Ask for confirmation
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete item: " + itemName + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Get all items to find the actual item to delete
                List<Item> allItems = inventoryManager.getAllItems();

                if (itemIndex >= 0 && itemIndex < allItems.size()) {
                    Item itemToDelete = allItems.get(itemIndex);

                    // Delete the item from database
                    boolean deleted = inventoryManager.removeItem(itemToDelete);

                    if (deleted) {
                        // Remove from table
                        DefaultTableModel model = itemTable.getTableModel();
                        model.removeRow(selectedRow);

                        // Update item numbers for remaining rows
                        updateItemNumbers();

                        JOptionPane.showMessageDialog(this,
                                "Item deleted successfully.",
                                "Delete Complete",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Failed to delete item. Please try again.",
                                "Delete Failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid item selection. Please refresh and try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error deleting item: " + e.getMessage(),
                        "Delete Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateItemNumbers() {
        DefaultTableModel model = itemTable.getTableModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0);
        }
    }

    private void loadTableData() {
        List<Item> allItems = inventoryManager.getAllItems();
        updateTableWithItems(allItems);
    }

    private void updateTableWithItems(List<Item> items) {
        DefaultTableModel model = itemTable.getTableModel();
        model.setRowCount(0);

        int itemNo = 1;
        for (Item item : items) {
            Object[] rowData = createTableRow(item, itemNo++);
            model.addRow(rowData);
        }

        itemTable.adjustRowHeights();
    }

    private Object[] createTableRow(Item item, int itemNo) {
        return new Object[]{
                itemNo,
                item.getCategory() != null ? item.getCategory().toString() : "",
                item.getName(),
                item.getQuantity(),
                item.getLocation(),
                item.getPurchasePrice(),
                item.descriptionDetails()
        };
    }

    private void setupTableAppearance() {
        JTable table = itemTable.getTable();

        // Set basic table properties
        table.setRowHeight(30); // Initial row height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Style the table header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        // Style the table
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(100, 149, 237));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Set custom cell renderer with proper column alignment
        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
            private final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            private final DefaultTableCellRenderer priceRenderer = new DefaultTableCellRenderer();

            {
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                centerRenderer.setFont(new Font("Segoe UI", Font.PLAIN, 12));

                priceRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                priceRenderer.setFont(new Font("Segoe UI", Font.BOLD, 12));

                defaultRenderer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                // Column 0: No. - Center aligned and bold
                if (column == 0) {
                    Component comp = centerRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    comp.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    return comp;
                }
                // Column 1: Category - Left aligned (default)
                else if (column == 1) {
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
                // Column 2: Name - Left aligned (default)
                else if (column == 2) {
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
                // Column 3: Quantity - Center aligned
                else if (column == 3) {
                    Component comp = centerRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    return comp;
                }
                // Column 4: Location - Left aligned (default)
                else if (column == 4) {
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
                // Column 5: Price - Right aligned with currency format
                else if (column == 5) {
                    if (value instanceof Double) {
                        priceRenderer.setText(String.format("$%.2f", (Double) value));
                    } else if (value instanceof Number) {
                        priceRenderer.setText(String.format("$%.2f", ((Number) value).doubleValue()));
                    } else if (value instanceof String) {
                        try {
                            double price = Double.parseDouble((String) value);
                            priceRenderer.setText(String.format("$%.2f", price));
                        } catch (NumberFormatException e) {
                            priceRenderer.setText(value.toString());
                        }
                    } else {
                        priceRenderer.setText(value != null ? value.toString() : "");
                    }

                    Component comp = priceRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    return comp;
                }
                // Column 6: Details - Text area for wrapping
                else if (column == 6) {
                    JTextArea textArea = new JTextArea();
                    textArea.setText(value != null ? value.toString() : "");
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    textArea.setOpaque(true);

                    if (isSelected) {
                        textArea.setBackground(new Color(100, 149, 237));
                        textArea.setForeground(Color.WHITE);
                    } else {
                        textArea.setBackground(table.getBackground());
                        textArea.setForeground(table.getForeground());
                    }

                    textArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

                    // Adjust row height based on text area content
                    int lineCount = textArea.getLineCount();
                    if (lineCount > 1) {
                        table.setRowHeight(row, Math.max(30, 25 * lineCount));
                    }

                    return textArea;
                }
                else {
                    // Default renderer for any other columns
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
            }
        });

        // Set column widths for AllItemsPanel
        // Column indices: 0:No., 1:Category, 2:Name, 3:Quantity, 4:Location, 5:Price, 6:Details
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);   // No. - smaller for just numbers
        columnModel.getColumn(1).setPreferredWidth(120);  // Category
        columnModel.getColumn(2).setPreferredWidth(150);  // Name
        columnModel.getColumn(3).setPreferredWidth(80);   // Quantity - narrower for numbers
        columnModel.getColumn(4).setPreferredWidth(130);  // Location
        columnModel.getColumn(5).setPreferredWidth(80);   // Price
        columnModel.getColumn(6).setPreferredWidth(467);  // Details

        // Add alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    // Light blue for even rows, white for odd rows
                    c.setBackground(row % 2 == 0 ? new Color(240, 248, 255) : Color.WHITE);
                }

                return c;
            }
        });

        // Add mouse listener for row selection
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    table.setRowSelectionInterval(row, row);
                }
            }
        });
    }
}