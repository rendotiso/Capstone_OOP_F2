package UI.Panel;

import Model.Data.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Misc extends JPanel {

    // ATTRIBUTES
    private JPanel panelist, rootPanel, miscellaneous_panel, panel, table_panel, description_panel;
    private JTextField name_field, vendor_field, price_field, warranty_field, itemtype_field, usage_field;
    private JTextArea textArea1;
    private JLabel miscellaneous_label, name_label, quantity_label, location_label, vendor_label,
            price_label, warranty_label, itemtype_label, usage_label, condition_label, description_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton, REFRESHButton;
    private JComboBox<String> location_combobox;
    private JTable table1;
    private JPanel panelButton;
    private JRadioButton INTACTRadioButton;
    private JRadioButton DAMAGEDRadioButton;
    private JPanel radiopanel1;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private JScrollPane scrollPane;
    private final InventoryManager inventoryManager;

    // Button groups for radio buttons
    private ButtonGroup conditionGroup;

    // Placeholder texts
    private static final String WARRANTY_PLACEHOLDER = "MM/DD/YYYY";

    public Misc() {
        inventoryManager = InventoryManager.getInstance();
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
        setupButtonListeners();
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
        warranty_field = new JTextField(8);
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
        warranty_label = new JLabel("PURCHASED DATE:");
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

        table1 = new JTable();
        scrollPane = new JScrollPane(table1);
        textAreaScroll = new JScrollPane(textArea1);

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
        warranty_field.setBackground(bg);
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
        warranty_field.setForeground(placeholderColor);
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
        warranty_label.setForeground(black);
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
        warranty_label.setFont(labelFont);
        itemtype_label.setFont(labelFont);
        usage_label.setFont(labelFont);
        condition_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        warranty_field.setFont(placeholderFont);
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
        // Set placeholder text for warranty field
        warranty_field.setText(WARRANTY_PLACEHOLDER);

        // Add focus listener to handle placeholder behavior
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
    public String getWarrantyInput() {
        String text = warranty_field.getText();
        if (text.equals(WARRANTY_PLACEHOLDER)) {
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
    public String getCondition() {
        if (DAMAGEDRadioButton.isSelected()) {
            return "DAMAGED";
        }
        return "INTACT";
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

    public void setCondition(boolean isIntact) {
        if (isIntact) {
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
    private void setupButtonListeners() {
//        ADDButton.addActionListener(e -> addItem());
//        UPDATEButton.addActionListener(e -> updateItem());
//        REMOVEButton.addActionListener(e -> removeItem());
//        CLEARButton.addActionListener(e -> clearForm());
//        REFRESHButton.addActionListener(e -> refreshForm());
    }


    public void clearForm() {
        name_field.setText("");
        spinner1.setValue(1);
        vendor_field.setText("");
        price_field.setText("");

        // Reset warranty field to placeholder
        warranty_field.setText(WARRANTY_PLACEHOLDER);
        warranty_field.setForeground(new Color(100, 100, 100, 180));
        warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        itemtype_field.setText("");
        usage_field.setText("");
        textArea1.setText("");
        location_combobox.setSelectedIndex(0);

        INTACTRadioButton.setSelected(true);
    }
}