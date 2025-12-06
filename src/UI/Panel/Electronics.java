package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;
import java.awt.*;
import java.awt.event.ActionListener;

public class Electronics extends JPanel {

    // ATTRIBUTES
    private JPanel panelist, rootPanel, panel, Electronics_panel, table_panel, description_panel;
    private JTextField name_field, quantity_field, price_field,
            vendor_field, location_field, warranty_field, model_field, brand_field, LMD_field;
    private JLabel Electronics_label, name_label, quantity_label, price_label,
            location_label, vendor_label, warranty_label, model_label, brand_label, LMD_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton;
    private JTable table1;
    private JLabel description_label;

    private JTextArea textArea1;
    private JScrollPane scrollPane;

    public JTextField getLastmaintenancedate_field() {
        return LMD_field;
    }

    public void setLastmaintenancedate_field(JTextField lastmaintenancedate_field) {}

    public Electronics() {
        initComponents();
        setupLayout();
        setupAppearance();
        createTable();
    }

    private void initComponents() {
        // Initialize panels
        panelist = new JPanel();
        rootPanel = new JPanel();
        panel = new JPanel();
        Electronics_panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();

        // Initializes form fields
        name_field = new JTextField();
        quantity_field = new JTextField();
        price_field = new JTextField();
        vendor_field = new JTextField();
        warranty_field = new JTextField();
        model_field = new JTextField();
        brand_field = new JTextField();
        LMD_field = new JTextField();
        location_field = new JTextField();

        // Initialize labels
        Electronics_label = new JLabel("ELECTRONICS");
        name_label = new JLabel("NAME:");
        quantity_label = new JLabel("QUANTITY:");
        location_label = new JLabel("LOCATION:");
        vendor_label = new JLabel("VENDOR:");
        price_label = new JLabel("PRICE:");
        warranty_label = new JLabel("WARRANTY:");
        model_label = new JLabel("MODEL:");
        brand_label = new JLabel("BRAND:");
        LMD_label = new JLabel("LMD:");

        table1 = new JTable();
        scrollPane = new JScrollPane(table1);
    }

