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
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(clearButton);

        return panel;
    }

    private void setupListeners() {
        clearButton.addActionListener(e -> searchField.setText(""));
        searchButton.addActionListener(e -> searchFunction());
        clearButton.setFocusable(false);
        searchButton.setFocusable(false);
    }

    private void searchFunction() {
        System.out.println("Searching for: " + searchField.getText());
    }

    private void createTable() {
        Object[][] data = {
                {"The Dark Knight", 2008, 9.0, 123445, "Action"},
                {"Inception", 2010, 8.8, 234567, "Sci-Fi"},
                {"Interstellar", 2014, 8.6, 345678, "Adventure"},
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
                new String[]{"Name", "Year", "Rating", "Number", "Category", "Boggsh", "At Vhaket hende"}
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
}