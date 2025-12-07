package UI.Panel;

import UI.Utilities.ItemTable;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class AllItemsPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton, clearButton, refreshButton, deleteButton;
    private JTable table1;
    private JScrollPane scrollPane;
    private ItemTable sharedData;

    public AllItemsPanel() {
        sharedData = ItemTable.getInstance();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize table FIRST
        table1 = new JTable();
        scrollPane = new JScrollPane(table1);

        // Create search panel
        JPanel searchPanel = createSearchPanel();

        // Add components
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Styling
        searchPanel.setBackground(new Color(70, 130, 180));
        searchField.setBackground(Color.WHITE);
        searchButton.setBackground(new Color(100, 149, 237));
        clearButton.setBackground(new Color(100, 149, 237));
        searchButton.setForeground(Color.WHITE);
        clearButton.setForeground(Color.WHITE);

        // Setup table and listeners
        createTable();
        setupListeners();
    }

    private JPanel createSearchPanel() {
        // Main panel with BorderLayout to position left and right components
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(70, 130, 180));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left panel for search controls
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        leftPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        searchField = new JTextField(25);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchButton.setFocusPainted(false);
        clearButton.setFocusPainted(false);

        leftPanel.add(searchLabel);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        leftPanel.add(clearButton);

        // Right panel for Refresh and Delete buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightPanel.setOpaque(false);

        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");

        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        refreshButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);

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
        String searchText = searchField.getText();
        sharedData.searchItems(searchText);
    }

    private void refreshFunction() {
        // Clear search field
        searchField.setText("");

        // Refresh from shared data model
        DefaultTableModel model = sharedData.getAllItemsTableModel();
        table1.setModel(model);
        setupTableAppearance();

        JOptionPane.showMessageDialog(this, "Data refreshed successfully!", "Refresh",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteFunction() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the actual item index from the "No." column
        Object noValue = table1.getValueAt(selectedRow, 0);
        if (noValue == null) {
            JOptionPane.showMessageDialog(this, "Invalid row selected!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int itemNo;
        if (noValue instanceof Integer) {
            itemNo = (Integer) noValue;
        } else {
            try {
                itemNo = Integer.parseInt(noValue.toString());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid row number!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int actualIndex = itemNo - 1; // Convert to 0-based index

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this item?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            sharedData.removeItem(actualIndex);
            JOptionPane.showMessageDialog(this, "Item deleted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void createTable() {
        // Use the shared table model
        table1.setModel(sharedData.getAllItemsTableModel());
        setupTableAppearance();
    }

    private void setupTableAppearance() {
        // Create a custom cell renderer that handles both alignment and wrapping
        table1.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
            private final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

            {
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                // Columns to center align: No.(0), Quantity(3), Price(5)
                if (column == 0 || column == 3 || column == 5) {
                    Component comp = centerRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);

                    // Format price column with currency symbol
                    if (column == 5 && value instanceof Double) {
                        centerRenderer.setText(String.format("$%.2f", (Double) value));
                    }

                    return comp;
                }
                // Columns to wrap text: Name(2), Category(1), Location(4), Details(6)
                else if (column == 1 || column == 2 || column == 4 || column == 6) {
                    JTextArea textArea = new JTextArea();
                    textArea.setText(value != null ? value.toString() : "");
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(table.getFont());
                    textArea.setOpaque(true);

                    if (isSelected) {
                        textArea.setBackground(table.getSelectionBackground());
                        textArea.setForeground(table.getSelectionForeground());
                    } else {
                        textArea.setBackground(table.getBackground());
                        textArea.setForeground(table.getForeground());
                    }

                    textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                    return textArea;
                } else {
                    // Default renderer for other columns
                    Component comp = defaultRenderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
                    return comp;
                }
            }
        });

        // Set dynamic row height
        table1.setRowHeight(25);
        table1.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustRowHeights();
            }
        });

        // Table styling
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table1.getTableHeader().setBackground(new Color(70, 130, 180));
        table1.getTableHeader().setForeground(Color.WHITE);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table1.setSelectionBackground(new Color(100, 149, 237));
        table1.setSelectionForeground(Color.WHITE);
        table1.setGridColor(Color.LIGHT_GRAY);
        table1.setShowGrid(true);

        // Set auto resize mode
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Set column widths
        TableColumnModel columnModel = table1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);    // No.
        columnModel.getColumn(1).setPreferredWidth(120);   // Category
        columnModel.getColumn(2).setPreferredWidth(150);   // Name
        columnModel.getColumn(3).setPreferredWidth(80);    // Quantity
        columnModel.getColumn(4).setPreferredWidth(130);   // Location
        columnModel.getColumn(5).setPreferredWidth(80);   // Price
        columnModel.getColumn(6).setPreferredWidth(467);   // Details
    }

    private void adjustRowHeights() {
        for (int row = 0; row < table1.getRowCount(); row++) {
            int maxHeight = 25; // Minimum height

            // Check all wrap-able columns
            int[] wrapColumns = {1, 2, 4, 6}; // Category, Name, Location, Details
            for (int col : wrapColumns) {
                Object cellValue = table1.getValueAt(row, col);
                if (cellValue != null) {
                    String cellText = cellValue.toString();
                    if (!cellText.isEmpty()) {
                        JTextArea tempTextArea = new JTextArea(cellText);
                        tempTextArea.setLineWrap(true);
                        tempTextArea.setWrapStyleWord(true);
                        tempTextArea.setFont(table1.getFont());
                        tempTextArea.setSize(table1.getColumnModel().getColumn(col).getWidth(), Integer.MAX_VALUE);

                        int textHeight = tempTextArea.getPreferredSize().height + 4;
                        maxHeight = Math.max(maxHeight, textHeight);
                    }
                }
            }

            table1.setRowHeight(row, Math.min(maxHeight, 200));
        }
    }

    public JTable getTable() {
        return table1;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void addRefreshButtonListener(java.awt.event.ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(java.awt.event.ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}