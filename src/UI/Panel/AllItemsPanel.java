package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class AllItemsPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton, clearButton, refreshButton, deleteButton;
    private JTable table1;
    private JScrollPane scrollPane;

    public AllItemsPanel() {
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
        leftPanel.setOpaque(false); // Transparent to show parent background

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
        rightPanel.setOpaque(false); // Transparent to show parent background

        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");

        // Set button fonts
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Remove focus painting
        refreshButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);

        // Set custom colors
        Color refreshColor = new Color(0x74c69d); // #74c69d
        Color deleteColor = new Color(0xc10a0a);  // #c10a0a

        refreshButton.setBackground(refreshColor);
        deleteButton.setBackground(deleteColor);
        refreshButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);

        // Make buttons opaque
        refreshButton.setOpaque(true);
        deleteButton.setOpaque(true);

        rightPanel.add(refreshButton);
        rightPanel.add(deleteButton);

        // Add both panels to main panel
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

        // Add placeholder listeners for new buttons
        refreshButton.addActionListener(e -> refreshFunction());
        deleteButton.addActionListener(e -> deleteFunction());
    }

    private void searchFunction() {
        System.out.println("Searching for: " + searchField.getText());
    }

    private void refreshFunction() {
        System.out.println("Refresh button clicked");
        // You can add refresh logic here
    }

    private void deleteFunction() {
        System.out.println("Delete button clicked");
        // You can add delete logic here
    }

    private void createTable() {
        Object[][] data = {
                {"The Dark Knight", 2008, 9.0, 123445, "Action"},
                {"Inception", 2010, 8.8, 234567, "Sci-Fi"},
                {"Interstellar", "BEDROOOOOOM", 8.6, 345678, "Adventure"},
                {"The Matrix", 1999, 8.7, 456789, "Action"},
                {"Pulp Fiction", 1994, 8.9, 567890, "Crime"},
                {"Fight Club", 1999, 8.8, 678901, "Drama"},
                {"Forrest Gump", 1994, 8.8, 789012, "Drama"},
                {"The Shawshank Redemption", 1994, 9.3, 890123, "Drama"},
                {"The Godfather", 1972, 9.2, 901234, "Crime"},
                {"The Dark Knight Rises", 2012, 8.4, 123456, "Action"}
        };

        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"No.", "Category", "Name", "Quantity", "Location", "Price", "Details"}
        ));

        table1.setRowHeight(25);
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table1.getTableHeader().setBackground(new Color(70, 130, 180));
        table1.getTableHeader().setForeground(Color.WHITE);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table1.setSelectionBackground(new Color(100, 149, 237));
        table1.setSelectionForeground(Color.WHITE);
        table1.setGridColor(Color.LIGHT_GRAY);
        table1.setShowGrid(true);

        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table1.getColumnModel().getColumn(0).setPreferredWidth(50);
        table1.getColumnModel().getColumn(1).setPreferredWidth(120);
        table1.getColumnModel().getColumn(2).setPreferredWidth(150);
        table1.getColumnModel().getColumn(3).setPreferredWidth(80);
        table1.getColumnModel().getColumn(4).setPreferredWidth(130);
        table1.getColumnModel().getColumn(5).setPreferredWidth(80);
        table1.getColumnModel().getColumn(6).setPreferredWidth(468);
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

    // New getter methods for the new buttons
    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    // New action listener methods
    public void addRefreshButtonListener(java.awt.event.ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(java.awt.event.ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}