package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;
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
            warranty_label, tooltype_label, material_label, requiresmaintenance_label, size_label, description_label;
    private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton;
    private JComboBox<String> location_combobox;
    private JTable table1;
    private JPanel panelButton;
    private JRadioButton YESRadioButton;
    private JRadioButton NORadioButton;
    private JPanel radiopanel1;
    private JScrollPane textAreaScroll;
    private JSpinner spinner1;
    private JScrollPane scrollPane;

    // Button groups for radio buttons
    private ButtonGroup maintenanceGroup;

    // Placeholder texts
    private static final String WARRANTY_PLACEHOLDER = "MM/DD/YYYY";

    public Tools() {
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
        tools_panel = new JPanel();
        panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();
        panelButton = new JPanel();

        // Initialize radio panel
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
        size_label = new JLabel("STEEL GRADE:");  // Will be placed below material
        requiresmaintenance_label = new JLabel("REQUIRES MAINTENANCE:");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        // Initialize buttons
        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");

        // Initialize combo boxes
        location_combobox = new JComboBox<>(new String[]{
                "GARAGE", "WORKSHOP", "BASEMENT", "SHED",
                "KITCHEN", "LIVING ROOM", "BEDROOM", "STORAGE", "TOOLBOX"
        });

        // Initialize radio buttons
        YESRadioButton = new JRadioButton("YES");
        NORadioButton = new JRadioButton("NO");

        // Create button group
        maintenanceGroup = new ButtonGroup();
        maintenanceGroup.add(YESRadioButton);
        maintenanceGroup.add(NORadioButton);

        // Set default selection
        NORadioButton.setSelected(true);

        table1 = new JTable();
        scrollPane = new JScrollPane(table1);
        textAreaScroll = new JScrollPane(textArea1);

        YESRadioButton.setFocusable(false);
        NORadioButton.setFocusable(false);
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
        tools_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tools_panel.add(tools_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(tools_panel, gbc);

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

        // Row 5: Warranty Date - same as Food class
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

        // Row 8: SIZE - MOVED HERE (below material)
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

        // Row 9: Requires Maintenance (with radio buttons) - similar to Food class radio layout
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(requiresmaintenance_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        // Set GridLayout for maintenance radio buttons (1 row, 2 columns, 20px horizontal gap) - same as Food
        radiopanel1.setLayout(new GridLayout(1, 2, 20, 0));
        radiopanel1.add(YESRadioButton);
        radiopanel1.add(NORadioButton);
        panel.add(radiopanel1, formGbc);

        row++;

        // Row 10: Description/Note - similar layout to Food class
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
        table_panel.add(scrollPane, BorderLayout.CENTER);

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

        // Set panels opaque - same as Food class
        panelist.setOpaque(true);
        rootPanel.setOpaque(true);
        tools_panel.setOpaque(true);
        panel.setOpaque(true);
        table_panel.setOpaque(true);
        description_panel.setOpaque(true);
        panelButton.setOpaque(true);
        radiopanel1.setOpaque(true);

        // Set panel backgrounds - same as Food class
        panelist.setBackground(bg);
        rootPanel.setBackground(bg);
        tools_panel.setBackground(header);
        panel.setBackground(bg);
        panelButton.setBackground(bg);
        table_panel.setBackground(bg);
        description_panel.setBackground(bg);
        radiopanel1.setBackground(bg);

        // Set text field backgrounds - same as Food class
        name_field.setBackground(bg);
        size_field.setBackground(bg);
        vendor_field.setBackground(bg);
        price_field.setBackground(bg);
        warranty_field.setBackground(bg);
        tooltype_field.setBackground(bg);
        material_field.setBackground(bg);
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

        // Set radio button backgrounds - same as Food class
        YESRadioButton.setBackground(bg);
        NORadioButton.setBackground(bg);

        // Make radio buttons opaque - same as Food class
        YESRadioButton.setOpaque(true);
        NORadioButton.setOpaque(true);

        // Set foreground colors - same as Food class
        name_field.setForeground(black);
        size_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        warranty_field.setForeground(placeholderColor);
        tooltype_field.setForeground(black);
        material_field.setForeground(black);
        textArea1.setForeground(black);
        location_combobox.setForeground(black);

        // Set radio button text colors - same as Food class
        YESRadioButton.setForeground(black);
        NORadioButton.setForeground(black);

        // Set label colors - same as Food class
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
        description_label.setForeground(black);

        // Set button colors - same as Food class
        ADDButton.setBackground(new Color(70, 130, 180));
        CLEARButton.setBackground(new Color(70, 130, 180));
        UPDATEButton.setBackground(new Color(70, 130, 180));
        REMOVEButton.setBackground(new Color(70, 130, 180));
        ADDButton.setForeground(Color.white);
        CLEARButton.setForeground(Color.white);
        UPDATEButton.setForeground(Color.white);
        REMOVEButton.setForeground(Color.white);

        // Make buttons opaque - same as Food class
        ADDButton.setOpaque(true);
        CLEARButton.setOpaque(true);
        UPDATEButton.setOpaque(true);
        REMOVEButton.setOpaque(true);

        // Set fonts - same as Food class
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font radioFont = new Font("Segoe UI", Font.PLAIN, 14);
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

        // Set spinner font - same as Food class
        spinner1.setFont(fieldFont);

        // Set radio button fonts - same as Food class
        YESRadioButton.setFont(radioFont);
        NORadioButton.setFont(radioFont);

        // Set button fonts - same as Food class
        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
    }

    private void setupPlaceholders() {
        // Set placeholder text for warranty field - same pattern as Food class
        warranty_field.setText(WARRANTY_PLACEHOLDER);

        // Add focus listener - same pattern as Food class
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

    // PUBLIC METHODS - same pattern as Food class

    public void clearForm() {
        name_field.setText("");
        spinner1.setValue(1);
        size_field.setText("");
        vendor_field.setText("");
        price_field.setText("");

        // Reset warranty field to placeholder - same as Food class
        warranty_field.setText(WARRANTY_PLACEHOLDER);
        warranty_field.setForeground(new Color(100, 100, 100, 180));
        warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        tooltype_field.setText("");
        material_field.setText("");
        textArea1.setText("");
        location_combobox.setSelectedIndex(0);

        // Reset radio button to default (NO) - same as Food class
        NORadioButton.setSelected(true);
    }

    // Radio button getter methods - same pattern as Food class
    public boolean requiresMaintenance() {
        return YESRadioButton.isSelected();
    }

    public void setRequiresMaintenance(boolean requires) {
        if (requires) {
            YESRadioButton.setSelected(true);
        } else {
            NORadioButton.setSelected(true);
        }
    }

    // Action listener methods - same as Food class
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

    // Form field getter methods - same pattern as Food class
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

    // Setter methods for form fields - same pattern as Food class
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
        Object[][] data = {
                {"Hammer", 2, "12in", "GARAGE", "Hardware Co", "$24.99", "01/15/2023", "Hand Tool", "Steel", "NO", "Claw hammer"},
                {"Drill", 1, "18V", "WORKSHOP", "Power Tools", "$89.99", "03/22/2023", "Power Tool", "Metal", "YES", "Cordless drill"},
                {"Wrench Set", 1, "10pc", "GARAGE", "Tool Master", "$39.99", "", "Hand Tool", "Chrome", "NO", "Complete set"}
        };

        Objects.requireNonNull(table1).setModel(new DefaultTableModel(
                data,
                new String[]{"Name", "Qty", "Size", "Location", "Vendor", "Price", "Warranty", "Type", "Material", "Maint", "Description"}
        ));

        // Table styling - same as Food class
        table1.setRowHeight(25);
        table1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table1.getTableHeader().setBackground(new Color(70, 130, 180));
        table1.getTableHeader().setForeground(Color.WHITE);
        table1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table1.setSelectionBackground(new Color(100, 149, 237));
        table1.setSelectionForeground(Color.WHITE);
        table1.setGridColor(Color.LIGHT_GRAY);
        table1.setShowGrid(true);

        // Set column widths
        table1.getColumnModel().getColumn(0).setPreferredWidth(80);
        table1.getColumnModel().getColumn(1).setPreferredWidth(40);
        table1.getColumnModel().getColumn(2).setPreferredWidth(50);
        table1.getColumnModel().getColumn(3).setPreferredWidth(70);
        table1.getColumnModel().getColumn(4).setPreferredWidth(80);
        table1.getColumnModel().getColumn(5).setPreferredWidth(60);
        table1.getColumnModel().getColumn(6).setPreferredWidth(80);
        table1.getColumnModel().getColumn(7).setPreferredWidth(70);
        table1.getColumnModel().getColumn(8).setPreferredWidth(70);
        table1.getColumnModel().getColumn(9).setPreferredWidth(50);
        table1.getColumnModel().getColumn(10).setPreferredWidth(120);
    }
}