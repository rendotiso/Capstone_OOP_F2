package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.*;
import Model.Entities.Food;
import UI.Utilities.ItemTable;
import Model.Entities.Food;
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

public class FoodPanel extends JPanel{

    // ATTRIBUTES
    private JPanel panelist,  rootPanel, food_panel, panel, table_panel, description_panel;
    private JTextField name_field, vendor_field, price_field, purchaseDate_field, expiredate_field;
    private JTextArea textArea1;
    private JLabel food_label, name_label, quantity_label,
            location_label, vendor_label, price_label, purchaseDate_label, expireydate_label,
            perish_label, cannedgoods_label, description_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton, REFRESHButton;
    private JComboBox<String> location_combobox;
    private ItemTable itemTable;
    private JPanel panelButton;
    private JRadioButton YESRadioButton;
    private JRadioButton NORadioButton;
    private JRadioButton YESRadioButton1;
    private JRadioButton NORadioButton1;
    private JPanel radiopanel1;
    private JPanel radiopanel2;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private final InventoryManager inventoryManager;
    private int selectedIndex = -1;

    // Button groups for radio buttons
    private ButtonGroup perishGroup;
    private ButtonGroup cannedGoodsGroup;

    // Placeholder texts
    private static final String PURCHASE_DATE_PLACEHOLDER = "MM/DD/YYYY";
    private static final String EXPIRY_DATE_PLACEHOLDER = "MM/DD/YYYY";

    public FoodPanel() {
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
        food_panel = new JPanel();
        panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();
        panelButton = new JPanel();

        // Initialize radio panels
        radiopanel1 = new JPanel();
        radiopanel2 = new JPanel();

        // Initialize form fields
        name_field = new JTextField(8);
        vendor_field = new JTextField(8);
        price_field = new JTextField(8);
        purchaseDate_field = new JTextField(8);
        expiredate_field = new JTextField(8);
        
        textArea1 = new JTextArea(3, 15);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        // Initialize spinner for quantity with left-aligned text
        spinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        // Customize the spinner editor for left alignment
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner1, "#") {
            public void setHorizontalAlignment(int alignment) {
                super.setAlignmentX(SwingConstants.LEFT);
            }
        };
        spinner1.setEditor(editor);

