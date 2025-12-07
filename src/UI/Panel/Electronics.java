package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Electronics extends JPanel {

    // ATTRIBUTES
    private JPanel panelist, rootPanel, Electronics_panel, panel, table_panel, description_panel;
    private JTextField name_field, LMD_field, vendor_field, price_field, warranty_field, model_field, brand_field;
    private JTextArea textArea1;
    private JLabel Electronics_label, name_label, quantity_label, location_label, vendor_label, price_label,
            warranty_label, model_label, brand_label, LMD_label, description_label, maintenance_label;
    private JCheckBox maintenanceNeeded; // Existing
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton;
    private JComboBox<String> location_combobox;
    private JTable table1;
    private JPanel panelButton;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private JScrollPane scrollPane;

    // Placeholder texts
    private static final String WARRANTY_PLACEHOLDER = "MM/DD/YYYY";
    private static final String LMD_PLACEHOLDER = "MM/DD/YYYY";

    public Electronics() {
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
        setupMaintenanceListener();
        createTable();
    }

    private void initComponents() {
        // Initialize panels
        panelist = new JPanel();
        rootPanel = new JPanel();
        Electronics_panel = new JPanel();
        panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();
        panelButton = new JPanel();

        // Initialize form fields
        name_field = new JTextField(8);
        vendor_field = new JTextField(8);
        price_field = new JTextField(8);
        warranty_field = new JTextField(8);
        model_field = new JTextField(8);
        brand_field = new JTextField(8);
        LMD_field = new JTextField(8);

        textArea1 = new JTextArea(3, 15);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        // Initialize checkbox
        maintenanceNeeded = new JCheckBox();
        maintenanceNeeded.setText("");


        // Initialize spinner for quantity with left-aligned text
        spinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        // Customize the spinner editor for left alignment
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner1, "#");
        spinner1.setEditor(editor);

        // Force left alignment of the text field inside the spinner
        JComponent editorComp = spinner1.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Initialize labels
        Electronics_label = new JLabel("ELECTRONICS");
        name_label = new JLabel("NAME:");
        quantity_label = new JLabel("QUANTITY:");
        location_label = new JLabel("LOCATION:");
        vendor_label = new JLabel("VENDOR:");
        price_label = new JLabel("PRICE:");
        warranty_label = new JLabel("PURCHASED DATE:");
        model_label = new JLabel("MODEL:");
        brand_label = new JLabel("BRAND:");
        LMD_label = new JLabel("LAST MAINTENANCE DATE:");
        description_label = new JLabel("DESCRIPTION/NOTE:");
        maintenance_label = new JLabel("MAINTENANCE NEEDED:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "GARAGE", "BASEMENT", "UTILITY ROOM", "STORAGE ROOM", "SHELF", "DRAWER", "ELECTRONICS CABINET"
        });

        table1 = new JTable();
        scrollPane = new JScrollPane(table1);
        textAreaScroll = new JScrollPane(textArea1);
    }

    private void setupLayout() {
        // Main panel setup
        setLayout(new BorderLayout());
        panelist.setLayout(new BorderLayout());

        // Root panel setup (2 rows, 2 columns layout)
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // TOOLS title panel (top-left, spans 2 columns)
        Electronics_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Electronics_panel.add(Electronics_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(Electronics_panel, gbc);

        // Form panel (left side)
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Row 0: Name
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(name_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        name_field.setPreferredSize(new Dimension(80, 25));
        panel.add(name_field, formGbc);

        row++;

        // Row 1: Quantity (Spinner)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(quantity_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        spinner1.setPreferredSize(new Dimension(80, 25));
        panel.add(spinner1, formGbc);

        row++;

        // Row 2: Location
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(location_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        location_combobox.setPreferredSize(new Dimension(80, 25));
        panel.add(location_combobox, formGbc);

        row++;

        // Row 3: Vendor
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(vendor_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        vendor_field.setPreferredSize(new Dimension(80, 25));
        panel.add(vendor_field, formGbc);

        row++;

        // Row 4: Price
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(price_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        price_field.setPreferredSize(new Dimension(80, 25));
        panel.add(price_field, formGbc);

        row++;

        // Row 5: Warranty Date
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(warranty_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        warranty_field.setPreferredSize(new Dimension(80, 25));
        panel.add(warranty_field, formGbc);

        row++;

        // Row 6: Model
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(model_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        model_field.setPreferredSize(new Dimension(80, 25));
        panel.add(model_field, formGbc);

        row++;

        // Row 7: Brand
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(brand_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        brand_field.setPreferredSize(new Dimension(80, 25));
        panel.add(brand_field, formGbc);

        row++;

        // Row 8: Maintenance Needed (NEW ROW)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(maintenance_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        maintenanceNeeded.setPreferredSize(new Dimension(25, 25));
        panel.add(maintenanceNeeded, formGbc);

        row++;

        // Row 9: LMD - MOVED BELOW MAINTENANCE
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(LMD_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        LMD_field.setPreferredSize(new Dimension(80, 25));
        panel.add(LMD_field, formGbc);

        row++;

        // Row 10: Description/Note
        description_panel.setLayout(new GridBagLayout());
        GridBagConstraints descGbc = new GridBagConstraints();
        descGbc.insets = new Insets(5, 5, 5, 5);

        // Description label
        descGbc.gridx = 0; descGbc.gridy = 0;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.HORIZONTAL;
        description_panel.add(description_label, descGbc);

        // Text area
        descGbc.gridx = 0; descGbc.gridy = 1;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.BOTH;
        descGbc.weighty = 1.0;
        textAreaScroll.setPreferredSize(new Dimension(200, 80));
        description_panel.add(textAreaScroll, descGbc);

        // Buttons panel
        descGbc.gridx = 0; descGbc.gridy = 2;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.NONE;
        descGbc.weighty = 0;
        descGbc.anchor = GridBagConstraints.CENTER;

        panelButton.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButton.add(ADDButton);
        panelButton.add(CLEARButton);
        panelButton.add(UPDATEButton);
        panelButton.add(REMOVEButton);
        description_panel.add(panelButton, descGbc);

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
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(table_panel, gbc);

        // Add root panel to panelist
        panelist.add(rootPanel, BorderLayout.CENTER);

        // Add panelist to main panel
        add(panelist, BorderLayout.CENTER);
    }

    private void setupAppearance() {
        // Set background colors
        Color header = new Color(0x4682B4);
        Color black = new Color(-16777216);
        Color bg = new Color(0xF5F5F5);
        Color placeholderColor = new Color(100, 100, 100, 180);
        Color warningColor = new Color(0xFF6B6B);

        // Set panels opaque
        panelist.setOpaque(true);
        rootPanel.setOpaque(true);
        Electronics_panel.setOpaque(true);
        panel.setOpaque(true);
        table_panel.setOpaque(true);
        description_panel.setOpaque(true);
        panelButton.setOpaque(true);

        // Set panel backgrounds
        panelist.setBackground(bg);
        rootPanel.setBackground(bg);
        Electronics_panel.setBackground(header);
        panel.setBackground(bg);
        panelButton.setBackground(bg);
        table_panel.setBackground(bg);
        description_panel.setBackground(bg);

        // Set text field backgrounds
        name_field.setBackground(bg);
        vendor_field.setBackground(bg);
        price_field.setBackground(bg);
        warranty_field.setBackground(bg);
        model_field.setBackground(bg);
        brand_field.setBackground(bg);
        LMD_field.setBackground(bg);
        textArea1.setBackground(bg);
        location_combobox.setBackground(bg);

        // Set spinner background
        spinner1.setBackground(bg);

        // Customize spinner text field
        JComponent editorComp = spinner1.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Set foreground colors
        name_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        warranty_field.setForeground(placeholderColor);
        model_field.setForeground(black);
        brand_field.setForeground(black);
        LMD_field.setForeground(black);
        textArea1.setForeground(black);
        location_combobox.setForeground(black);

        // Set label colors
        Electronics_label.setForeground(Color.WHITE);
        name_label.setForeground(black);
        quantity_label.setForeground(black);
        location_label.setForeground(black);
        vendor_label.setForeground(black);
        price_label.setForeground(black);
        warranty_label.setForeground(black);
        model_label.setForeground(black);
        brand_label.setForeground(black);
        LMD_label.setForeground(black);
        description_label.setForeground(black);
        maintenance_label.setForeground(black);

        // Set button colors
        ADDButton.setBackground(new Color(70, 130, 180));
        CLEARButton.setBackground(new Color(70, 130, 180));
        UPDATEButton.setBackground(new Color(70, 130, 180));
        REMOVEButton.setBackground(new Color(70, 130, 180));
        ADDButton.setForeground(Color.white);
        CLEARButton.setForeground(Color.white);
        UPDATEButton.setForeground(Color.white);
        REMOVEButton.setForeground(Color.white);

        // Make buttons opaque
        ADDButton.setOpaque(true);
        CLEARButton.setOpaque(true);
        UPDATEButton.setOpaque(true);
        REMOVEButton.setOpaque(true);

        // Set fonts
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13);

        Electronics_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        warranty_label.setFont(labelFont);
        model_label.setFont(labelFont);
        brand_label.setFont(labelFont);
        LMD_label.setFont(labelFont);
        description_label.setFont(labelFont);
        maintenance_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        warranty_field.setFont(placeholderFont);
        model_field.setFont(fieldFont);
        brand_field.setFont(fieldFont);
        LMD_field.setFont(fieldFont);
        textArea1.setFont(fieldFont);
        location_combobox.setFont(fieldFont);

        // Set spinner font
        spinner1.setFont(fieldFont);

        // Set button fonts
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
    }

    private void setupPlaceholders() {
        // Set placeholder text for warranty field
        warranty_field.setText(WARRANTY_PLACEHOLDER);

        // Set placeholder for LMD field
        LMD_field.setText(LMD_PLACEHOLDER);
        LMD_field.setForeground(new Color(100, 100, 100, 180));
        LMD_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        // Add focus listener for warranty
        warranty_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (warranty_field.getText().equals(WARRANTY_PLACEHOLDER)) {
                    warranty_field.setText("");
                    warranty_field.setForeground(Color.BLACK);
                    warranty_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (warranty_field.getText().isEmpty()) {
                    warranty_field.setText(WARRANTY_PLACEHOLDER);
                    warranty_field.setForeground(new Color(100, 100, 100, 180));
                    warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });

        // Add focus listener for LMD
        LMD_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (LMD_field.getText().equals(LMD_PLACEHOLDER)) {
                    LMD_field.setText("");
                    LMD_field.setForeground(Color.BLACK);
                    LMD_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (LMD_field.getText().isEmpty()) {
                    LMD_field.setText(LMD_PLACEHOLDER);
                    LMD_field.setForeground(new Color(100, 100, 100, 180));
                    LMD_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });
    }

    private void setupMaintenanceListener() {

        maintenanceNeeded.addChangeListener(e -> {
            if (maintenanceNeeded.isSelected()) {

                LMD_field.setBackground(new Color(0xFFE5E5));
                LMD_label.setForeground(new Color(0xFF0000));
                LMD_label.setText("LAST MAINTENANCE DATE: (URGENT!)");
            } else {

                LMD_field.setBackground(new Color(0xF5F5F5));
                LMD_label.setForeground(Color.BLACK);
                LMD_label.setText("LAST MAINTENANCE DATE:");
            }
        });
    }

    // PUBLIC METHODS

    public void clearForm() {
        name_field.setText("");
        spinner1.setValue(1);
        LMD_field.setText(LMD_PLACEHOLDER);
        LMD_field.setForeground(new Color(100, 100, 100, 180));
        LMD_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        vendor_field.setText("");
        price_field.setText("");

        warranty_field.setText(WARRANTY_PLACEHOLDER);
        warranty_field.setForeground(new Color(100, 100, 100, 180));
        warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        model_field.setText("");
        brand_field.setText("");
        textArea1.setText("");
        location_combobox.setSelectedIndex(0);
        maintenanceNeeded.setSelected(false);
    }

    // Action listener methods
    public void addAddButtonListener(ActionListener listener) {
        ADDButton.addActionListener(listener);
    }

    public void addClearButtonListener(ActionListener listener) {
        CLEARButton.addActionListener(listener);
    }

    public void addUpdateButtonListener(ActionListener listener) {
        UPDATEButton.addActionListener(listener);
    }

    public void addRemoveButtonListener(ActionListener listener) {
        REMOVEButton.addActionListener(listener);
    }

    public void addMaintenanceChangeListener(javax.swing.event.ChangeListener listener) {
        maintenanceNeeded.addChangeListener(listener);
    }

    public int getSelectedTableRow() {
        return table1.getSelectedRow();
    }

    public JButton getAddButton() {
        return ADDButton;
    }

    public JButton getClearButton() {
        return CLEARButton;
    }

    public JButton getUpdateButton() {
        return UPDATEButton;
    }

    public JButton getRemoveButton() {
        return REMOVEButton;
    }

    // Form field getter methods
    public String getNameInput() {
        return name_field.getText();
    }

    public int getQuantityInput() {
        return (int) spinner1.getValue();
    }

    public String getLMDInput() {
        String text = LMD_field.getText();
        if (text.equals(LMD_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public String getVendorInput() {
        return vendor_field.getText();
    }

    public String getPriceInput() {
        return price_field.getText();
    }

    public String getWarrantyInput() {
        String text = warranty_field.getText();
        if (text.equals(WARRANTY_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public String getModelInput() {
        return model_field.getText();
    }

    public String getBrandInput() {
        return brand_field.getText();
    }

    public String getDescriptionInput() {
        return textArea1.getText();
    }

    public String getLocationInput() {
        return (String) location_combobox.getSelectedItem();
    }

    public boolean getMaintenanceNeeded() {
        return maintenanceNeeded.isSelected();
    }

    // Setter methods for form fields
    public void setNameInput(String name) {
        name_field.setText(name);
    }

    public void setQuantityInput(int quantity) {
        spinner1.setValue(quantity);
    }

    public void setLMDInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            LMD_field.setText(LMD_PLACEHOLDER);
            LMD_field.setForeground(new Color(100, 100, 100, 180));
            LMD_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            LMD_field.setText(date);
            LMD_field.setForeground(Color.BLACK);
            LMD_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    public void setVendorInput(String vendor) {
        vendor_field.setText(vendor);
    }

    public void setPriceInput(String price) {
        price_field.setText(price);
    }

    public void setWarrantyInput(String warranty) {
        if (warranty == null || warranty.trim().isEmpty()) {
            warranty_field.setText(WARRANTY_PLACEHOLDER);
            warranty_field.setForeground(new Color(100, 100, 100, 180));
            warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            warranty_field.setText(warranty);
            warranty_field.setForeground(Color.BLACK);
            warranty_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    public void setModelInput(String model) {
        model_field.setText(model);
    }

    public void setBrandInput(String brand) {
        brand_field.setText(brand);
    }

    public void setDescriptionInput(String description) {
        textArea1.setText(description);
    }

    public void setLocationInput(String location) {
        location_combobox.setSelectedItem(location);
    }

    public void setMaintenanceNeeded(boolean needsMaintenance) {
        maintenanceNeeded.setSelected(needsMaintenance);

        if (needsMaintenance) {
            LMD_field.setBackground(new Color(0xFFE5E5));
            LMD_label.setForeground(new Color(0xFF0000));
            LMD_label.setText("LAST MAINTENANCE DATE: (URGENT!)");
        } else {
            LMD_field.setBackground(new Color(0xF5F5F5));
            LMD_label.setForeground(Color.BLACK);
            LMD_label.setText("LAST MAINTENANCE DATE:");
        }
    }

    private void createTable() {
        Object[][] data = {
                {"Laptop", 2, "GARAGE", "Tech Vendor", "$999.99", "Working", true},
                {"Monitor", 1, "ELECTRONICS CABINET", "Tech Co", "$249.99", "Needs calibration", false},
                {"Router", 3, "UTILITY ROOM", "Network Inc", "$89.99", "Good condition", true}
        };

        Objects.requireNonNull(table1).setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Qty", "Location", "Vendor", "Price", "Details", "Maintenance"}
        ));

        // Table styling
        table1.setRowHeight(25);
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table1.getTableHeader().setBackground(new Color(70, 130, 180));
        table1.getTableHeader().setForeground(Color.WHITE);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table1.setSelectionBackground(new Color(100, 149, 237));
        table1.setSelectionForeground(Color.WHITE);
        table1.setGridColor(Color.LIGHT_GRAY);
        table1.setShowGrid(true);

        // Custom renderer for Maintenance column (show checkboxes in table)
        table1.getColumnModel().getColumn(6).setCellRenderer(new MaintenanceRenderer());

        // Set column widths
        table1.getColumnModel().getColumn(0).setPreferredWidth(80);
        table1.getColumnModel().getColumn(1).setPreferredWidth(30);
        table1.getColumnModel().getColumn(2).setPreferredWidth(80);
        table1.getColumnModel().getColumn(3).setPreferredWidth(80);
        table1.getColumnModel().getColumn(4).setPreferredWidth(60);
        table1.getColumnModel().getColumn(5).setPreferredWidth(100);
        table1.getColumnModel().getColumn(6).setPreferredWidth(90);
    }

    // Custom renderer for Maintenance column
    private class MaintenanceRenderer extends JCheckBox implements javax.swing.table.TableCellRenderer {
        public MaintenanceRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            if (value instanceof Boolean) {
                setSelected((Boolean) value);

                if ((Boolean) value) {
                    setBackground(new Color(0xFFE5E5));
                    setText("YES");
                } else {
                    setText("NO");
                }
            } else {
                setSelected(false);
                setText("");
            }

            return this;
        }
    }
}