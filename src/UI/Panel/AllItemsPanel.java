
package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class AllItemsPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton, clearButton, refreshButton, deleteButton;
    private JTable table1;

    public AllItemsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel searchPanel = createSearchPanel();
        JTable itemTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(table1);
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        searchPanel.setBackground(new Color(70, 130, 180));
        searchField.setBackground(Color.WHITE);
        searchButton.setBackground(new Color(100, 149, 237));
        clearButton.setBackground(new Color(100, 149, 237));
        searchButton.setForeground(Color.WHITE);
        clearButton.setForeground(Color.WHITE);
    }

    //SETTERS FOR NAME MUST IMPLEMENT

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

        // WILL CHANGE STYLING IF FIT
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

    // MUST IMPLEMENT : SEARCH AND CLEAR FUNCTIONALITY
    private void setupListeners() {
        // upper
        clearButton.addActionListener(e -> searchField.setText(""));
        searchButton.addActionListener(e->searchFunction());
        clearButton.setFocusable(false);
        searchButton.setFocusable(false);

        //bottom

    }

    private void searchFunction(){

    }
    // MUST IMPLEMENT : TABLE
    private void createTable(){
        Object[][] data = {
                {"The Dark Knight", 2008, 9.0, 123445},
                {"INPUT", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445},
                {"The Dark Knight", 2008, 9.0, 123445}
        };
        Objects.requireNonNull(table1).setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Year", "Rating", "Number", "Mali"}
        ));
    }
}