        // Force left alignment of the text field inside the spinner
        JComponent editorComp = spinner1.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        // Initialize labels
        food_label = new JLabel("FOOD");
        name_label = new JLabel("NAME:");
        quantity_label = new JLabel("QUANTITY:");
        location_label = new JLabel("LOCATION:");
        vendor_label = new JLabel("VENDOR:");
        price_label = new JLabel("PRICE:");
        purchaseDate_label = new JLabel("PURCHASED DATE:");
        expireydate_label = new JLabel("EXPIRY DATE:");
        perish_label = new JLabel("PERISHABLE?");
        cannedgoods_label = new JLabel("CANNED GOOD?");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");
        REFRESHButton = new JButton("REFRESH");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "REFRIGERATOR", "PANTRY", "FREEZER",
                "FOOD STORAGE RACK", "KITCHEN SHELVES", "COUNTERTOP CONTAINERS"
        });

        // Initialize radio buttons
        YESRadioButton = new JRadioButton("YES");
        NORadioButton = new JRadioButton("NO");
        YESRadioButton1 = new JRadioButton("YES");
        NORadioButton1 = new JRadioButton("NO");

        // Create button groups
        perishGroup = new ButtonGroup();
        perishGroup.add(YESRadioButton);
        perishGroup.add(NORadioButton);

        cannedGoodsGroup = new ButtonGroup();
        cannedGoodsGroup.add(YESRadioButton1);
        cannedGoodsGroup.add(NORadioButton1);

        // Set default selections
        NORadioButton.setSelected(true);  // Default: NO for perish
        NORadioButton1.setSelected(true); // Default: NO for canned goods

        textAreaScroll = new JScrollPane(textArea1);
        String[] columnNames = {"Name", "Quantity", "Location", "Vendor", "Price", "Details"};
        itemTable = new ItemTable(columnNames);

        YESRadioButton1.setFocusable(false);
        YESRadioButton.setFocusable(false);
        NORadioButton.setFocusable(false);
        NORadioButton1.setFocusable(false);
    }

    private void setupLayout() {
        // Main panel setup
        setLayout(new BorderLayout());
        panelist.setLayout(new BorderLayout());

        // Root panel setup (2 rows, 2 columns layout)
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // FOOD title panel (top-left, spans 2 columns)
        food_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        food_panel.add(food_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(food_panel, gbc);

        // Form panel (left side - 10 rows, 2 columns)
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

        // Row 5: Purchased Date
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

        // Row 6: Expiry Date
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(expireydate_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        expiredate_field.setPreferredSize(new Dimension(80, 25));
        panel.add(expiredate_field, formGbc);

        row++;

        // Row 7: Perish (with radio buttons)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(perish_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        // Set GridLayout for perish radio buttons (1 row, 2 columns, 20px horizontal gap)
        radiopanel1.setLayout(new GridLayout(1, 2, 20, 0));
        radiopanel1.add(YESRadioButton);
        radiopanel1.add(NORadioButton);
        panel.add(radiopanel1, formGbc);

        row++;

        // Row 8: Canned Good (with radio buttons)
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(cannedgoods_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        // Set GridLayout for canned goods radio buttons (1 row, 2 columns, 20px horizontal gap)
        radiopanel2.setLayout(new GridLayout(1, 2, 20, 0));
        radiopanel2.add(YESRadioButton1);
        radiopanel2.add(NORadioButton1);
        panel.add(radiopanel2, formGbc);

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
        food_panel.setOpaque(true);
        panel.setOpaque(true);
        table_panel.setOpaque(true);
        description_panel.setOpaque(true);
        panelButton.setOpaque(true);
        radiopanel1.setOpaque(true);
        radiopanel2.setOpaque(true);

        // Set panel backgrounds
        panelist.setBackground(bg);
        rootPanel.setBackground(bg);
        food_panel.setBackground(header);
        panel.setBackground(bg);
        panelButton.setBackground(bg);
        table_panel.setBackground(bg);
        description_panel.setBackground(bg);
        radiopanel1.setBackground(bg);
        radiopanel2.setBackground(bg);

        // Set text field backgrounds
        name_field.setBackground(bg);
        vendor_field.setBackground(bg);
        price_field.setBackground(bg);
        purchaseDate_field.setBackground(bg);
        expiredate_field.setBackground(bg);
        textArea1.setBackground(bg);
        location_combobox.setBackground(bg);

        // Set spinner background and left alignment
        spinner1.setBackground(bg);

        // Customize spinner arrow buttons and force left alignment
        Component spinnerEditor = spinner1.getEditor();
        if (spinnerEditor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) spinnerEditor).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT); // Force left alignment
        }

        // Set radio button backgrounds
        YESRadioButton.setBackground(bg);
        NORadioButton.setBackground(bg);
        YESRadioButton1.setBackground(bg);
        NORadioButton1.setBackground(bg);

        // Make radio buttons opaque
        YESRadioButton.setOpaque(true);
        NORadioButton.setOpaque(true);
        YESRadioButton1.setOpaque(true);
        NORadioButton1.setOpaque(true);

        // Set foreground colors
        name_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        purchaseDate_field.setForeground(placeholderColor); // Start with placeholder color
        expiredate_field.setForeground(placeholderColor); // Start with placeholder color
        textArea1.setForeground(black);
        location_combobox.setForeground(black);

        // Set radio button text colors
        YESRadioButton.setForeground(black);
        NORadioButton.setForeground(black);
        YESRadioButton1.setForeground(black);
        NORadioButton1.setForeground(black);

        // Set label colors
        food_label.setForeground(Color.WHITE);
        name_label.setForeground(black);
        quantity_label.setForeground(black);
        location_label.setForeground(black);
        vendor_label.setForeground(black);
        price_label.setForeground(black);
        purchaseDate_label.setForeground(black);
        expireydate_label.setForeground(black);
        perish_label.setForeground(black);
        cannedgoods_label.setForeground(black);
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
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13); // Slightly faded font for placeholders

        food_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        purchaseDate_label.setFont(labelFont);
        expireydate_label.setFont(labelFont);
        perish_label.setFont(labelFont);
        cannedgoods_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        purchaseDate_field.setFont(placeholderFont); // Use placeholder font initially
        expiredate_field.setFont(placeholderFont); // Use placeholder font initially
        textArea1.setFont(fieldFont);
        location_combobox.setFont(fieldFont);

        // Set spinner font (left aligned)
        spinner1.setFont(fieldFont);

        // Set radio button fonts
        YESRadioButton.setFont(radioFont);
        NORadioButton.setFont(radioFont);
        YESRadioButton1.setFont(radioFont);
        NORadioButton1.setFont(radioFont);

        // Set button fonts
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
        REFRESHButton.setFont(buttonFont);
    }

    private void setupPlaceholders() {
        // Set placeholder text for date fields
        purchaseDate_field.setText(PURCHASE_DATE_PLACEHOLDER);
        expiredate_field.setText(EXPIRY_DATE_PLACEHOLDER);

        // Add focus listeners to handle placeholder behavior
        purchaseDate_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (purchaseDate_field.getText().equals(PURCHASE_DATE_PLACEHOLDER)) {
                    purchaseDate_field.setText("");
                    purchaseDate_field.setForeground(Color.BLACK);
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (purchaseDate_field.getText().isEmpty()) {
                    purchaseDate_field.setText(PURCHASE_DATE_PLACEHOLDER);
                    purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });

        expiredate_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (expiredate_field.getText().equals(EXPIRY_DATE_PLACEHOLDER)) {
                    expiredate_field.setText("");
                    expiredate_field.setForeground(Color.BLACK);
                    expiredate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (expiredate_field.getText().isEmpty()) {
                    expiredate_field.setText(EXPIRY_DATE_PLACEHOLDER);
                    expiredate_field.setForeground(new Color(100, 100, 100, 180));
                    expiredate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });
    }
    
    // GETTER
    public boolean isPerish() {
        return YESRadioButton.isSelected();
    }
    public boolean isCannedGood() {
        return YESRadioButton1.isSelected();
    }
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
        // Return empty string if it's the placeholder
        if (text.equals(PURCHASE_DATE_PLACEHOLDER)) {
            return "";
        }
        return text;
    }
    public String getExpiryDateInput() {
        String text = expiredate_field.getText();
        // Return empty string if it's the placeholder
        if (text.equals(EXPIRY_DATE_PLACEHOLDER)) {
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

    // SETTER
    public void setNameInput(String name) {
        name_field.setText(name);
    }
    public void setQuantityInput(int quantity) {
        spinner1.setValue(quantity);
    }
    public void setVendorInput(String vendor) {
        vendor_field.setText(vendor);
    }
    public void setPriceInput(String price) {
        price_field.setText(price);
    }
    public void setpurchaseDateInput(String purchaseDate) {
        if (purchaseDate == null || purchaseDate.trim().isEmpty()) {
            purchaseDate_field.setText(PURCHASE_DATE_PLACEHOLDER);
            purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
            purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            purchaseDate_field.setText(purchaseDate);
            purchaseDate_field.setForeground(Color.BLACK);
            purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }
    public void setExpiryDateInput(String expiryDate) {
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            expiredate_field.setText(EXPIRY_DATE_PLACEHOLDER);
            expiredate_field.setForeground(new Color(100, 100, 100, 180));
            expiredate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            expiredate_field.setText(expiryDate);
            expiredate_field.setForeground(Color.BLACK);
            expiredate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }
    public void setDescriptionInput(String description) {
        textArea1.setText(description);
    }
    public void setLocationInput(String location) {
        location_combobox.setSelectedItem(location);
    }
    public void setPerish(boolean isPerish) {
        if (isPerish) {
            YESRadioButton.setSelected(true);
        } else {
            NORadioButton.setSelected(true);
        }
    }

    public void setCannedGood(boolean isCannedGood) {
        if (isCannedGood) {
            YESRadioButton1.setSelected(true);
        } else {
            NORadioButton1.setSelected(true);
        }
    }

    // DATA LOADING AND ACTION LISTENER

    private void loadItems() {
        itemTable.clearTable();

        // Force reload from file to get latest data
        inventoryManager.loadFromFile();

        inventoryManager.getItemsByCategory(Category.FOOD).stream()
                .filter(Food.class::isInstance)
                .map(Food.class::cast)
                .forEach(food -> {
                    itemTable.addRow(new Object[]{
                            food.getName(),
                            food.getQuantity(),
                            food.getLocation(),
                            food.getVendor(),
                            food.getPurchasePrice(),
                            food.descriptionDetails()
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

    private Food createFoodFromForm() {
        return new Food(
                getNameInput(),
                getDescriptionInput(),
                getQuantityInput(),
                parsePrice(getPriceInput()),
                getPurchaseDateInput(),
                getVendorInput(),
                getLocationInput(),
                getExpiryDateInput(),
                isCannedGood(),
                isPerish()
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

        if (getExpiryDateInput().trim().isEmpty()) {
            showError("Expiry Date cannot be empty!");
            return false;
        }

        if (isCannedGood()) {
            showError("Is Canned cannot be empty!");
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
    private void addItem() {
        try {
            if (validateForm()) {
                Food food = createFoodFromForm();
                inventoryManager.addItem(food);
                loadItems();
                clearForm();
                showSuccess("food item added successfully!");
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
                    Food food = createFoodFromForm();
                    java.util.List<Item> allItems = inventoryManager.getAllItems();
                    java.util.List<Item> foodItems = inventoryManager.getItemsByCategory(Category.FOOD);

                    if (selectedIndex < foodItems.size()) {
                        Item originalItem = foodItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, food);
                            loadItems();
                            clearForm();
                            selectedIndex = -1;
                            showSuccess("Food item updated successfully!");
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
                        "Are you sure you want to remove this food item?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    List<Item> foodItems = inventoryManager.getItemsByCategory(Category.FOOD);

                    if (selectedIndex < foodItems.size()) {
                        Item itemToRemove = foodItems.get(selectedIndex);
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
    
    private void setupButtonListeners() {
        ADDButton.addActionListener(e -> addItem());
        UPDATEButton.addActionListener(e -> updateItem());
        REMOVEButton.addActionListener(e -> removeItem());
        CLEARButton.addActionListener(e -> clearForm());
        REFRESHButton.addActionListener(e -> refreshForm());
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
        expiredate_field.setText("");
        textArea1.setText("");

        // Reset spinner
        spinner1.setValue(1);

        // Reset combo box
        location_combobox.setSelectedIndex(0);


        // Reset date fields to placeholders
        setFieldToPlaceholder(purchaseDate_field, PURCHASE_DATE_PLACEHOLDER);
        setFieldToPlaceholder(expiredate_field, EXPIRY_DATE_PLACEHOLDER);

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
            List<Item> foodsItems = inventoryManager.getItemsByCategory(Category.FOOD);

            if (selectedRow < foodsItems.size()) {
                Food food = (Food) foodsItems.get(selectedRow);
                populateForm(food);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Food food) {
        setNameInput(food.getName());
        setQuantityInput(food.getQuantity());
        setVendorInput(food.getVendor());
        setPriceInput(String.valueOf(food.getPurchasePrice()));
        setpurchaseDateInput(food.getPurchaseDate());
        setExpiryDateInput(food.getExpiryDate());
        setPerish(food.getIsPerishable());
        setCannedGood(food.getIsCanned());
        setDescriptionInput(food.getDescription());
        setLocationInput(food.getLocation());
    }

   


}