    private void setupLayout() {
        // Main panel setup
        setLayout(new BorderLayout());
        panelist.setLayout(new BorderLayout());
        panelist.setPreferredSize(new Dimension(1000, 860));

        // Root panel setup (2 rows, 2 columns, layout)
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Clothes title panel (top-left, spans 2 columns)
        Electronics_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Electronics_panel.add(Electronics_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(Electronics_panel, gbc);

        // Form panel (left side - 10 rows, 2 columns)
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Row 0: Name
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        panel.add(name_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        name_field.setPreferredSize(new Dimension(150, 25));
        panel.add(name_field, formGbc);

        row++;

        // Row 1: Quantity
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(quantity_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        quantity_field.setPreferredSize(new Dimension(150, 25));
        panel.add(quantity_field, formGbc);

        row++;

        // Row 2: Location
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(location_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        location_field.setPreferredSize(new Dimension(150, 25));
        panel.add(location_field, formGbc);

        row++;

        // Row 3: Vendor
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(vendor_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        vendor_field.setPreferredSize(new Dimension(150, 25));
        panel.add(vendor_field, formGbc);

        row++;

        // Row 4: Price
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(price_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        price_field.setPreferredSize(new Dimension(150, 25));
        panel.add(price_field, formGbc);

        row++;

        // Row 5: Warranty
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(warranty_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        warranty_field.setPreferredSize(new Dimension(150, 25));
        panel.add(warranty_field, formGbc);

        row++;

        // Row 6: Model
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(model_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        model_field.setPreferredSize(new Dimension(150, 25));
        panel.add(model_field, formGbc);

        row++;

        // Row 7: Brand
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(brand_label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        brand_field.setPreferredSize(new Dimension(150, 25));
        panel.add(brand_field, formGbc);

        row++;

        // Row 8: Description/Note
        description_panel.setLayout(new GridBagLayout());
        GridBagConstraints descGbc = new GridBagConstraints();
        descGbc.insets = new Insets(5, 5, 5, 5);

        // Description label
        descGbc.gridx = 0;
        descGbc.gridy = 0;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.HORIZONTAL;
        description_panel.add(description_label, descGbc);

        // Text area
        descGbc.gridx = 0; descGbc.gridy = 1;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.BOTH;
        descGbc.weighty = 1.0;
        JScrollPane textAreaScroll = new JScrollPane(textArea1);
        textAreaScroll.setPreferredSize(new Dimension(510, 80));
        description_panel.add(textAreaScroll, descGbc);

        // Buttons panel
        descGbc.gridx = 0; descGbc.gridy = 2;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.NONE;
        descGbc.weighty = 0;
        descGbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(ADDButton);
        buttonPanel.add(CLEARButton);
        buttonPanel.add(UPDATEButton);
        buttonPanel.add(REMOVEButton);
        description_panel.add(buttonPanel, descGbc);

        // Add description panel to main form panel
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.gridwidth = 2;
        formGbc.fill = GridBagConstraints.BOTH;
        formGbc.weighty = 1.0;
        panel.add(description_panel, formGbc);

        // Add form panel to root panel (left side)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(panel, gbc);

        // Table panel (right side)
        table_panel.setLayout(new BorderLayout());
        table_panel.add(scrollPane, BorderLayout.CENTER);

        // Add table panel to root panel (right side)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        rootPanel.add(table_panel, gbc);

        // Add root panel to panel1
        panelist.add(rootPanel, BorderLayout.CENTER);

        // Add panel1 to main panel
        add(panelist, BorderLayout.CENTER);
    }

    private void setupAppearance(){
        Color lightBlueGray = new Color(-7618067);
        Color darkBlue = new Color(-4660737);
        Color lightBlue = new Color(-2102785);
        Color black = new Color(-16777216);
        Color white = new Color(-16448251);

        panelist.setBackground(lightBlueGray);
        rootPanel.setBackground(lightBlueGray);
        Electronics_panel.setBackground(darkBlue);
        panel.setBackground(darkBlue);
        table_panel.setBackground(lightBlueGray);
        description_panel.setBackground(darkBlue);

        // Set text field backgrounds
        name_field.setBackground(lightBlue);
        quantity_field.setBackground(lightBlue);
        vendor_field.setBackground(lightBlue);
        price_field.setBackground(lightBlue);
        location_field.setBackground(lightBlue);
        warranty_field.setBackground(lightBlueGray);
        model_field.setBackground(lightBlueGray);
        brand_field.setBackground(lightBlueGray);
        LMD_label.setBackground(lightBlueGray);
        textArea1.setBackground(lightBlue);

        // Set foreground colors
        name_field.setForeground(black);
        quantity_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        textArea1.setForeground(black);
        location_field.setForeground(black);
        warranty_field.setForeground(black);
        model_field.setForeground(black);
        brand_field.setForeground(black);
        LMD_field.setForeground(black);

        // Set label colors
        Electronics_label.setForeground(black);
        name_label.setForeground(black);
        quantity_label.setForeground(black);
        location_label.setForeground(black);
        vendor_label.setForeground(black);
        price_label.setForeground(black);
        location_label.setForeground(black);
        warranty_label.setForeground(black);
        model_label.setForeground(black);
        brand_label.setForeground(black);
        LMD_label.setForeground(black);
        description_label.setForeground(black);

        // Set button colors
        ADDButton.setBackground(lightBlueGray);
        CLEARButton.setBackground(lightBlueGray);
        UPDATEButton.setBackground(lightBlueGray);
        REMOVEButton.setBackground(lightBlueGray);
        ADDButton.setForeground(black);
        CLEARButton.setForeground(black);
        UPDATEButton.setForeground(black);
        REMOVEButton.setForeground(black);

        // Set fonts
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);

        Electronics_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        location_label.setFont(labelFont);
        warranty_label.setFont(labelFont);
        model_label.setFont(labelFont);
        brand_label.setFont(labelFont);
        LMD_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        quantity_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        location_field.setFont(fieldFont);
        warranty_field.setFont(fieldFont);
        model_field.setFont(fieldFont);
        brand_field.setFont(fieldFont);
        LMD_field.setFont(fieldFont);
        textArea1.setFont(fieldFont);

        // Set button fonts
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
    }

////    public Electronics() {
//////        setContentPane(Panel);
//////        setTitle("HomeScreen");
//////        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//////        setSize(600, 500);
//////        setLocationRelativeTo(null);
//////
////        setters();
//////        setVisible(true);
////        listeners();
////        createTable();
////    }
//
////    public JPanel getRootPanelElectronics(){
////        return Panel;
////    }
////

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
