package UI.Panel;

import UI.Utilities.ItemTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Tools extends JPanel {
    // ATTRIBUTES
    private JPanel panelist, rootPanel, tools_panel, panel, table_panel, description_panel;
    private JTextField name_field, size_field, vendor_field, price_field, warranty_field, tooltype_field, material_field;
    private JTextArea textArea1;
    private JLabel tools_label, name_label, quantity_label, location_label, vendor_label, price_label,
            warranty_label, tooltype_label, material_label, requiresmaintenance_label, size_label, description_label,
            maintenanceInterval_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton;
    private JComboBox<String> location_combobox;
    private JTable table1;
    private JPanel panelButton;
    private JCheckBox requiresMaintenanceCheckBox;
    private JPanel radiopanel1;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1, maintenanceIntervalSpinner;
    private JScrollPane scrollPane;

    // Table model for dynamic data
    private DefaultTableModel tableModel;
    private ItemTable sharedData;
    private int selectedRowIndex = -1;
    private String originalItemId = "";

    // Placeholder texts
    private static final String WARRANTY_PLACEHOLDER = "MM/DD/YYYY";

    public Tools() {
        sharedData = ItemTable.getInstance();
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
        createTable();
        setupButtonListeners();
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

        // Initialize radio panel (will now hold checkbox)
        radiopanel1 = new JPanel();

        // Initialize form fields
        name_field = new JTextField(8);
        size_field = new JTextField(8);
        vendor_field = new JTextField(8);
        price_field = new JTextField(8);
        warranty_field = new JTextField(8);
        tooltype_field = new JTextField(8);
        material_field = new JTextField(8);

        textArea1 = new JTextArea(3, 15);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

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
        warranty_label = new JLabel("PURCHASED DATE:");
        tooltype_label = new JLabel("TOOL TYPE:");
        material_label = new JLabel("MATERIAL:");
        size_label = new JLabel("STEEL GRADE:");
        requiresmaintenance_label = new JLabel("REQUIRES MAINTENANCE:");
        maintenanceInterval_label = new JLabel("MAINTENANCE INTERVAL (DAYS):");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "GARAGE", "BASEMENT", "STORAGE ROOM", "TOOLBOX", "UTILITY ROOM",
                "TOOL RACK"
        });

        // Initialize checkbox instead of radio buttons
        requiresMaintenanceCheckBox = new JCheckBox("Requires Maintenance");

        // Initialize maintenance interval spinner
        maintenanceIntervalSpinner = new JSpinner(new SpinnerNumberModel(30, 1, 365, 1));
        JSpinner.NumberEditor intervalEditor = new JSpinner.NumberEditor(maintenanceIntervalSpinner, "#");
        maintenanceIntervalSpinner.setEditor(intervalEditor);

        // Force left alignment
        JComponent intervalEditorComp = maintenanceIntervalSpinner.getEditor();
        if (intervalEditorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) intervalEditorComp).getTextField();
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        table1 = new JTable();
        scrollPane = new JScrollPane(table1);
        textAreaScroll = new JScrollPane(textArea1);

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
        panel.add(maintenanceInterval_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;
        maintenanceIntervalSpinner.setPreferredSize(new Dimension(80, 25));
        panel.add(maintenanceIntervalSpinner, formGbc);

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
        warranty_field.setBackground(bg);
        tooltype_field.setBackground(bg);
        material_field.setBackground(bg);
        textArea1.setBackground(bg);
        location_combobox.setBackground(bg);

        // Set spinner background
        spinner1.setBackground(bg);
        maintenanceIntervalSpinner.setBackground(bg);

        // Customize the spinner editor
        JComponent editorComp = spinner1.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Customize maintenance interval spinner text field
        JComponent intervalEditorComp = maintenanceIntervalSpinner.getEditor();
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
        warranty_field.setForeground(placeholderColor);
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
        warranty_label.setForeground(black);
        tooltype_label.setForeground(black);
        material_label.setForeground(black);
        size_label.setForeground(black);
        requiresmaintenance_label.setForeground(black);
        maintenanceInterval_label.setForeground(black);
        description_label.setForeground(black);

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
        Font checkboxFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13);

        tools_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        warranty_label.setFont(labelFont);
        tooltype_label.setFont(labelFont);
        material_label.setFont(labelFont);
        size_label.setFont(labelFont);
        requiresmaintenance_label.setFont(labelFont);
        maintenanceInterval_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        size_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        warranty_field.setFont(placeholderFont);
        tooltype_field.setFont(fieldFont);
        material_field.setFont(fieldFont);
        textArea1.setFont(fieldFont);
        location_combobox.setFont(fieldFont);

        // Set spinner font
        spinner1.setFont(fieldFont);
        maintenanceIntervalSpinner.setFont(fieldFont);

        // Set checkbox font
        requiresMaintenanceCheckBox.setFont(checkboxFont);

        // Set button fonts
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
    }

    private void setupPlaceholders() {
        // Set placeholder text for warranty field
        warranty_field.setText(WARRANTY_PLACEHOLDER);

        // Add focus listener
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
    }

    private void setupButtonListeners() {
        ADDButton.addActionListener(e -> addItemToTable());
        CLEARButton.addActionListener(e -> clearForm());
        UPDATEButton.addActionListener(e -> updateToMainTable());
        REMOVEButton.addActionListener(e -> removeItemFromTable());

        // Set all buttons to non-focusable for better UX
        ADDButton.setFocusable(false);
        CLEARButton.setFocusable(false);
        UPDATEButton.setFocusable(false);
        REMOVEButton.setFocusable(false);
    }

    private void addItemToTable() {
        // Validate required fields
        if (getNameInput().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String priceInput = getPriceInput().trim();
        if (priceInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Price is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse price to double with 2 decimal places
        double price;
        try {
            // Remove any currency symbols and commas
            String cleanPrice = priceInput.replaceAll("[^\\d.]", "");
            price = Double.parseDouble(cleanPrice);
            // Round to 2 decimal places
            price = Math.round(price * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format! Please enter a valid number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get values from form
        String name = getNameInput();
        int quantity = getQuantityInput();
        String location = getLocationInput();
        String vendor = getVendorInput();

        // Build details string with all additional information
        String details = buildDetailsString();

        // Create item data with double price
        ItemTable.ItemData item = new ItemTable.ItemData(
                "Tools",      // Category
                name,
                quantity,
                location,
                vendor,
                price,        // Double price
                details
        );

        // Add to TEMPORARY storage only (not to main table)
        sharedData.addTemporaryItem(item);

        // Also add to local table with formatted price
        tableModel.addRow(new Object[]{
                name,
                quantity,
                location,
                vendor,
                String.format("$%.2f", price),  // Formatted price for display
                details
        });

        // Clear form after adding
        clearForm();

        // Show success message
        JOptionPane.showMessageDialog(this, "Tool added to local table successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private String buildDetailsString() {
        StringBuilder details = new StringBuilder();

        // Add purchased date if provided
        String warranty = getWarrantyInput();
        if (!warranty.isEmpty() && !warranty.equals(WARRANTY_PLACEHOLDER)) {
            details.append("Purchased: ").append(warranty).append(" | ");
        }

        // Add tool type if provided
        String toolType = getToolTypeInput();
        if (!toolType.trim().isEmpty()) {
            details.append("Type: ").append(toolType).append(" | ");
        }

        // Add material if provided
        String material = getMaterialInput();
        if (!material.trim().isEmpty()) {
            details.append("Material: ").append(material).append(" | ");
        }

        // Add steel grade if provided
        String size = getSizeInput();
        if (!size.trim().isEmpty()) {
            details.append("Steel Grade: ").append(size).append(" | ");
        }

        // Add maintenance information if required
        if (requiresMaintenance()) {
            details.append("Maintenance: Required");
            int interval = getMaintenanceInterval();
            if (interval > 0) {
                details.append(" (Every ").append(interval).append(" days)");
            }
            details.append(" | ");
        } else {
            details.append("Maintenance: Not Required | ");
        }

        // Add description if provided
        String description = getDescriptionInput();
        if (!description.trim().isEmpty()) {
            details.append("Notes: ").append(description);
        }

        // Remove trailing " | " if it exists
        String detailsStr = details.toString();
        if (detailsStr.endsWith(" | ")) {
            detailsStr = detailsStr.substring(0, detailsStr.length() - 3);
        }

        return detailsStr;
    }

    private void updateToMainTable() {
        int tempCount = sharedData.getTemporaryItemsCount();
        if (tempCount == 0) {
            JOptionPane.showMessageDialog(this, "No items in local table to update!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Transfer " + tempCount + " item(s) from local table to main inventory?",
                "Confirm Transfer",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Transfer all temporary items to main table
            sharedData.transferTemporaryToMain();

            // Clear local table
            tableModel.setRowCount(0);

            JOptionPane.showMessageDialog(this,
                    tempCount + " item(s) transferred to main inventory successfully!",
                    "Transfer Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void removeItemFromTable() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to remove!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove this tool from local table?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Get the item name to identify it in temporary storage
            String itemName = (String) tableModel.getValueAt(selectedRow, 0);

            // Find and remove from temporary storage
            java.util.List<ItemTable.ItemData> tempItems = sharedData.getTemporaryItems();
            for (int i = 0; i < tempItems.size(); i++) {
                ItemTable.ItemData item = tempItems.get(i);
                if (item.getName().equals(itemName)) {
                    sharedData.removeTemporaryItem(i);
                    break;
                }
            }

            // Remove from local table
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Tool removed from local table successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // PUBLIC METHODS

    public void clearForm() {
        name_field.setText("");
        spinner1.setValue(1);
        size_field.setText("");
        vendor_field.setText("");
        price_field.setText("");

        // Reset warranty field to placeholder
        warranty_field.setText(WARRANTY_PLACEHOLDER);
        warranty_field.setForeground(new Color(100, 100, 100, 180));
        warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        tooltype_field.setText("");
        material_field.setText("");
        textArea1.setText("");
        location_combobox.setSelectedIndex(0);

        // Reset checkbox to unchecked
        requiresMaintenanceCheckBox.setSelected(false);

        // Reset maintenance interval to default value
        maintenanceIntervalSpinner.setValue(30);

        // Reset selection
        selectedRowIndex = -1;
        originalItemId = "";
    }

    // Clear local table completely
    public void clearLocalTable() {
        int rowCount = tableModel.getRowCount();
        if (rowCount == 0) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Clear all " + rowCount + " item(s) from local table?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.setRowCount(0);
            sharedData.clearTemporaryItems();
            JOptionPane.showMessageDialog(this, "Local table cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Checkbox getter method
    public boolean requiresMaintenance() {
        return requiresMaintenanceCheckBox.isSelected();
    }

    public void setRequiresMaintenance(boolean requires) {
        requiresMaintenanceCheckBox.setSelected(requires);
    }

    // Maintenance interval getter method
    public int getMaintenanceInterval() {
        return (int) maintenanceIntervalSpinner.getValue();
    }

    public void setMaintenanceInterval(int days) {
        maintenanceIntervalSpinner.setValue(days);
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

    public String getSizeInput() {
        return size_field.getText();
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

    public String getToolTypeInput() {
        return tooltype_field.getText();
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

    // Setter methods for form fields
    public void setNameInput(String name) {
        name_field.setText(name);
    }

    public void setQuantityInput(int quantity) {
        spinner1.setValue(quantity);
    }

    public void setSizeInput(String size) {
        size_field.setText(size);
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

    public void setToolTypeInput(String toolType) {
        tooltype_field.setText(toolType);
    }

    public void setMaterialInput(String material) {
        material_field.setText(material);
    }

    public void setDescriptionInput(String description) {
        textArea1.setText(description);
    }

    public void setLocationInput(String location) {
        location_combobox.setSelectedItem(location);
    }

    private void createTable() {
        // Create table model with column names
        String[] columnNames = {"Name", "Qty", "Location", "Vendor", "Price", "Details"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Return proper class for each column
                switch (columnIndex) {
                    case 1: // Quantity column
                        return Integer.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Make table non-editable directly
                return false;
            }
        };

        table1.setModel(tableModel);

        // Create a custom cell renderer for wrapping text
        table1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                // For Name (0), Location (2), Vendor (3), and Details (5) columns, use JTextArea for wrapping
                if (column == 0 || column == 2 || column == 3 || column == 5) {
                    JTextArea textArea = new JTextArea();
                    textArea.setText(value != null ? value.toString() : "");
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setFont(table.getFont());
                    textArea.setOpaque(true);

                    // Set background and foreground colors
                    if (isSelected) {
                        textArea.setBackground(table.getSelectionBackground());
                        textArea.setForeground(table.getSelectionForeground());
                    } else {
                        textArea.setBackground(table.getBackground());
                        textArea.setForeground(table.getForeground());
                    }

                    // Set border
                    textArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

                    return textArea;
                } else {
                    // For other columns (Qty, Price), use default renderer
                    Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    // Center align both Qty (column 1) and Price (column 4)
                    if (comp instanceof JLabel) {
                        ((JLabel) comp).setHorizontalAlignment(SwingConstants.CENTER);
                    }

                    return comp;
                }
            }
        });

        // Set a specific renderer for Integer class (Qty column) to ensure center alignment
        table1.setDefaultRenderer(Integer.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Center align the text
                if (comp instanceof JLabel) {
                    ((JLabel) comp).setHorizontalAlignment(SwingConstants.CENTER);
                }

                return comp;
            }
        });

        // Set initial row height
        table1.setRowHeight(25);

        // Add component listener to adjust row heights dynamically
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

        // Set column widths
        TableColumnModel columnModel = table1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120); // Name
        columnModel.getColumn(1).setPreferredWidth(50);  // Qty
        columnModel.getColumn(2).setPreferredWidth(100); // Location
        columnModel.getColumn(3).setPreferredWidth(120); // Vendor
        columnModel.getColumn(4).setPreferredWidth(80);  // Price
        columnModel.getColumn(5).setPreferredWidth(300); // Details

        // Add mouse listener to populate form when row is clicked
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table1.rowAtPoint(evt.getPoint());
                if (row >= 0 && row < table1.getRowCount()) {
                    populateFormFromTable(row);
                }
            }
        });
    }

    private void adjustRowHeights() {
        for (int row = 0; row < table1.getRowCount(); row++) {
            int maxHeight = 25; // Minimum height

            // Check each column that can wrap text
            for (int col = 0; col < table1.getColumnCount(); col++) {
                if (col == 0 || col == 2 || col == 3 || col == 5) { // Name, Location, Vendor, Details
                    Object cellValue = table1.getValueAt(row, col);
                    if (cellValue != null) {
                        String cellText = cellValue.toString();
                        if (!cellText.isEmpty()) {
                            JTextArea tempTextArea = new JTextArea(cellText);
                            tempTextArea.setLineWrap(true);
                            tempTextArea.setWrapStyleWord(true);
                            tempTextArea.setFont(table1.getFont());
                            tempTextArea.setSize(table1.getColumnModel().getColumn(col).getWidth(), Integer.MAX_VALUE);

                            int textHeight = tempTextArea.getPreferredSize().height + 4; // Add padding
                            maxHeight = Math.max(maxHeight, textHeight);
                        }
                    }
                }
            }

            table1.setRowHeight(row, Math.min(maxHeight, 200)); // Cap at 200px
        }
    }

    private void populateFormFromTable(int row) {
        selectedRowIndex = row;

        // Get values from table
        String name = (String) tableModel.getValueAt(row, 0);
        Integer quantity = (Integer) tableModel.getValueAt(row, 1);
        String location = (String) tableModel.getValueAt(row, 2);
        String vendor = (String) tableModel.getValueAt(row, 3);
        String priceDisplay = (String) tableModel.getValueAt(row, 4);
        String details = (String) tableModel.getValueAt(row, 5);

        // Store original item name for identification
        originalItemId = name;

        // Populate basic fields
        setNameInput(name);
        if (quantity != null) setQuantityInput(quantity);
        setLocationInput(location);
        setVendorInput(vendor);

        // Parse price from formatted string to set in form
        try {
            String cleanPrice = priceDisplay.replaceAll("[^\\d.]", "");
            double price = Double.parseDouble(cleanPrice);
            // Set price without formatting in the input field
            price_field.setText(String.valueOf(price));
        } catch (NumberFormatException e) {
            price_field.setText("");
        }

        // Parse details string to populate additional fields
        if (details != null && !details.isEmpty()) {
            parseDetailsString(details);
        }
    }

    private void parseDetailsString(String details) {
        String[] parts = details.split(" \\| ");

        // Reset additional fields first
        setWarrantyInput("");
        setToolTypeInput("");
        setMaterialInput("");
        setSizeInput("");
        setRequiresMaintenance(false);
        setMaintenanceInterval(30);
        setDescriptionInput("");

        for (String part : parts) {
            if (part.startsWith("Purchased: ")) {
                setWarrantyInput(part.substring(11));
            } else if (part.startsWith("Type: ")) {
                setToolTypeInput(part.substring(6));
            } else if (part.startsWith("Material: ")) {
                setMaterialInput(part.substring(10));
            } else if (part.startsWith("Steel Grade: ")) {
                setSizeInput(part.substring(13));
            } else if (part.startsWith("Maintenance: ")) {
                if (part.contains("Required")) {
                    setRequiresMaintenance(true);
                    // Extract interval if present
                    if (part.contains("Every ")) {
                        String intervalStr = part.substring(part.indexOf("Every ") + 6, part.indexOf(" days)"));
                        try {
                            int interval = Integer.parseInt(intervalStr);
                            setMaintenanceInterval(interval);
                        } catch (NumberFormatException e) {
                            // Keep default value
                        }
                    }
                } else {
                    setRequiresMaintenance(false);
                }
            } else if (part.startsWith("Notes: ")) {
                setDescriptionInput(part.substring(7));
            }
        }
    }

    // Get the table for external access
    public JTable getTable() {
        return table1;
    }
}