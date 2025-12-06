package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Food extends JPanel{

    // ATTRIBUTES
    private JPanel panelist,  rootPanel, food_panel, panel, table_panel, description_panel;
    private JTextField name_field, vendor_field, price_field, warranty_field, expiredate_field;
    private JTextArea textArea1;
    private JLabel food_label, name_label, quantity_label,
            location_label, vendor_label, price_label, warranty_label, expireydate_label,
            perish_label, cannedgoods_label, description_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton;
    private JComboBox<String> location_combobox;
    private JTable table1;
    private JPanel panelButton;
    private JRadioButton YESRadioButton;
    private JRadioButton NORadioButton;
    private JRadioButton YESRadioButton1;
    private JRadioButton NORadioButton1;
    private JPanel radiopanel1;
    private JPanel radiopanel2;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private JScrollPane scrollPane;

    // Button groups for radio buttons
    private ButtonGroup perishGroup;
    private ButtonGroup cannedGoodsGroup;

    // Placeholder texts
    private static final String PURCHASE_DATE_PLACEHOLDER = "MM/DD/YYYY";
    private static final String EXPIRY_DATE_PLACEHOLDER = "MM/DD/YYYY";

    public Food() {
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
        createTable();
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
        warranty_field = new JTextField(8);
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
        warranty_label = new JLabel("PURCHASED DATE:");
        expireydate_label = new JLabel("EXPIRY DATE:");
        perish_label = new JLabel("PERISH:");
        cannedgoods_label = new JLabel("CANNED GOOD:");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "LIVING ROOM", "KITCHEN", "BEDROOM",
                "BATHROOM", "GARAGE", "BASEMENT"
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

        table1 = new JTable();
        scrollPane = new JScrollPane(table1);
        textAreaScroll = new JScrollPane(textArea1);

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
        panel.add(warranty_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        warranty_field.setPreferredSize(new Dimension(80, 25));
        panel.add(warranty_field, formGbc);

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
        Color placeholderColor = new Color(100, 100, 100, 180); // Semi-transparent gray for placeholders

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
        warranty_field.setBackground(bg);
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
        warranty_field.setForeground(placeholderColor); // Start with placeholder color
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
        warranty_label.setForeground(black);
        expireydate_label.setForeground(black);
        perish_label.setForeground(black);
        cannedgoods_label.setForeground(black);
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
        Font radioFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13); // Slightly faded font for placeholders

        food_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        warranty_label.setFont(labelFont);
        expireydate_label.setFont(labelFont);
        perish_label.setFont(labelFont);
        cannedgoods_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        warranty_field.setFont(placeholderFont); // Use placeholder font initially
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
    }

    private void setupPlaceholders() {
        // Set placeholder text for date fields
        warranty_field.setText(PURCHASE_DATE_PLACEHOLDER);
        expiredate_field.setText(EXPIRY_DATE_PLACEHOLDER);

        // Add focus listeners to handle placeholder behavior
        warranty_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (warranty_field.getText().equals(PURCHASE_DATE_PLACEHOLDER)) {
                    warranty_field.setText("");
                    warranty_field.setForeground(Color.BLACK);
                    warranty_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (warranty_field.getText().isEmpty()) {
                    warranty_field.setText(PURCHASE_DATE_PLACEHOLDER);
                    warranty_field.setForeground(new Color(100, 100, 100, 180));
                    warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
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

    // METHODS AND ACTION LISTENERS

    public void clearForm() {
        name_field.setText("");
        spinner1.setValue(1); // Reset spinner to default value
        vendor_field.setText("");
        price_field.setText("");

        // Reset date fields to placeholders
        warranty_field.setText(PURCHASE_DATE_PLACEHOLDER);
        warranty_field.setForeground(new Color(100, 100, 100, 180));
        warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        expiredate_field.setText(EXPIRY_DATE_PLACEHOLDER);
        expiredate_field.setForeground(new Color(100, 100, 100, 180));
        expiredate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        textArea1.setText("");
        location_combobox.setSelectedIndex(0);

        // Reset radio buttons to default (NO)
        NORadioButton.setSelected(true);
        NORadioButton1.setSelected(true);
    }

    // Radio button getter methods
    public boolean isPerish() {
        return YESRadioButton.isSelected();
    }

    public boolean isCannedGood() {
        return YESRadioButton1.isSelected();
    }

    // Radio button setter methods (optional)
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

    public String getVendorInput() {
        return vendor_field.getText();
    }

    public String getPriceInput() {
        return price_field.getText();
    }

    public String getWarrantyInput() {
        String text = warranty_field.getText();
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

    // Setter methods for form fields
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

    public void setWarrantyInput(String warranty) {
        if (warranty == null || warranty.trim().isEmpty()) {
            warranty_field.setText(PURCHASE_DATE_PLACEHOLDER);
            warranty_field.setForeground(new Color(100, 100, 100, 180));
            warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            warranty_field.setText(warranty);
            warranty_field.setForeground(Color.BLACK);
            warranty_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

        table1.setRowHeight(25);
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table1.getTableHeader().setBackground(new Color(70, 130, 180));
        table1.getTableHeader().setForeground(Color.WHITE);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table1.setSelectionBackground(new Color(100, 149, 237));
        table1.setSelectionForeground(Color.WHITE);
        table1.setGridColor(Color.LIGHT_GRAY);
        table1.setShowGrid(true);
    }
}