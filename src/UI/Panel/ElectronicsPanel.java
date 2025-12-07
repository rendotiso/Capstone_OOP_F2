package UI.Panel;

import Model.Data.InventoryManager;
import UI.Utilities.ItemTable;
import Model.Entities.Electronic;
import Model.Entities.Item;
import Model.Enums.Category;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ElectronicsPanel extends JPanel {

    // ATTRIBUTES
    private JPanel panelist, rootPanel, Electronics_panel, panel, table_panel, description_panel;
    private JTextField name_field, LMD_field, vendor_field, price_field, purchaseDate_field, warranty_field, model_field, brand_field;
    private JTextArea textArea1;
    private JLabel Electronics_label, name_label, quantity_label, location_label, vendor_label,
            price_label,warranty_label,model_label, brand_label, LMD_label,
            description_label,  maintenance_label, purchaseDate_label;
    private JCheckBox maintenanceNeeded;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton, REFRESHButton;
    private JComboBox<String> location_combobox;
    private ItemTable itemTable;
    private JPanel panelButton;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private final InventoryManager inventoryManager;
    private int selectedIndex = -1;

    // Placeholder texts
    private static final String DATE_PLACEHOLDER  = "MM/DD/YYYY";
    private static final String WARRANTY_PLACEHOLDER  = "MM/DD/YYYY";
    private static final String LMD_PLACEHOLDER  = "MM/DD/YYYY";

    public ElectronicsPanel() {
        inventoryManager = InventoryManager.getInstance();
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
        setupMaintenanceListener();
        setupTableSelectionListener();
        setupButtonListeners();
        loadItems();

        // Add listener to adjust row heights when panel is shown
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    itemTable.adjustRowHeights();
                });
            }
        });
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
        purchaseDate_field = new JTextField(8);

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
        warranty_label = new JLabel("WARRANTY DATE:");
        model_label = new JLabel("MODEL:");
        brand_label = new JLabel("BRAND:");
        LMD_label = new JLabel("LAST MAINTENANCE DATE:");
        description_label = new JLabel("DESCRIPTION/NOTE:");
        maintenance_label = new JLabel("YEARLY MAINTENANCE NEEDED? ");
        purchaseDate_label = new JLabel("PURCHASE DATE: ");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");
        REFRESHButton = new JButton("REFRESH");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "GARAGE", "BASEMENT", "UTILITY ROOM", "STORAGE ROOM", "SHELF", "DRAWER", "ELECTRONICS CABINET"
        });

        textAreaScroll = new JScrollPane(textArea1);
        String[] columnNames = {"Name", "Quantity", "Location", "Vendor", "Price", "Details"};
        itemTable = new ItemTable(columnNames);
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

        // Row 5: Purchase Date
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(purchaseDate_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        purchaseDate_field.setPreferredSize(new Dimension(80, 25));
        panel.add(purchaseDate_field, formGbc);

        row++;

        // Row 6: Warranty Date
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

        // Row 7: Model
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

        // Row 8: Brand
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

        // Row 9: Maintenance Needed (NEW ROW)
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

        // Row 10: LMD - MOVED BELOW MAINTENANCE
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

        // Row 11: Description/Note
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
        panelButton.add(REFRESHButton);
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
        // FIX: Use itemTable directly instead of scrollPane
        table_panel.add(itemTable, BorderLayout.CENTER);

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
        Color header = new Color(0x4682B4);
        Color black = new Color(-16777216);
        Color bg = new Color(0xF5F5F5);
        Color placeholderColor = new Color(100, 100, 100, 180);
        Color buttonColor = new Color(70, 130, 180);

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
        purchaseDate_label.setForeground(black);
        warranty_label.setForeground(black);
        model_label.setForeground(black);
        brand_label.setForeground(black);
        LMD_label.setForeground(black);
        description_label.setForeground(black);
        maintenance_label.setForeground(black);

        // Set button colors
        ADDButton.setBackground(buttonColor);
        CLEARButton.setBackground(buttonColor);
        UPDATEButton.setBackground(buttonColor);
        REMOVEButton.setBackground(buttonColor);
        REFRESHButton.setBackground(buttonColor);
        ADDButton.setForeground(Color.white);
        CLEARButton.setForeground(Color.white);
        UPDATEButton.setForeground(Color.white);
        REMOVEButton.setForeground(Color.white);
        REFRESHButton.setForeground(Color.white);

        // Make buttons opaque
        ADDButton.setOpaque(true);
        CLEARButton.setOpaque(true);
        UPDATEButton.setOpaque(true);
        REMOVEButton.setOpaque(true);
        REFRESHButton.setOpaque(true);

        ADDButton.setFocusable(false);
        UPDATEButton.setFocusable(false);
        REMOVEButton.setFocusable(false);
        CLEARButton.setFocusable(false);
        REFRESHButton.setFocusable(false);

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
        purchaseDate_label.setFont(labelFont);
        warranty_label.setFont(labelFont);
        model_label.setFont(labelFont);
        brand_label.setFont(labelFont);
        LMD_label.setFont(labelFont);
        description_label.setFont(labelFont);
        maintenance_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        purchaseDate_field.setFont(fieldFont);
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
        REFRESHButton.setFont(buttonFont);
    }
    private void setupPlaceholders() {
        LMD_field.setText(LMD_PLACEHOLDER);
        LMD_field.setForeground(new Color(100, 100, 100, 180));
        LMD_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        warranty_field.setText(WARRANTY_PLACEHOLDER);
        warranty_field.setForeground(new Color(100, 100, 100, 180));
        warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        purchaseDate_field.setText(DATE_PLACEHOLDER);
        purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
        purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

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
                    warranty_field.setText(WARRANTY_PLACEHOLDER); // Use correct constant
                    warranty_field.setForeground(new Color(100, 100, 100, 180));
                    warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });

        purchaseDate_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (purchaseDate_field.getText().equals(DATE_PLACEHOLDER)) {
                    purchaseDate_field.setText("");
                    purchaseDate_field.setForeground(Color.BLACK);
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (purchaseDate_field.getText().isEmpty()) { // Fixed: use purchaseDate_field
                    purchaseDate_field.setText(DATE_PLACEHOLDER);
                    purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });

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

    //GETTERS
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
    public String getPurchaseDateInput() {
        String text = purchaseDate_field.getText();
        if (text.equals(DATE_PLACEHOLDER)) {
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


    // Setter
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
    public void setPurchaseDateInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            purchaseDate_field.setText(DATE_PLACEHOLDER);
            purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
            purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            purchaseDate_field.setText(date);
            purchaseDate_field.setForeground(Color.BLACK);
            purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

    // DATA LOADING AND ACTION LISTENERS

    private void loadItems() {
        itemTable.clearTable();

        // Force reload from file to get latest data
        inventoryManager.loadFromFile();

        inventoryManager.getItemsByCategory(Category.ELECTRONICS).stream()
                .filter(Electronic.class::isInstance)
                .map(Electronic.class::cast)
                .forEach(electronic -> {
                    itemTable.addRow(new Object[]{
                            electronic.getName(),
                            electronic.getQuantity(),
                            electronic.getLocation(),
                            electronic.getVendor(),
                            electronic.getPurchasePrice(),
                            electronic.descriptionDetails()
                    });
                });

        itemTable.adjustRowHeights();
    }

    private double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return 0.0;
        }
        String cleaned = priceStr.replace("$", "").replace(",", "").trim();
        return Double.parseDouble(cleaned);
    }

    private Electronic createElectronicFromForm() {
        return new Electronic(
                getNameInput(),
                getDescriptionInput(),
                getQuantityInput(),
                parsePrice(getPriceInput()),
                getPurchaseDateInput(),
                getVendorInput(),
                getLocationInput(),
                getWarrantyInput(),
                getBrandInput(),
                getModelInput(),
                getMaintenanceNeeded(),
                getLMDInput()
        );
    }

    private boolean validateForm() {
        if (getNameInput().trim().isEmpty()) {
            showError("Name cannot be empty!");
            return false;
        }

        try {
            double price = parsePrice(getPriceInput());
            if (price <= 0) {
                showError("Price must be greater than 0");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Price must be a valid number");
            return false;
        }

        if (getQuantityInput() <= 0) {
            showError("Quantity must be greater than 0");
            return false;
        }

        if (getBrandInput().trim().isEmpty()) {
            showError("Brand cannot be empty!");
            return false;
        }

        if (getModelInput().trim().isEmpty()) {
            showError("Model cannot be empty!");
            return false;
        }

        if (!validateDateField(getWarrantyInput(), "Warranty Date")) return false;
        if (!validateDateField(getPurchaseDateInput(), "Purchase Date")) return false;
        if (!validateDateField(getLMDInput(), "Last Maintenance Date")) return false;

        if (isFutureDate(getLMDInput())) {
            showError("Last Maintenance Date cannot be a future date");
            return false;
        }

        return true;
    }

    private boolean isFutureDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate inputDate = LocalDate.parse(dateStr, formatter);
            LocalDate today = LocalDate.now();
            return inputDate.isAfter(today);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateDateField(String dateText, String fieldName) {
        if (!dateText.isEmpty() && !dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showError(fieldName + " must be in MM/DD/YYYY format");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // ============ EVENT HANDLERS ============

    public void setupButtonListeners() {
        ADDButton.addActionListener(e -> addItem());
        UPDATEButton.addActionListener(e -> updateItem());
        REMOVEButton.addActionListener(e -> removeItem());
        CLEARButton.addActionListener(e -> clearForm());
        REFRESHButton.addActionListener(e -> refreshForm());
    }

    private void addItem() {
        try {
            if (validateForm()) {
                Electronic electronic = createElectronicFromForm();
                inventoryManager.addItem(electronic);
                loadItems();
                clearForm();
                showSuccess("Electronic item added successfully!");
            }
        } catch (IOException e) {
            showError("Error saving item: " + e.getMessage());
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void updateItem() {
        if (selectedIndex >= 0) {
            try {
                if (validateForm()) {
                    Electronic electronic = createElectronicFromForm();
                    List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> electronicsItems = inventoryManager.getItemsByCategory(Category.ELECTRONICS);

                    if (selectedIndex < electronicsItems.size()) {
                        Item originalItem = electronicsItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, electronic);
                            loadItems();
                            clearForm();
                            selectedIndex = -1;
                            showSuccess("Electronic item updated successfully!");
                        }
                    }
                }
            } catch (Exception e) {
                showError("Update Error: " + e.getMessage());
            }
        } else {
            showError("Please select a row to update!");
        }
    }

    private void removeItem() {
        if (selectedIndex >= 0) {
            try {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove this electronic item?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    List<Item> electronicsItems = inventoryManager.getItemsByCategory(Category.ELECTRONICS);

                    if (selectedIndex < electronicsItems.size()) {
                        Item itemToRemove = electronicsItems.get(selectedIndex);
                        inventoryManager.removeItem(itemToRemove);
                        loadItems();
                        clearForm();
                        selectedIndex = -1;
                        showSuccess("Item removed successfully!");
                    }
                }
            } catch (Exception e) {
                showError("Error removing item: " + e.getMessage());
            }
        } else {
            showError("Please select a row to remove!");
        }
    }

    private void refreshForm() {
        loadItems();
        clearForm();
        selectedIndex = -1;
    }

    private void clearForm() {
        // Clear text fields
        name_field.setText("");
        vendor_field.setText("");
        price_field.setText("");
        model_field.setText("");
        brand_field.setText("");
        textArea1.setText("");

        // Reset spinner
        spinner1.setValue(1);

        // Reset combo box
        location_combobox.setSelectedIndex(0);

        // Reset checkbox
        maintenanceNeeded.setSelected(false);

        // Reset date fields to placeholders
        setFieldToPlaceholder(LMD_field, LMD_PLACEHOLDER);
        setFieldToPlaceholder(warranty_field, WARRANTY_PLACEHOLDER);
        setFieldToPlaceholder(purchaseDate_field, DATE_PLACEHOLDER);

        // Clear table selection
        itemTable.clearSelection();
    }

    private void setFieldToPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(new Color(100, 100, 100, 180));
        field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
    }

    private void setupTableSelectionListener() {
        itemTable.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = itemTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedIndex = selectedRow;
                    populateFormFromSelectedRow(selectedRow);
                }
            }
        });
    }

    private void populateFormFromSelectedRow(int selectedRow) {
        try {
            List<Item> electronicsItems = inventoryManager.getItemsByCategory(Category.ELECTRONICS);

            if (selectedRow < electronicsItems.size()) {
                Electronic electronic = (Electronic) electronicsItems.get(selectedRow);
                populateForm(electronic);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Electronic electronic) {
        setNameInput(electronic.getName());
        setQuantityInput(electronic.getQuantity());
        setVendorInput(electronic.getVendor());
        setPriceInput(String.valueOf(electronic.getPurchasePrice()));
        setPurchaseDateInput(electronic.getPurchaseDate());
        setWarrantyInput(electronic.getWarrantyPeriod());
        setModelInput(electronic.getModel());
        setBrandInput(electronic.getBrand());
        setDescriptionInput(electronic.getDescription());
        setLocationInput(electronic.getLocation());
        setMaintenanceNeeded(electronic.getMaintenanceNeeded());
        setLMDInput(electronic.getLastMaintenanceDate());
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

}
