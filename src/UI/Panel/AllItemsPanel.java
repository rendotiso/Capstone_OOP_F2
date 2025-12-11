package UI.Panel;

import UI.Utilities.ItemTable;
import Model.Data.InventoryManager;
import Model.Entities.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AllItemsPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton, clearButton, refreshButton, deleteButton;
    private JLabel totalValueLabel;
    private final ItemTable itemTable;
    private final InventoryManager inventoryManager;
    private int selectedIndex = -1;

    // appearance
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SELECTION_COLOR = new Color(100, 149, 237);
    private static final Color ALT_ROW_COLOR = new Color(240, 248, 255);
    private static final Color POSITIVE_COLOR = new Color(0, 100, 0);
    private static final Color NEGATIVE_COLOR = new Color(215, 70, 70);
    private static final Color BTN_COLOR = new Color(105, 165, 225);
    private static final Color REFRESH_COLOR = new Color(111, 168, 214);

    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 12);
    private static final Locale PH_LOCALE = Locale.forLanguageTag("en-PH");


    public AllItemsPanel() {
        inventoryManager = InventoryManager.getInstance();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"No.", "Category", "Name", "Quantity", "Location", "Price", "Details", "Value"};
        itemTable = new ItemTable(columnNames);
        itemTable.setColumnWidths(50, 100, 150, 60, 100, 80, 170, 80);

        add(createSearchPanel(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(itemTable, BorderLayout.CENTER);
        centerPanel.add(createTotalPanel(), BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        setupTableAppearance();
        setupListeners();
        setupTableSelectionListener();

        loadAllItems();
    }

    private JPanel createSearchPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PRIMARY_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        leftPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(HEADER_FONT);

        searchField = new JTextField(25);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        searchButton = createStyledButton("Search", BTN_COLOR);
        clearButton = createStyledButton("Clear", BTN_COLOR);

        leftPanel.add(searchLabel);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        leftPanel.add(clearButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightPanel.setOpaque(false);

        refreshButton = createStyledButton("Refresh", REFRESH_COLOR);
        deleteButton = createStyledButton("Delete", NEGATIVE_COLOR);

        rightPanel.add(refreshButton);
        rightPanel.add(deleteButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        totalPanel.setBackground(new Color(238, 238, 238));

        JPanel totalDisplay = new JPanel(new BorderLayout(10, 0));
        totalDisplay.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        totalDisplay.setBackground(Color.WHITE);

        JLabel totalText = new JLabel("TOTAL VALUE: ");
        totalText.setFont(HEADER_FONT);
        totalText.setForeground(PRIMARY_COLOR);

        totalValueLabel = new JLabel("₱0.00");
        totalValueLabel.setFont(HEADER_FONT);
        totalValueLabel.setForeground(POSITIVE_COLOR);
        totalValueLabel.putClientProperty("html.disable", Boolean.TRUE);

        totalDisplay.add(totalText, BorderLayout.WEST);
        totalDisplay.add(totalValueLabel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(new Color(238, 238, 238));
        rightPanel.add(totalDisplay);

        totalPanel.add(rightPanel, BorderLayout.EAST);
        return totalPanel;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(BOLD_FONT);
        btn.setFocusPainted(false);
        btn.setFocusable(false);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    private void loadAllItems() {
        updateTableData(inventoryManager.getAllItems());
    }

    private void updateTableData(List<Item> items) {
        itemTable.clearTable();
        double runningTotal = 0.0;
        int itemNo = 1;

        for (Item item : items) {
            double itemValue = item.getPurchasePrice() * item.getQuantity();
            runningTotal += itemValue;

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
        updateTotalLabel(runningTotal);

        selectedIndex = -1;
        itemTable.clearSelection();
    }

    private void updateTotalLabel(double total) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(PH_LOCALE);
        totalValueLabel.setText(currencyFormat.format(total));
        totalValueLabel.setForeground(total > 0 ? POSITIVE_COLOR : NEGATIVE_COLOR);

        totalValueLabel.repaint();
    }

    private void searchFunction() {
        String searchText = searchField.getText().trim();
        try {
            List<Item> filteredItems = inventoryManager.searchItems(searchText);

            if (filteredItems.isEmpty() && !searchText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items found matching: " + searchText,
                        "No Results", JOptionPane.INFORMATION_MESSAGE);
            }

            updateTableData(filteredItems);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching items: " + e.getMessage(),
                    "Search Error", JOptionPane.ERROR_MESSAGE);
            loadAllItems();
        }
    }

    private void refreshFunction() {
        searchField.setText("");
        loadAllItems();
        JOptionPane.showMessageDialog(this, "Table refreshed successfully.",
                "Refresh Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteFunction() {
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DefaultTableModel model = itemTable.getTableModel();

            String itemName = (String) model.getValueAt(selectedIndex, 2);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete item: " + itemName + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                int displayNo = (Integer) model.getValueAt(selectedIndex, 0);
                List<Item> currentList = searchField.getText().isEmpty()
                        ? inventoryManager.getAllItems()
                        : inventoryManager.searchItems(searchField.getText());

                if(displayNo - 1 < currentList.size()){
                    Item itemToDelete = currentList.get(displayNo - 1);
                    inventoryManager.removeItem(itemToDelete);

                    if(!searchField.getText().isEmpty()){
                        searchFunction();
                    } else {
                        loadAllItems();
                    }

                    JOptionPane.showMessageDialog(this, "Item deleted successfully.",
                            "Delete Complete", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting item: " + e.getMessage(),
                    "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupListeners() {
        clearButton.addActionListener(e -> {
            searchField.setText("");
            loadAllItems();
        });
        searchButton.addActionListener(e -> searchFunction());
        searchField.addActionListener(e -> searchFunction());
        refreshButton.addActionListener(e -> refreshFunction());
        deleteButton.addActionListener(e -> deleteFunction());
    }

    private void setupTableSelectionListener() {
        itemTable.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedIndex = itemTable.getSelectedRow();
            }
        });
    }


    private void setupTableAppearance() {
        JTable table = itemTable.getTable();

        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        table.getTableHeader().setFont(HEADER_FONT);
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);

        table.setFont(REGULAR_FONT);
        table.setSelectionBackground(SELECTION_COLOR);
        table.setSelectionForeground(Color.WHITE);

        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
            private final JTextArea textArea = new JTextArea();

            {
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setFont(REGULAR_FONT);
                textArea.setOpaque(true);
                textArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component comp;

                if (column == 6) {
                    textArea.setText(value != null ? value.toString() : "");

                    int columnWidth = table.getColumnModel().getColumn(column).getWidth();
                    textArea.setSize(columnWidth, Short.MAX_VALUE);
                    int preferredHeight = textArea.getPreferredSize().height;

                    if (table.getRowHeight(row) != Math.max(30, preferredHeight)) {
                        table.setRowHeight(row, Math.max(30, preferredHeight));
                    }

                    comp = textArea;
                } else {
                    comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    JLabel label = (JLabel) comp;
                    label.setFont(REGULAR_FONT);

                    if (column == 0 || column == 3) {
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        if (column == 0) label.setFont(BOLD_FONT);
                    }
                    else if (column == 5 || column == 7) {
                        label.setHorizontalAlignment(SwingConstants.RIGHT);
                        label.setFont(BOLD_FONT);
                        formatCurrency(label, value);
                    }
                    else {
                        label.setHorizontalAlignment(SwingConstants.LEFT);
                    }
                }

                if (isSelected) {
                    comp.setBackground(SELECTION_COLOR);
                    comp.setForeground(Color.WHITE);
                } else {
                    comp.setBackground(row % 2 == 0 ? ALT_ROW_COLOR : Color.WHITE);
                    comp.setForeground(Color.BLACK);
                }

                return comp;
            }

            private void formatCurrency(JLabel label, Object value) {
                if (value instanceof Number n) {
                    label.setText(String.format("₱%.2f", n.doubleValue()));
                } else if (value instanceof String s) {
                    try {
                        double val = Double.parseDouble(s);
                        label.setText(String.format("₱%.2f", val));
                    } catch (NumberFormatException e) {
                        label.setText(s);
                    }
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
        columnModel.getColumn(6).setPreferredWidth(314);
        columnModel.getColumn(7).setPreferredWidth(100);

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