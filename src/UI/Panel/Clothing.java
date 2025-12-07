package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Clothing;
import Model.Entities.Item;
import Model.Enums.Category;
import UI.Utilities.ItemTable;

import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClothingPanel extends JPanel {

    // ATTRIBUTES
    private JPanel panelist, rootPanel, Clothes_panel, panel, table_panel, description_panel;
    private JTextField name_field, vendor_field, price_field, condition_field, fabrictype_field, purchaseDate_field;
    private JTextArea textArea1;
    private JLabel Clothes_label, name_label, quantity_label, location_label, vendor_label, price_label,
            condition_label, fabrictype_label, size_label, description_label, purchaseDate_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton, REFRESHButton;
    private JComboBox<String> location_combobox, size_combobox;
    private ItemTable itemTable;
    private JPanel panelButton;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private final InventoryManager inventoryManager;
    private int selectedIndex = -1;

    // Placeholder texts
    private static final String DATE_PLACEHOLDER  = "MM/DD/YYYY";

    public ClothingPanel() {
        inventoryManager = InventoryManager.getInstance();
        initComponents();
        setupLayout();
        setupAppearance();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
        setupPlaceholders();
    }

    private void initComponents() {
        // Initialize panels
        panelist = new JPanel();
        rootPanel = new JPanel();
        Clothes_panel = new JPanel();
        panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();
        panelButton = new JPanel();

        // Initialize form fields
        name_field = new JTextField(8);
        vendor_field = new JTextField(8);
        price_field = new JTextField(8);
        purchaseDate_field = new JTextField(8);
        condition_field = new JTextField(8);
        fabrictype_field = new JTextField(8);

        textArea1 = new JTextArea(3, 15);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textAreaScroll = new JScrollPane(textArea1);

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
        Clothes_label = new JLabel("CLOTHING");
        name_label = new JLabel("NAME:");
        quantity_label = new JLabel("QUANTITY:");
        location_label = new JLabel("LOCATION:");
        vendor_label = new JLabel("VENDOR:");
        price_label = new JLabel("PRICE:");
        purchaseDate_label = new JLabel("PURCHASE DATE:");
        condition_label = new JLabel("CONDITION:");
        fabrictype_label = new JLabel("FABRIC TYPE:");
        size_label = new JLabel("SIZE:");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");
        REFRESHButton = new JButton("REFRESH");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "STORAGE BOX", "WARDROBE", "UNDERBED STORAGE", "CLOSET",
                "DRAWER CLOSET", "HANGING RACK"
        });

        size_combobox = new JComboBox<>(new String[]{
                "XS", "S", "M", "L", "XL", "XXL"
        });

        textAreaScroll = new JScrollPane(textArea1);
        String[] columnNames = {"Name", "Quantity", "Location", "Vendor", "Price", "Details"};
        itemTable = new ItemTable(columnNames);
    }

    private void setupLayout() {
        // Main panel setup - similar to Food class
        setLayout(new BorderLayout());
        panelist.setLayout(new BorderLayout());

        // Root panel setup (2 rows, 2 columns layout) - similar to Food class
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // TOOLS title panel (top-left, spans 2 columns) - similar to Food class
        Clothes_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Clothes_panel.add(Clothes_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(Clothes_panel, gbc);

        // Form panel (left side) - similar height to Food class
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Row 0: Name - similar field sizes
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

        // Row 1: Quantity (Spinner) - same as Food class
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

        // Row 2: Location - same as Food class
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

        // Row 3: Vendor - same as Food class
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

        // Row 4: Price - same as Food class
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

        // Row 5: Purchase Date - same as Food class
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

        // Row 6: Condition - same as Food class
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(condition_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        condition_field.setPreferredSize(new Dimension(80, 25));
        panel.add(condition_field, formGbc);

        row++;

        // Row 7: Fabric Type
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(fabrictype_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        fabrictype_field.setPreferredSize(new Dimension(80, 25));
        panel.add(fabrictype_field, formGbc);

        row++;

        // Row 8: Size - MOVED HERE (below material)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(size_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        size_combobox.setPreferredSize(new Dimension(80, 25));
        panel.add(size_combobox, formGbc);

        row++;

        // Row 9: Description/Note - similar layout to Food class
        description_panel.setLayout(new GridBagLayout());
        GridBagConstraints descGbc = new GridBagConstraints();
        descGbc.insets = new Insets(5, 5, 5, 5);

        // Description label
        descGbc.gridx = 0; descGbc.gridy = 0;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.HORIZONTAL;
        description_panel.add(description_label, descGbc);

        // Text area - similar size to Food class
        descGbc.gridx = 0; descGbc.gridy = 1;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.BOTH;
        descGbc.weighty = 1.0;
        textAreaScroll.setPreferredSize(new Dimension(200, 80));
        description_panel.add(textAreaScroll, descGbc);

        // Buttons panel - same as Food class
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

        // Add description panel to main form panel - same as Food class
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.gridwidth = 2;
        formGbc.fill = GridBagConstraints.BOTH;
        formGbc.weighty = 1.0;
        panel.add(description_panel, formGbc);

        // Add form panel to root panel (left side) - same proportions as Food class
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(panel, gbc);

        // Table panel (right side) - same as Food class
        table_panel.setLayout(new BorderLayout());
        table_panel.add(itemTable, BorderLayout.CENTER);

        // Add table panel to root panel (right side) - same proportions as Food class
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(table_panel, gbc);

        // Add root panel to panelist - same as Food class
        panelist.add(rootPanel, BorderLayout.CENTER);

        // Add panelist to main panel - same as Food class
        add(panelist, BorderLayout.CENTER);
    }

    private void setupAppearance() {
        // Set background colors - same as Food class
        Color header = new Color(0x4682B4);
        Color black = new Color(-16777216);
        Color bg = new Color(0xF5F5F5);
        Color placeholderColor = new Color(100, 100, 100, 180);
        Color buttonColor = new Color(70, 130, 180);

        // Set panels opaque - same as Food class
        panelist.setOpaque(true);
        rootPanel.setOpaque(true);
        Clothes_panel.setOpaque(true);
        panel.setOpaque(true);
        table_panel.setOpaque(true);
        description_panel.setOpaque(true);
        panelButton.setOpaque(true);

        // Set panel backgrounds - same as Food class
        panelist.setBackground(bg);
        rootPanel.setBackground(bg);
        Clothes_panel.setBackground(header);
        panel.setBackground(bg);
        panelButton.setBackground(bg);
        table_panel.setBackground(bg);
        description_panel.setBackground(bg);

        // Set text field backgrounds - same as Food class
        name_field.setBackground(bg);
        vendor_field.setBackground(bg);
        price_field.setBackground(bg);
        purchaseDate_field.setBackground(bg);
        condition_field.setBackground(bg);
        fabrictype_field.setBackground(bg);
        size_combobox.setBackground(bg);
        textArea1.setBackground(bg);
        location_combobox.setBackground(bg);

        // Set spinner background - same as Food class
        spinner1.setBackground(bg);

        // Customize spinner text field - same as Food class
        JComponent editorComp = spinner1.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Set foreground colors - same as Food class
        name_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        purchaseDate_field.setForeground(black);
        condition_field.setForeground(placeholderColor);
        fabrictype_field.setForeground(black);
        size_combobox.setForeground(black);
        textArea1.setForeground(black);
        location_combobox.setForeground(black);

        // Set label colors - same as Food class
        Clothes_label.setForeground(Color.WHITE);
        name_label.setForeground(black);
        quantity_label.setForeground(black);
        location_label.setForeground(black);
        vendor_label.setForeground(black);
        price_label.setForeground(black);
        purchaseDate_label.setForeground(black);
        condition_label.setForeground(black);
        fabrictype_label.setForeground(black);
        size_label.setForeground(black);
        description_label.setForeground(black);

        // Set button colors - same as Food class
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

        // Set fonts - same as Food class
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13);

        Clothes_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        purchaseDate_label.setFont(labelFont);
        condition_label.setFont(labelFont);
        fabrictype_label.setFont(labelFont);
        size_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        purchaseDate_field.setFont(fieldFont);
        condition_field.setFont(placeholderFont);
        fabrictype_field.setFont(fieldFont);
        size_combobox.setFont(fieldFont);
        textArea1.setFont(fieldFont);
        location_combobox.setFont(fieldFont);

        // Set spinner font - same as Food class
        spinner1.setFont(fieldFont);

        // Set button fonts - same as Food class
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
        REFRESHButton.setFont(buttonFont);

    }
    private void setupPlaceholders() {
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
    }

        //GETTERS
    public String getNameInput() {
        return name_field.getText();
    }

    public int getQuantityInput() {
        return (int) spinner1.getValue();
    }

    public String getVendorInput() {
        return vendor_field.getText();
    }

    public String getPriceInput() {
        return price_field.getText();
    }

    public String getPurchaseDateInput() {
        String text = purchaseDate_field.getText();
        if (text.equals(DATE_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public String getDescriptionInput() {
        return textArea1.getText();
    }

    public String getLocationInput() {
        return (String) location_combobox.getSelectedItem();
    }

    public String getConditionInput() {
        return condition_field.getText();
    }

    public String getFabricTypeInput() {
        return fabrictype_field.getText();
    }

    public String getSizeInput() {
        return (String) size_combobox.getSelectedItem();
    }

    //SETTERS
    public void setNameInput(String name) {
        name_field.setText(name);
    }
    public void setQuantityInput(int quantity) {
        spinner1.setValue(quantity);
    }
    public void setSizeInput(String size) {
        size_combobox.setSelectedItem(size);
    }
    public void setVendorInput(String vendor) {
        vendor_field.setText(vendor);
    }
    public void setPriceInput(String price) {
        price_field.setText(price);
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
    public void setConditionInput(String condition) {
        condition_field.setText(condition);
    }
    public void setFabrictypeInput(String toolType) {
        fabrictype_field.setText(toolType);
    }
    public void setDescriptionInput(String description) {
        textArea1.setText(description);
    }
    public void setLocationInput(String location) {
        location_combobox.setSelectedItem(location);
    }

    // DATA LOADING AND ACTION LISTENERS BELOW

    private void loadItems() {
        itemTable.clearTable();

        // Force reload from file to get latest data
        inventoryManager.loadFromFile();

        inventoryManager.getItemsByCategory(Category.CLOTHING).stream()
                .filter(Model.Entities.Clothing.class::isInstance)
                .map(Model.Entities.Clothing.class::cast)
                .forEach(clothing -> {
                    itemTable.addRow(new Object[]{
                            clothing.getName(),
                            clothing.getQuantity(),
                            clothing.getLocation(),
                            clothing.getVendor(),
                            clothing.descriptionDetails()
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

    private Model.Entities.Clothing createClothingFromForm() {
        return new Model.Entities.Clothing(
                getNameInput(),
                getDescriptionInput(),
                getQuantityInput(),
                parsePrice(getPriceInput()),
                getVendorInput(),
                getLocationInput(),
                getConditionInput(),
                getFabricTypeInput(),
                getSizeInput(),
                getPurchaseDateInput()
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

        if (getConditionInput().trim().isEmpty()) {
            showError("Condition cannot be empty!");
            return false;
        }

        if (getFabricTypeInput().trim().isEmpty()) {
            showError("Fabric Type cannot be empty!");
            return false;
        }

        if (getSizeInput().trim().isEmpty()) {
            showError("Size Type cannot be empty!");
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
    public void setupButtonListeners(){
        ADDButton.addActionListener(e -> addItem());
        UPDATEButton.addActionListener(e -> updateItem());
        REMOVEButton.addActionListener(e -> removeItem());
        CLEARButton.addActionListener(e -> clearForm());
        REFRESHButton.addActionListener(e -> refreshForm());
    }

    private void addItem() {
        try {
            if (validateForm()) {
                Model.Entities.Clothing clothing = createClothingFromForm();
                inventoryManager.addItem(clothing);
                loadItems();
                clearForm();
                showSuccess("Clothing item added successfully!");
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
                    Clothing clothing = createClothingFromForm();
                    java.util.List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> clothingItems = inventoryManager.getItemsByCategory(Category.CLOTHING);

                    if (selectedIndex < clothingItems.size()) {
                        Item originalItem = clothingItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, clothing);
                            loadItems();
                            clearForm();
                            selectedIndex = -1;
                            showSuccess("Clothing item updated successfully!");
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
                        "Are you sure you want to remove this clothing item?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    List<Item> clothingItems = inventoryManager.getItemsByCategory(Category.CLOTHING);

                    if (selectedIndex < clothingItems.size()) {
                        Item itemToRemove = clothingItems.get(selectedIndex);
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
        condition_field.setText("");
        fabrictype_field.setText("");
        textArea1.setText("");

        // Reset spinner
        spinner1.setValue(1);

        // Reset date fields to placeholders
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
            List<Item> clothingItems = inventoryManager.getItemsByCategory(Category.CLOTHING);

            if (selectedRow < clothingItems.size()) {
                Model.Entities.Clothing clothing = (Clothing) clothingItems.get(selectedRow);
                populateForm(clothing);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Model.Entities.Clothing clothing) {
        setNameInput(clothing.getName());
        setQuantityInput(clothing.getQuantity());
        setVendorInput(clothing.getVendor());
        setPriceInput(String.valueOf(clothing.getPurchasePrice()));
        setPurchaseDateInput(clothing.getPurchaseDate());
        setConditionInput(clothing.getCondition());
        setFabrictypeInput(clothing.getFabricType());
        setSizeInput(String.valueOf(clothing.getSize()));
        setDescriptionInput(clothing.getDescription());
        setLocationInput(String.valueOf(clothing.getLocation()));
    }
}