package UI.Panel;

import UI.Utilities.ItemTable;
import Model.Data.InventoryManager;

import java.text.NumberFormat;
import java.util.List;
import Model.Entities.Item;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Locale;

public class AllItemsPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton, clearButton, refreshButton, deleteButton;
    private final ItemTable itemTable;
    private final InventoryManager inventoryManager;
    private int selectedIndex = -1;
    private JLabel totalValueLabel;

    public AllItemsPanel() {
        inventoryManager = InventoryManager.getInstance();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"No.", "Category", "Name", "Quantity", "Location", "Price", "Details", "Value"};
        itemTable = new ItemTable(columnNames);
        itemTable.setColumnWidths(50, 100, 150, 60, 100, 80, 170, 80);
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.NORTH);
        add(itemTable, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(itemTable, BorderLayout.CENTER);
        centerPanel.add(createTotalPanel(), BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // Styling
        searchPanel.setBackground(new Color(70, 130, 180));
        searchField.setBackground(Color.WHITE);
        searchButton.setBackground(new Color(100, 149, 237));
        clearButton.setBackground(new Color(100, 149, 237));
        searchButton.setForeground(Color.WHITE);
        clearButton.setForeground(Color.WHITE);

        setupTableAppearance();
        setupListeners();
        setupTableSelectionListener(); // Add table selection listener
        loadItems();
        updateTotalValue();
    }

    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        totalPanel.setBackground(new Color(245, 245, 245));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(new Color(245, 245, 245));

        JPanel totalDisplay = new JPanel(new BorderLayout(10, 0));
        totalDisplay.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        totalDisplay.setBackground(Color.WHITE);

        JLabel totalText = new JLabel("TOTAL: ");
        totalText.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalText.setForeground(new Color(70, 130, 180));

        totalValueLabel = new JLabel("$0.00");
        totalValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalValueLabel.setForeground(new Color(0, 100, 0));

        totalValueLabel.putClientProperty("html.disable", Boolean.TRUE);

        totalDisplay.add(totalText, BorderLayout.WEST);
        totalDisplay.add(totalValueLabel, BorderLayout.CENTER);

        rightPanel.add(totalDisplay);
        totalPanel.add(rightPanel, BorderLayout.EAST);

        return totalPanel;
    }

    private void updateTotalValue() {
        double currentTotal = calculateTotalValue();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedValue = currencyFormat.format(currentTotal);
        totalValueLabel.setText(formattedValue);

        if (currentTotal > 10000) {
            totalValueLabel.setForeground(new Color(0, 128, 0));
        } else if (currentTotal > 1000) {
            totalValueLabel.setForeground(new Color(0, 100, 0));
        } else {
            totalValueLabel.setForeground(new Color(139, 0, 0));
        }

        totalValueLabel.getParent().revalidate();
        totalValueLabel.getParent().repaint();
    }

    private double calculateTotalValue() {
        List<Item> allItems = inventoryManager.getAllItems();
        double total = 0.0;

        for (Item item : allItems) {
            total += item.getPurchasePrice() * item.getQuantity();
        }

        return total;
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
        clearButton.addActionListener(_ -> {
            searchField.setText("");
            loadItems();
        });
        clearButton.setFocusable(false);
        searchButton.setFocusable(false);
        refreshButton.setFocusable(false);
        deleteButton.setFocusable(false);

        searchButton.addActionListener(_ -> searchFunction());
        refreshButton.addActionListener(_ -> refreshFunction());
        deleteButton.addActionListener(_ -> deleteFunction());
        searchField.addActionListener(_ -> searchFunction());
    }

    private void setupTableSelectionListener() {
        itemTable.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedIndex = selectedRow;
                }
            }
        });
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

            double filteredTotal = filteredItems.stream()
                    .mapToDouble(item -> item.getPurchasePrice() * item.getQuantity())
                    .sum();

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            totalValueLabel.setText(currencyFormat.format(filteredTotal));
            totalValueLabel.setForeground(new Color(70, 130, 180));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching items: " + e.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            loadItems();
        }
    }

    private void refreshFunction() {
        loadItems();
        searchField.setText("");
        selectedIndex = -1;
        itemTable.clearSelection();

        JOptionPane.showMessageDialog(this,
                "Table refreshed successfully.",
                "Refresh Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteFunction() {
        if (selectedIndex >= 0) {
            try {
                List<Item> allItems = inventoryManager.getAllItems();
                DefaultTableModel model = itemTable.getTableModel();
                if (selectedIndex < model.getRowCount()) {
                    int displayItemNo = (Integer) model.getValueAt(selectedIndex, 0);
                    int itemIndex = displayItemNo - 1;
                    String itemName = (String) model.getValueAt(selectedIndex, 2);

                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to delete item: " + itemName + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if (confirm == JOptionPane.YES_OPTION) {
                        if (itemIndex >= 0 && itemIndex < allItems.size()) {
                            Item itemToDelete = allItems.get(itemIndex);
                            inventoryManager.removeItem(itemToDelete);

                            loadItems();
                            selectedIndex = -1;

                            JOptionPane.showMessageDialog(this,
                                    "Item deleted successfully.",
                                    "Delete Complete",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error deleting item: " + e.getMessage(),
                        "Delete Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a row to delete!",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadItems() {
        itemTable.clearTable();

        List<Item> allItems = inventoryManager.getAllItems();
        int itemNo = 1;
        for (Item item : allItems) {
            double itemValue = item.getPurchasePrice() * item.getQuantity();

            Object[] rowData = new Object[]{
                    itemNo++,
                    item.getCategory() != null ? item.getCategory().toString() : "",
                    item.getName(),
                    item.getQuantity(),
                    item.getLocation(),
                    item.getPurchasePrice(),
                    item.descriptionDetails(),
                    itemValue
            };
            itemTable.addRow(rowData);
        }

        itemTable.adjustRowHeights();
        updateTotalValue();
    }

    private void updateTableWithItems(List<Item> items) {
        itemTable.clearTable();

        int itemNo = 1;
        for (Item item : items) {
            Object[] rowData = createTableRow(item, itemNo++);
            itemTable.addRow(rowData);
        }

        itemTable.adjustRowHeights();
        selectedIndex = -1;
        itemTable.clearSelection();
    }

    private Object[] createTableRow(Item item, int itemNo) {
        double itemValue = item.getPurchasePrice() * item.getQuantity();

        return new Object[]{
                itemNo,
                item.getCategory() != null ? item.getCategory().toString() : "",
                item.getName(),
                item.getQuantity(),
                item.getLocation(),
                item.getPurchasePrice(),
                item.descriptionDetails(),
                itemValue
        };
    }

    private void setupTableAppearance() {
        JTable table = itemTable.getTable();

        table.setRowHeight(30);
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
                if (column == 0) {
                    Component comp = centerRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    comp.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    return comp;
                }
                else if (column == 1) {
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
                else if (column == 2) {
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
                else if (column == 3) {
                    return centerRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                }
                else if (column == 4) {
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
                else if (column == 5) {
                    switch (value) {
                        case Double v -> priceRenderer.setText(String.format("$%.2f", v));
                        case Number number -> priceRenderer.setText(String.format("$%.2f", number.doubleValue()));
                        case String s -> {
                            try {
                                double price = Double.parseDouble(s);
                                priceRenderer.setText(String.format("$%.2f", price));
                            } catch (NumberFormatException e) {
                                priceRenderer.setText(value.toString());
                            }
                        }
                        default -> priceRenderer.setText(value.toString());
                    }

                    return priceRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                }

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

                    int lineCount = textArea.getLineCount();
                    if (lineCount > 1) {
                        table.setRowHeight(row, Math.max(30, 25 * lineCount));
                    }

                    return textArea;
                }else if (column == 7) {
                    switch (value) {
                        case Double v -> priceRenderer.setText(String.format("$%,.2f", v));
                        case Number number -> {
                            double v = number.doubleValue();
                            priceRenderer.setText(String.format("$%,.2f", v));
                        }
                        case String s -> {
                            try {
                                double val = Double.parseDouble(s);
                                priceRenderer.setText(String.format("$%,.2f", val));
                            } catch (NumberFormatException e) {
                                priceRenderer.setText(value.toString());
                            }
                        }
                        default -> priceRenderer.setText(value.toString());
                    }

                    return priceRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                }
                else {
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
            }
        });

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(130);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(6).setPreferredWidth(313);
        columnModel.getColumn(7).setPreferredWidth(100);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 248, 255) : Color.WHITE);
                }

                return c;
            }
        });

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