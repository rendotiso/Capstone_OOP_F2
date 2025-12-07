package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Miscellaneous;
import Model.Entities.Item;
import UI.Utilities.ItemTable;
import Model.Enums.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MiscPanel extends JPanel {

    // ATTRIBUTES
    private JPanel panelist, rootPanel, miscellaneous_panel, panel, table_panel, description_panel;
    private JTextField name_field, vendor_field, price_field, purchaseDate_field, itemtype_field, usage_field;
    private JTextArea textArea1;
    private JLabel miscellaneous_label, name_label, quantity_label, location_label, vendor_label,
            price_label, purchaseDate_label, itemtype_label, usage_label, condition_label, description_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton, REFRESHButton;
    private JComboBox<String> location_combobox;
    private ItemTable itemTable;
    private JPanel panelButton;
    private JRadioButton INTACTRadioButton;
    private JRadioButton DAMAGEDRadioButton;
    private JPanel radiopanel1;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private final InventoryManager inventoryManager;
    private int selectedIndex = -1;

    // Button groups for radio buttons
    private ButtonGroup conditionGroup;

    // Placeholder texts
    private static final String purchaseDate_PLACEHOLDER = "MM/DD/YYYY";

    public MiscPanel() {
        inventoryManager = InventoryManager.getInstance();
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
    }

    private void initComponents() {
        // Initialize panels
        panelist = new JPanel();
        rootPanel = new JPanel();
        miscellaneous_panel = new JPanel();
        panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();
        panelButton = new JPanel();

        // Initialize radio panel
        radiopanel1 = new JPanel();

        // Initialize form fields
        name_field = new JTextField(8);
        vendor_field = new JTextField(8);
        price_field = new JTextField(8);
        purchaseDate_field = new JTextField(8);
        itemtype_field = new JTextField(8);
        usage_field = new JTextField(8);
        textArea1 = new JTextArea(3, 15);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        // Initialize spinner for quantity (replacing JTextField)
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

        // Initialize labels - FIXED TYPO: "venodor_label" to "vendor_label"
        miscellaneous_label = new JLabel("MISCELLANEOUS");
        name_label = new JLabel("NAME:");
        quantity_label = new JLabel("QUANTITY:");
        location_label = new JLabel("LOCATION:");
        vendor_label = new JLabel("VENDOR:");  // Fixed from "venodor_label"
        price_label = new JLabel("PRICE:");
        purchaseDate_label = new JLabel("PURCHASED DATE:");
        itemtype_label = new JLabel("ITEM TYPE:");  // Fixed from "itemtyep_label"
        usage_label = new JLabel("USAGE:");
        condition_label = new JLabel("CONDITION:");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");
        REFRESHButton = new JButton("REFRESH");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "STORAGE ROOM", "CABINET", "SHELF",
                "DRAWER", "HALLWAY CABINET", "CONTAINER RACK", "ATTIC", "STORAGE"
        });

        // Initialize radio buttons
        INTACTRadioButton = new JRadioButton("INTACT");
        DAMAGEDRadioButton = new JRadioButton("DAMAGED");

        // Create button group
        conditionGroup = new ButtonGroup();
        conditionGroup.add(INTACTRadioButton);
        conditionGroup.add(DAMAGEDRadioButton);

        // Set default selection
        INTACTRadioButton.setSelected(true);  // Default: INTACT

        textAreaScroll = new JScrollPane(textArea1);
        String[] columnNames = {"Name", "Quantity", "Location", "Vendor", "Price", "Details"};
        itemTable = new ItemTable(columnNames);

        INTACTRadioButton.setFocusable(false);
        DAMAGEDRadioButton.setFocusable(false);
    }

    private void setupLayout() {
        // Main panel setup
        setLayout(new BorderLayout());
        panelist.setLayout(new BorderLayout());

        // Root panel setup (2 rows, 2 columns layout)
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // MISCELLANEOUS title panel (top-left, spans 2 columns)
        miscellaneous_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        miscellaneous_panel.add(miscellaneous_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(miscellaneous_panel, gbc);

        // Form panel (left side - 11 rows, 2 columns)
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

        // Row 5: purchaseDate Date
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

        // Row 6: Item Type
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(itemtype_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        itemtype_field.setPreferredSize(new Dimension(80, 25));
        panel.add(itemtype_field, formGbc);

        row++;

        // Row 7: Usage
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(usage_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        usage_field.setPreferredSize(new Dimension(80, 25));
        panel.add(usage_field, formGbc);

        row++;

        // Row 8: Condition (with radio buttons)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(condition_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        // Set GridLayout for condition radio buttons (1 row, 2 columns, 20px horizontal gap)
        radiopanel1.setLayout(new GridLayout(1, 2, 20, 0));
        radiopanel1.add(INTACTRadioButton);
        radiopanel1.add(DAMAGEDRadioButton);
        panel.add(radiopanel1, formGbc);

        row++;

        // Row 9: Description/Note
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
        textAreaScroll.setPreferredSize(new Dimension(200, 60));
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
        miscellaneous_panel.setOpaque(true);
        panel.setOpaque(true);
        table_panel.setOpaque(true);
        description_panel.setOpaque(true);
        panelButton.setOpaque(true);
        radiopanel1.setOpaque(true);

        // Set panel backgrounds
        panelist.setBackground(bg);
        rootPanel.setBackground(bg);
        miscellaneous_panel.setBackground(header);
        panel.setBackground(bg);
        panelButton.setBackground(bg);
        table_panel.setBackground(bg);
        description_panel.setBackground(bg);
        radiopanel1.setBackground(bg);

        // Set text field backgrounds
        name_field.setBackground(bg);
        vendor_field.setBackground(bg);
        price_field.setBackground(bg);
        purchaseDate_field.setBackground(bg);
        itemtype_field.setBackground(bg);
        usage_field.setBackground(bg);
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

        // Set radio button backgrounds
        INTACTRadioButton.setBackground(bg);
        DAMAGEDRadioButton.setBackground(bg);

        // Make radio buttons opaque
        INTACTRadioButton.setOpaque(true);
        DAMAGEDRadioButton.setOpaque(true);

        // Set foreground colors
        name_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        purchaseDate_field.setForeground(placeholderColor);
        itemtype_field.setForeground(black);
        usage_field.setForeground(black);
        textArea1.setForeground(black);
        location_combobox.setForeground(black);

        // Set radio button text colors
        INTACTRadioButton.setForeground(black);
        DAMAGEDRadioButton.setForeground(black);

        // Set label colors
        miscellaneous_label.setForeground(Color.WHITE);
        name_label.setForeground(black);
        quantity_label.setForeground(black);
        location_label.setForeground(black);
        vendor_label.setForeground(black);
        price_label.setForeground(black);
        purchaseDate_label.setForeground(black);
        itemtype_label.setForeground(black);
        usage_label.setForeground(black);
        condition_label.setForeground(black);
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
        Font radioFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13);

        miscellaneous_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        purchaseDate_label.setFont(labelFont);
        itemtype_label.setFont(labelFont);
        usage_label.setFont(labelFont);
        condition_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        purchaseDate_field.setFont(placeholderFont);
        itemtype_field.setFont(fieldFont);
        usage_field.setFont(fieldFont);
        textArea1.setFont(fieldFont);
        location_combobox.setFont(fieldFont);

        // Set spinner font
        spinner1.setFont(fieldFont);

        // Set radio button fonts
        INTACTRadioButton.setFont(radioFont);
        DAMAGEDRadioButton.setFont(radioFont);

        // Set button fonts
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
        REFRESHButton.setFont(buttonFont);
    }

    private void setupPlaceholders() {
        // Set placeholder text for purchaseDate field
        purchaseDate_field.setText(purchaseDate_PLACEHOLDER);

        // Add focus listener to handle placeholder behavior
        purchaseDate_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (purchaseDate_field.getText().equals(purchaseDate_PLACEHOLDER)) {
                    purchaseDate_field.setText("");
                    purchaseDate_field.setForeground(Color.BLACK);
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (purchaseDate_field.getText().isEmpty()) {
                    purchaseDate_field.setText(purchaseDate_PLACEHOLDER);
                    purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });
    }


    // GETTERS
    public boolean isIntact() {
        return INTACTRadioButton.isSelected();
    }
    public boolean isDamaged() {
        return DAMAGEDRadioButton.isSelected();
    }
    public String getNameInput() {
        return name_field.getText();
    }
    public int getQuantityInput() {
        return (int) spinner1.getValue();
    }
    public String getQuantityText() {
        return String.valueOf(spinner1.getValue());
    }

    public String getVendorInput() {
        return vendor_field.getText();
    }
    public String getPriceInput() {
        return price_field.getText();
    }
    public String getPurchaseDateInput() {
        String text = purchaseDate_field.getText();
        if (text.equals(purchaseDate_PLACEHOLDER)) {
            return "";
        }
        return text;
    }
    public String getItemTypeInput() {
        return itemtype_field.getText();
    }
    public String getUsageInput() {
        return usage_field.getText();
    }
    public String getDescriptionInput() {
        return textArea1.getText();
    }

    public String getLocationInput() {
        return (String) location_combobox.getSelectedItem();
    }
    public boolean getIsConditionInput() {
        if (DAMAGEDRadioButton.isSelected()) {
            return true;
        }
        return false;
    }

    // SETTER
    public void setNameInput(String name) {
        name_field.setText(name);
    }
    public void setQuantityInput(int quantity) {
        spinner1.setValue(quantity);
    }

    public void setQuantityText(String quantity) {
        try {
            int qty = Integer.parseInt(quantity);
            spinner1.setValue(qty);
        } catch (NumberFormatException e) {
            spinner1.setValue(1);
        }
    }
    public void setVendorInput(String vendor) {
        vendor_field.setText(vendor);
    }
    public void setPriceInput(String price) {
        price_field.setText(price);
    }
    public void setpurchaseDateInput(String purchaseDate) {
        if (purchaseDate == null || purchaseDate.trim().isEmpty()) {
            purchaseDate_field.setText(purchaseDate_PLACEHOLDER);
            purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
            purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            purchaseDate_field.setText(purchaseDate);
            purchaseDate_field.setForeground(Color.BLACK);
            purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }
    public void setItemTypeInput(String itemType) {
        itemtype_field.setText(itemType);
    }
    public void setUsageInput(String usage) {
        usage_field.setText(usage);
    }
    public void setDescriptionInput(String description) {
        textArea1.setText(description);
    }
    public void setLocationInput(String location) {
        location_combobox.setSelectedItem(location);
    }

    public void setCondition(boolean condition) {
        if (condition) {
            INTACTRadioButton.setSelected(true);
        } else {
            DAMAGEDRadioButton.setSelected(true);
        }
    }
    public void setCondition(String condition) {
        if ("DAMAGED".equalsIgnoreCase(condition)) {
            DAMAGEDRadioButton.setSelected(true);
        } else {
            INTACTRadioButton.setSelected(true);
        }
    }

    // DATA LOADING AND ACTION LISENERS

    private void loadItems() {
        itemTable.clearTable();

        // Force reload from file to get latest data
        inventoryManager.loadFromFile();

        inventoryManager.getItemsByCategory(Category.MISCELLANEOUS).stream()
                .filter(Miscellaneous.class::isInstance)
                .map(Miscellaneous.class::cast)
                .forEach(miscellaneous -> {
                    itemTable.addRow(new Object[]{
                            miscellaneous.getName(),
                            miscellaneous.getQuantity(),
                            miscellaneous.getLocation(),
                            miscellaneous.getVendor(),
                            miscellaneous.getPurchasePrice(),
                            miscellaneous.descriptionDetails()
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

    private Miscellaneous createMiscellaneousFromForm() {
        return new Miscellaneous(
                getNameInput(),
                getDescriptionInput(),
                getQuantityInput(),
                parsePrice(getPriceInput()),
                getPurchaseDateInput(),
                getVendorInput(),
                getLocationInput(),
                getItemTypeInput(),
                getUsageInput(),
                getIsConditionInput()
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

        if (getIsConditionInput()) {
            showError("Condition cannot be empty!");
            return false;
        }

        if (getItemTypeInput().trim().isEmpty()) {
            showError("Item Type cannot be empty!");
            return false;
        }

        if (getUsageInput().trim().isEmpty()) {
            showError("Usage cannot be empty!");
            return false;
        }
        
        if (!validateDateField(getPurchaseDateInput(), "Purchase Date")) return false;

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
                Miscellaneous miscellaneous = createMiscellaneousFromForm();
                inventoryManager.addItem(miscellaneous);
                loadItems();
                clearForm();
                showSuccess("Miscellaneous item added successfully!");
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
                    Miscellaneous miscellaneous = createMiscellaneousFromForm();
                    java.util.List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> miscellaneousItems = inventoryManager.getItemsByCategory(Category.MISCELLANEOUS);

                    if (selectedIndex < miscellaneousItems.size()) {
                        Item originalItem = miscellaneousItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, miscellaneous);
                            loadItems();
                            clearForm();
                            selectedIndex = -1;
                            showSuccess("Miscellaneous item updated successfully!");
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
                        "Are you sure you want to remove this miscellaneous item?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    List<Item> miscellaneousItems = inventoryManager.getItemsByCategory(Category.MISCELLANEOUS);

                    if (selectedIndex < miscellaneousItems.size()) {
                        Item itemToRemove = miscellaneousItems.get(selectedIndex);
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
        usage_field.setText("");
        itemtype_field.setText("");
        textArea1.setText("");

        // Reset spinner
        spinner1.setValue(1);

        // Reset combo box
        location_combobox.setSelectedIndex(0);

        // Reset date fields to placeholders
        setFieldToPlaceholder(purchaseDate_field, purchaseDate_PLACEHOLDER);

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
            List<Item> miscellaneousItems = inventoryManager.getItemsByCategory(Category.MISCELLANEOUS);

            if (selectedRow < miscellaneousItems.size()) {
                Miscellaneous miscellaneous = (Miscellaneous) miscellaneousItems.get(selectedRow);
                populateForm(miscellaneous);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Miscellaneous miscellaneous) {
        setNameInput(miscellaneous.getName());
        setQuantityInput(miscellaneous.getQuantity());
        setVendorInput(miscellaneous.getVendor());
        setPriceInput(String.valueOf(miscellaneous.getPurchasePrice()));
        setpurchaseDateInput(miscellaneous.getPurchaseDate());
        setCondition(miscellaneous.getIsCondition());
        setUsageInput(miscellaneous.getUsage());
        setItemTypeInput(miscellaneous.getItemType());
        setDescriptionInput(miscellaneous.getDescription());
        setLocationInput(miscellaneous.getLocation());
    }
}
