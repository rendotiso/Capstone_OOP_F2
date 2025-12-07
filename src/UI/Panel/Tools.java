package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Tool;
import Model.Entities.Item;
import Model.Enums.Category;
import UI.Utilities.ItemTable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Tools extends JPanel {
    // ATTRIBUTES
    private JPanel panelist, rootPanel, tools_panel, panel, table_panel, description_panel;
    private JTextField name_field, size_field, vendor_field, price_field, purchased_field, tooltype_field, material_field, LastMaintenanceDate_field;
    private JTextArea textArea1;
    private JLabel tools_label, name_label, quantity_label, location_label, vendor_label, price_label,
            purchase_label, tooltype_label, material_label, requiresmaintenance_label, size_label, description_label,
            maintenanceIntervalDateDays_label, LastMaintenanceDate_label, maintenanceNeeded_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton, REFRESHButton;
    private JComboBox<String> location_combobox;
    private JTable table1;
    private JPanel panelButton;
    private JCheckBox requiresMaintenanceCheckBox, maintenanceNeededCheckBox;
    private JPanel radiopanel1, maintenancePanel;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1, maintenanceIntervalDateDays;
    private JScrollPane scrollPane;
    private ItemTable itemTable;
    private final InventoryManager inventoryManager;
    private int selectedIndex = -1;

    //Placeholders texts
    private static final String DATE_PLACEHOLDER = "MM/DD/YYYY";
    private static final String PURCHASED_PLACEHOLDER = "MM/DD/YYYY";
    private static final String LastMaintenanceDate_PLACEHOLDER = "MM/DD/YYYY";

    public Tools() {
        inventoryManager = InventoryManager.getInstance();
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
        setupMaintenanceListener();
        setupButtonListeners();
        setupTableSelectionListener();
    }

    private void initComponents() {
        // Initialize panels
        panelist = new JPanel();
        rootPanel = new JPanel();
        tools_panel = new JPanel();
        panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();
        panelButton = new JPanel();
        maintenancePanel = new JPanel();

        // Initialize radio panel (will now hold checkbox)
        radiopanel1 = new JPanel();

        // Initialize form fields
        name_field = new JTextField(8);
        size_field = new JTextField(8);
        vendor_field = new JTextField(8);
        price_field = new JTextField(8);
        purchased_field = new JTextField(8);
        tooltype_field = new JTextField(8);
        material_field = new JTextField(8);
        LastMaintenanceDate_field = new JTextField(8);

        textArea1 = new JTextArea(3, 15);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        // Initialize checkbox
        maintenanceNeededCheckBox = new JCheckBox();

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
        tools_label = new JLabel("TOOLS");
        name_label = new JLabel("NAME:");
        quantity_label = new JLabel("QUANTITY:");
        location_label = new JLabel("LOCATION:");
        vendor_label = new JLabel("VENDOR:");
        price_label = new JLabel("PRICE:");
        purchase_label = new JLabel("PURCHASED DATE:");
        tooltype_label = new JLabel("TOOL TYPE:");
        material_label = new JLabel("MATERIAL:");
        size_label = new JLabel("STEEL GRADE:");
        requiresmaintenance_label = new JLabel("REQUIRES MAINTENANCE:");
        maintenanceIntervalDateDays_label = new JLabel("MAINTENANCE INTERVAL (DAYS):");
        maintenanceNeeded_label = new JLabel("MAINTENANCE NEEDED:");
        LastMaintenanceDate_label = new JLabel("LAST MAINTENANCE DATE:");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");
        REFRESHButton = new JButton("REFRESH");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "GARAGE", "BASEMENT", "STORAGE ROOM", "TOOLBOX", "UTILITY ROOM",
                "TOOL RACK"
        });

        String[] columnNames = {"Name", "Quantity", "Location", "Vendor", "Price", "Details"};
        itemTable = new ItemTable(columnNames);
        textAreaScroll = new JScrollPane(textArea1);
        scrollPane = new JScrollPane(table1);

        // Initialize checkbox instead of radio butt    ons
        requiresMaintenanceCheckBox = new JCheckBox("Requires Maintenance");
        maintenanceNeededCheckBox = new JCheckBox();

        // Initialize maintenance interval spinner
        maintenanceIntervalDateDays = new JSpinner(new SpinnerNumberModel(30, 1, 365, 1));
        JSpinner.NumberEditor intervalEditor = new JSpinner.NumberEditor(maintenanceIntervalDateDays, "#");
        maintenanceIntervalDateDays.setEditor(intervalEditor);

        // Force left alignment
        JComponent intervalEditorComp = maintenanceIntervalDateDays.getEditor();
        if (intervalEditorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) intervalEditorComp).getTextField();
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        requiresMaintenanceCheckBox.setFocusable(false);
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
        tools_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tools_panel.add(tools_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(tools_panel, gbc);

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
        panel.add(purchase_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        purchased_field.setPreferredSize(new Dimension(80, 25));
        panel.add(purchased_field, formGbc);

        row++;

        // Row 6: Tool Type
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(tooltype_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        tooltype_field.setPreferredSize(new Dimension(80, 25));
        panel.add(tooltype_field, formGbc);

        row++;

        // Row 7: Material
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(material_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        material_field.setPreferredSize(new Dimension(80, 25));
        panel.add(material_field, formGbc);

        row++;

        // Row 8: SIZE
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(size_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        size_field.setPreferredSize(new Dimension(80, 25));
        panel.add(size_field, formGbc);

        row++;

        // Row 9: Requires Maintenance (with checkbox)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(requiresmaintenance_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        // Add checkbox instead of radio buttons
        radiopanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radiopanel1.add(requiresMaintenanceCheckBox);
        panel.add(radiopanel1, formGbc);

        row++;

        // Row 10: Maintenance Interval (new row)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(maintenanceIntervalDateDays_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;
        maintenanceIntervalDateDays.setPreferredSize(new Dimension(80, 25));
        panel.add(maintenanceIntervalDateDays, formGbc);

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
        // Set background colors
        Color header = new Color(0x4682B4);
        Color black = new Color(-16777216);
        Color bg = new Color(0xF5F5F5);
        Color placeholderColor = new Color(100, 100, 100, 180);
        Color buttonColor = new Color(70, 130, 180);

        // Set panels opaque
        panelist.setOpaque(true);
        rootPanel.setOpaque(true);
        tools_panel.setOpaque(true);
        panel.setOpaque(true);
        table_panel.setOpaque(true);
        description_panel.setOpaque(true);
        panelButton.setOpaque(true);
        radiopanel1.setOpaque(true);

        // Set panel backgrounds
        panelist.setBackground(bg);
        rootPanel.setBackground(bg);
        tools_panel.setBackground(header);
        panel.setBackground(bg);
        panelButton.setBackground(bg);
        table_panel.setBackground(bg);
        description_panel.setBackground(bg);
        radiopanel1.setBackground(bg);

        // Set text field backgrounds
        name_field.setBackground(bg);
        size_field.setBackground(bg);
        vendor_field.setBackground(bg);
        price_field.setBackground(bg);
        purchased_field.setBackground(bg);
        tooltype_field.setBackground(bg);
        material_field.setBackground(bg);
        textArea1.setBackground(bg);
        location_combobox.setBackground(bg);

        // Set spinner background
        spinner1.setBackground(bg);
        maintenanceIntervalDateDays.setBackground(bg);

        // Customize the spinner editor
        JComponent editorComp = spinner1.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Customize maintenance interval spinner text field
        JComponent intervalEditorComp = maintenanceIntervalDateDays.getEditor();
        if (intervalEditorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) intervalEditorComp).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Set checkbox background
        requiresMaintenanceCheckBox.setBackground(bg);
        requiresMaintenanceCheckBox.setOpaque(true);

        // Set foreground colors
        name_field.setForeground(black);
        size_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        purchased_field.setForeground(placeholderColor);
        tooltype_field.setForeground(black);
        material_field.setForeground(black);
        textArea1.setForeground(black);
        location_combobox.setForeground(black);

        // Set checkbox text color
        requiresMaintenanceCheckBox.setForeground(black);

        // Set label colors
        tools_label.setForeground(Color.WHITE);
        name_label.setForeground(black);
        quantity_label.setForeground(black);
        location_label.setForeground(black);
        vendor_label.setForeground(black);
        price_label.setForeground(black);
        purchase_label.setForeground(black);
        tooltype_label.setForeground(black);
        material_label.setForeground(black);
        size_label.setForeground(black);
        requiresmaintenance_label.setForeground(black);
        maintenanceIntervalDateDays_label.setForeground(black);
        description_label.setForeground(black);

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
        Font checkboxFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13);

        tools_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        purchase_label.setFont(labelFont);
        tooltype_label.setFont(labelFont);
        material_label.setFont(labelFont);
        size_label.setFont(labelFont);
        requiresmaintenance_label.setFont(labelFont);
        maintenanceIntervalDateDays_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        size_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        purchased_field.setFont(placeholderFont);
        tooltype_field.setFont(fieldFont);
        material_field.setFont(fieldFont);
        textArea1.setFont(fieldFont);
        location_combobox.setFont(fieldFont);

        // Set spinner font
        spinner1.setFont(fieldFont);
        maintenanceIntervalDateDays.setFont(fieldFont);

        // Set checkbox font
        requiresMaintenanceCheckBox.setFont(checkboxFont);

        // Set button fonts
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);

        ADDButton.setFocusable(false);
        CLEARButton.setFocusable(false);
        UPDATEButton.setFocusable(false);
        REMOVEButton.setFocusable(false);
        REFRESHButton.setFocusable(false);
    }

    private void setupPlaceholders() {
        // Set placeholder text for warranty field
        purchased_field.setText(DATE_PLACEHOLDER);

        // Set placeholder for LastMaintenanceDate field
        LastMaintenanceDate_field.setText(LastMaintenanceDate_PLACEHOLDER);

        // Add focus listener
        purchased_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (purchased_field.getText().equals(DATE_PLACEHOLDER)) {
                    purchased_field.setText("");
                    purchased_field.setForeground(Color.BLACK);
                    purchased_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (purchased_field.getText().isEmpty()) {
                    purchased_field.setText(DATE_PLACEHOLDER);
                    purchased_field.setForeground(new Color(100, 100, 100, 180));
                    purchased_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
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
    public String getLastMaintenanceDateInput() {
        String text = LastMaintenanceDate_field.getText();
        if (text.equals(LastMaintenanceDate_PLACEHOLDER)) {
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
    public String getPurchaseDateInput() {
        String text = purchased_field.getText();
        if (text.equals(PURCHASED_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public String getToolTypeInput() {
        return tooltype_field.getText();
    }

    public String getSizeInput() {
        return size_field.getText();
    }

    public String getMaterialInput() {
        return material_field.getText();
    }

    public String getDescriptionInput() {
        return textArea1.getText();
    }
    public String getLocationInput() {
        return (String) location_combobox.getSelectedItem();
    }

    public boolean getRequiresMaintenance() {
        return requiresMaintenanceCheckBox.isSelected();
    }

    public boolean getMaintenanceNeeded() {
        return maintenanceNeededCheckBox.isSelected();
    }

    public int getMaintenanceIntervalDateDays() {
        return (int) maintenanceIntervalDateDays.getValue();
    }

    // SETTERS
    public void setNameInput(String name) {
        name_field.setText(name);
    }
    public void setQuantityInput(int quantity) {
        spinner1.setValue(quantity);
    }
    public void setLastMaintenanceDateInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            LastMaintenanceDate_field.setText(LastMaintenanceDate_PLACEHOLDER);
            LastMaintenanceDate_field.setForeground(new Color(100, 100, 100, 180));
            LastMaintenanceDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            LastMaintenanceDate_field.setText(date);
            LastMaintenanceDate_field.setForeground(Color.BLACK);
            LastMaintenanceDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }
    public void setPurchaseDateInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            purchased_field.setText(PURCHASED_PLACEHOLDER);
            purchased_field.setForeground(new Color(100, 100, 100, 180));
            purchased_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            purchased_field.setText(date);
            purchased_field.setForeground(Color.BLACK);
            purchased_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }
    public void setVendorInput(String vendor) {
        vendor_field.setText(vendor);
    }
    public void setPriceInput(String price) {
        price_field.setText(price);
    }

    public void setToolTypeInput(String tooltype) {
        tooltype_field.setText(tooltype);
    }
    public void setSizeInput(String size) {
        size_field.setText(size);
    }
    public void setDescriptionInput(String description) {
        textArea1.setText(description);
    }

    public void setMaterialInput(String material) {
        material_field.setText(material);
    }

    public void setLocationInput(String location) {
        location_combobox.setSelectedItem(location);
    }
    public void setMaintenanceNeeded(boolean needsMaintenance) {
        maintenanceNeededCheckBox.setSelected(needsMaintenance);

        if (needsMaintenance) {
            LastMaintenanceDate_field.setBackground(new Color(0xFFE5E5));
            LastMaintenanceDate_label.setForeground(new Color(0xFF0000));
            LastMaintenanceDate_label.setText("LAST MAINTENANCE DATE: (URGENT!)");
        } else {
            LastMaintenanceDate_field.setBackground(new Color(0xF5F5F5));
            LastMaintenanceDate_label.setForeground(Color.BLACK);
            LastMaintenanceDate_label.setText("LAST MAINTENANCE DATE:");
        }
    }
    
    public void setmaintenanceIntervalDateDaysDays(JSpinner maintenanceIntervalDateDays){
        this.maintenanceIntervalDateDays = maintenanceIntervalDateDays;
    }

    // DATA LOADING AND ACTION LISTENERS

    private void loadItems() {
        itemTable.clearTable();

        // Force reload from file to get latest data
        inventoryManager.loadFromFile();

        inventoryManager.getItemsByCategory(Category.TOOLS).stream()
                .filter(Tool.class::isInstance)
                .map(Tool.class::cast)
                .forEach(tools -> {
                    itemTable.addRow(new Object[]{
                            tools.getName(),
                            tools.getQuantity(),
                            tools.getLocation(),
                            tools.getVendor(),
                            tools.getPurchasePrice(),
                            tools.getDescription()
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

    private Tool createToolFromForm() {
        return new Tool(
                getNameInput(),
                getDescriptionInput(),
                getQuantityInput(),
                parsePrice(getPriceInput()),
                getPurchaseDateInput(),
                getVendorInput(),
                getLocationInput(),
                getToolTypeInput(),
                getSizeInput(),
                getMaterialInput(),
                getMaintenanceNeeded(),
                getLastMaintenanceDateInput(),
                getMaintenanceIntervalDateDays() // THE FIX FOR THIS: MAKE INTERNVAL DAYS GETTER AND SETTER METHODS HERE
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

        if (getToolTypeInput().trim().isEmpty()) {
            showError("Tool Type cannot be empty!");
            return false;
        }

        if (getSizeInput().trim().isEmpty()) {
            showError("Size cannot be empty!");
            return false;
        }

        if (!validateDateField(getPurchaseDateInput(), "Purchase Date")) return false;
        if (!validateDateField(getLastMaintenanceDateInput(), "Last Maintenance Date")) return false;

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

    // DATA LOADING AND ACTION LISTENERS
    private void setupButtonListeners() {
        ADDButton.addActionListener(e -> addItem());
        UPDATEButton.addActionListener(e -> updateItem());
        REMOVEButton.addActionListener(e -> removeItem());
        CLEARButton.addActionListener(e -> clearForm());
        REFRESHButton.addActionListener(e -> refreshForm());
    }

    private void addItem() {
        try {
            if (validateForm()) {
                Tool tool = createToolFromForm();
                inventoryManager.addItem(tool);
                loadItems();
                clearForm();
                showSuccess("tool item added successfully!");
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
                    Tool tool = createToolFromForm();
                    java.util.List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> toolsItems = inventoryManager.getItemsByCategory(Category.TOOLS);

                    if (selectedIndex < toolsItems.size()) {
                        Item originalItem = toolsItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, tool);
                            loadItems();
                            clearForm();
                            selectedIndex = -1;
                            showSuccess("tool item updated successfully!");
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
                        "Are you sure you want to remove this tool item?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    List<Item> toolsItems = inventoryManager.getItemsByCategory(Category.TOOLS);

                    if (selectedIndex < toolsItems.size()) {
                        Item itemToRemove = toolsItems.get(selectedIndex);
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
        tooltype_field.setText("");
        size_field.setText("");
        textArea1.setText("");

        // Reset spinner
        spinner1.setValue(1);

        // Reset combo box
        location_combobox.setSelectedIndex(0);

        // Reset checkbox
        maintenanceNeededCheckBox.setSelected(false);

        // Reset date fields to placeholders
        setFieldToPlaceholder(LastMaintenanceDate_field, LastMaintenanceDate_PLACEHOLDER);
        setFieldToPlaceholder(purchased_field, PURCHASED_PLACEHOLDER);
        setFieldToPlaceholder(purchased_field, DATE_PLACEHOLDER);

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
            List<Item> toolsItems = inventoryManager.getItemsByCategory(Category.TOOLS);

            if (selectedRow < toolsItems.size()) {
                Tool tool = (Tool) toolsItems.get(selectedRow);
                populateForm(tool);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Tool tool) {
        setNameInput(tool.getName());
        setQuantityInput(tool.getQuantity());
        setVendorInput(tool.getVendor());
        setPriceInput(String.valueOf(tool.getPurchasePrice()));
        setPurchaseDateInput(tool.getPurchaseDate());
        setToolTypeInput(tool.getToolType());
        setSizeInput(tool.getSteelGrade());  // DONT FORGET TO CHANGE THE SIZEINPUT TO BE STEELGRADEINPUT
        setDescriptionInput(tool.getDescription());
        setLocationInput(tool.getLocation());
        setMaintenanceNeeded(tool.getMaintenanceNeeded());
        setLastMaintenanceDateInput(tool.getLastMaintenanceDate());
    }

    //(copy paste here the needed stuff, already implemented the maitenance listener since its
    // the only one with the same logic as tools)
    // yes. ikaw fix the red ani HHAHHHAHHAHAHHA, nag red siya so ako ra gi comment out
    // Requires maintenance is Maintenance Needed, i-fix na pls
    // and I notice ang uban data/panels nag butang og "warranty_label" for purchaseDate_label.
    // i-tarung iyang field names
    // i-add sad ang iyang call method didto sa constructor for the placeholder for the panels that requires it


    private void setupMaintenanceListener() {
        maintenanceNeededCheckBox.addChangeListener(e -> {
            if (maintenanceNeededCheckBox.isSelected()) {

                LastMaintenanceDate_field.setBackground(new Color(0xFFE5E5));
                LastMaintenanceDate_label.setForeground(new Color(0xFF0000));
                LastMaintenanceDate_label.setText("LAST MAINTENANCE DATE: (URGENT!)");
                maintenanceNeeded_label.setForeground(new Color(0xFF0000));
            } else {

                LastMaintenanceDate_field.setBackground(new Color(0xF5F5F5));
                LastMaintenanceDate_label.setForeground(Color.BLACK);
                LastMaintenanceDate_label.setText("LAST MAINTENANCE DATE:");
                maintenanceNeeded_label.setForeground(Color.BLACK);
            }
        });
    }
}