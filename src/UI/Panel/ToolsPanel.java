package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Tool;
import Model.Entities.Item;
import Model.Enums.Category;
import UI.Utilities.PanelAppearance;
import UI.Utilities.PanelActionListeners;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ToolsPanel extends PanelAppearance implements PanelActionListeners {
    private JTextField steelgrade_field, tooltype_field, material_field, lmd_field, maintenanceInterval_field;
    private JLabel steelgrade_label, tooltype_label, material_label, lmd_label, maintenanceInterval_label, maintenance_label;
    private JPanel maintenancePanel;
    private JRadioButton yesRadio, noRadio;
    private final InventoryManager inventoryManager;
    private static final String WARRANTY_PLACEHOLDER = "MM/DD/YYYY";
    private static final String[] TOOLS_LOCATIONS = {
            "TOOLSHED", "WORKSHOP", "GARAGE", "UTILITY ROOM",
            "STORAGE SHED", "BASEMENT", "CRAFT ROOM"
    };

    public ToolsPanel() {
        inventoryManager = InventoryManager.getInstance();
        initToolsComponents();
        setupToolsLayout();
        setupToolsAppearance();
        setupToolsPlaceholders();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
        setTitle("TOOLS");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> itemTable.adjustRowHeights());
            }
        });
    }

    private void initToolsComponents() {
        steelgrade_field = new JTextField(8);
        tooltype_field = new JTextField(8);
        material_field = new JTextField(8);
        lmd_field = new JTextField(8);
        maintenanceInterval_field = new JTextField(8);

        steelgrade_label = new JLabel("STEEL GRADE:");
        tooltype_label = new JLabel("TOOL TYPE:");
        material_label = new JLabel("MATERIAL:");
        lmd_label = new JLabel("LAST MAINTENANCE:");
        maintenanceInterval_label = new JLabel("INTERVAL (DAYS):");
        maintenance_label = new JLabel("NEEDS MAINTENANCE:");

        maintenancePanel = new JPanel();
        yesRadio = new JRadioButton("YES");
        noRadio = new JRadioButton("NO");
        ButtonGroup maintenanceGroup = new ButtonGroup();
        maintenanceGroup.add(yesRadio);
        maintenanceGroup.add(noRadio);
        noRadio.setSelected(true);

        location_combobox.removeAllItems();
        for (String location : TOOLS_LOCATIONS) {
            location_combobox.addItem(location);
        }

        yesRadio.setFocusable(false);
        noRadio.setFocusable(false);
    }

    private void setupToolsLayout() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = setupFormLayout(panel, formGbc);

        addFormRow(panel, formGbc, tooltype_label, tooltype_field, row++);
        addFormRow(panel, formGbc, material_label, material_field, row++);
        addFormRow(panel, formGbc, steelgrade_label, steelgrade_field, row++);
        addFormRow(panel, formGbc, lmd_label, lmd_field, row++);
        addFormRow(panel, formGbc, maintenanceInterval_label, maintenanceInterval_field, row++);
        addRadioButtonRow(panel, formGbc, maintenance_label, maintenancePanel, yesRadio, noRadio, row++);

        setupDescriptionPanel(row, formGbc);
    }

    private void addRadioButtonRow(JPanel panel, GridBagConstraints formGbc, JLabel label,
                                   JPanel radioPanel, JRadioButton option1, JRadioButton option2, int row) {
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        radioPanel.setLayout(new GridLayout(1, 2, 20, 0));
        radioPanel.add(option1);
        radioPanel.add(option2);
        panel.add(radioPanel, formGbc);
    }

    private void setupToolsAppearance() {
        Color bg = new Color(0xF5F5F5);
        Color black = new Color(-16777216);

        steelgrade_field.setBackground(bg);
        tooltype_field.setBackground(bg);
        material_field.setBackground(bg);
        lmd_field.setBackground(bg);
        maintenanceInterval_field.setBackground(bg);
        maintenancePanel.setBackground(bg);
        yesRadio.setBackground(bg);
        noRadio.setBackground(bg);

        steelgrade_field.setForeground(black);
        tooltype_field.setForeground(black);
        material_field.setForeground(black);
        lmd_field.setForeground(black);
        maintenanceInterval_field.setForeground(black);
        yesRadio.setForeground(black);
        noRadio.setForeground(black);

        steelgrade_label.setForeground(black);
        tooltype_label.setForeground(black);
        material_label.setForeground(black);
        lmd_label.setForeground(black);
        maintenanceInterval_label.setForeground(black);
        maintenance_label.setForeground(black);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font radioFont = new Font("Segoe UI", Font.BOLD, 12);

        steelgrade_label.setFont(labelFont);
        tooltype_label.setFont(labelFont);
        material_label.setFont(labelFont);
        lmd_label.setFont(labelFont);
        maintenanceInterval_label.setFont(labelFont);
        maintenance_label.setFont(labelFont);

        steelgrade_field.setFont(fieldFont);
        tooltype_field.setFont(fieldFont);
        material_field.setFont(fieldFont);
        lmd_field.setFont(fieldFont);
        maintenanceInterval_field.setFont(fieldFont);
        yesRadio.setFont(radioFont);
        noRadio.setFont(radioFont);
    }

    private void setupToolsPlaceholders() {
        lmd_field.setText(WARRANTY_PLACEHOLDER);
        lmd_field.setForeground(new Color(100, 100, 100, 180));
        lmd_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        lmd_field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (lmd_field.getText().equals(WARRANTY_PLACEHOLDER)) {
                    lmd_field.setText("");
                    lmd_field.setForeground(Color.BLACK);
                    lmd_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (lmd_field.getText().isEmpty()) {
                    lmd_field.setText(WARRANTY_PLACEHOLDER);
                    lmd_field.setForeground(new Color(100, 100, 100, 180));
                    lmd_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });
    }

    //GETTERS
    public String getSteelGradeInput() {
        return steelgrade_field.getText();
    }

    public String getToolTypeInput() {
        return tooltype_field.getText();
    }

    public String getMaterialInput() {
        return material_field.getText();
    }

    public String getLMDInput() {
        String text = lmd_field.getText();
        if (text.equals(WARRANTY_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public int getMaintenanceIntervalDays() {
        try {
            if (maintenanceInterval_field.getText().trim().isEmpty()) {
                return 0;
            }
            return Integer.parseInt(maintenanceInterval_field.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean getMaintenanceNeeded() {
        return yesRadio.isSelected();
    }

    //SETTERS
    public void setSteelGradeInput(String steelGrade) {
        steelgrade_field.setText(steelGrade);
    }

    public void setToolTypeInput(String toolType) {
        tooltype_field.setText(toolType);
    }

    public void setMaterialInput(String material) {
        material_field.setText(material);
    }

    public void setLMDInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            lmd_field.setText(WARRANTY_PLACEHOLDER);
            lmd_field.setForeground(new Color(100, 100, 100, 180));
            lmd_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            lmd_field.setText(date);
            lmd_field.setForeground(Color.BLACK);
            lmd_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    public void setMaintenanceIntervalDays(int days) {
        maintenanceInterval_field.setText(String.valueOf(days));
    }

    public void setMaintenanceNeeded(boolean needsMaintenance) {
        if (needsMaintenance) {
            yesRadio.setSelected(true);
        } else {
            noRadio.setSelected(true);
        }
    }

    @Override
    public void setupButtonListeners() {
        ADDButton.addActionListener(_ -> addItem());
        UPDATEButton.addActionListener(_ -> updateItem());
        REMOVEButton.addActionListener(_ -> removeItem());
        CLEARButton.addActionListener(_ -> clearForm());
        REFRESHButton.addActionListener(_ -> refreshForm());
    }

    @Override
    public void loadItems() {
        itemTable.clearTable();
        inventoryManager.loadFromFile();

        for (Item item : inventoryManager.getItemsByCategory(Category.TOOLS)) {
            if (item instanceof Tool tool) {
                itemTable.addRow(new Object[]{
                        tool.getName(),
                        tool.getQuantity(),
                        tool.getLocation(),
                        tool.getPurchaseDate(),
                        tool.getPurchasePrice(),
                        tool.descriptionDetails()
                });
            }
        }

        itemTable.adjustRowHeights();
    }

    @Override
    public void setupTableSelectionListener() {
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

    @Override
    public void refreshForm() {
        loadItems();
        clearForm();
        selectedIndex = -1;
    }

    @Override
    public void clearForm() {
        super.clearForm();
        steelgrade_field.setText("");
        tooltype_field.setText("");
        material_field.setText("");
        setLMDInput("");
        maintenanceInterval_field.setText("");
        setMaintenanceNeeded(false);
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
                getSteelGradeInput(),
                getMaterialInput(),
                getMaintenanceNeeded(),
                getLMDInput(),
                getMaintenanceIntervalDays()
        );
    }

    private boolean validateForm() {
        if (getNameInput().trim().isEmpty()) {
            showError("Name cannot be empty!");
            return false;
        }

        try {
            double price = parsePrice(getPriceInput());
            if (price < 0) {
                showError("Price must not be a negative number");
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

        if (getSteelGradeInput().trim().isEmpty()) {
            showError("Steel Grade cannot be empty!");
            return false;
        }

        if (validateDateField(getPurchaseDateInput(), "Purchase Date")) return false;
        if (validateDateField(getLMDInput(), "Last Maintenance Date")) return false;

        if (isFutureDate(getLMDInput())) {
            showError("Last Maintenance Date cannot be a future date");
            return false;
        }

        try {
            int interval = getMaintenanceIntervalDays();
            if (interval < 0) {
                showError("Maintenance Interval cannot be negative");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Maintenance Interval must be a valid number");
            return false;
        }

        return true;
    }

    private boolean isFutureDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.isEmpty() || dateStr.equals(WARRANTY_PLACEHOLDER)) {
                return false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate inputDate = LocalDate.parse(dateStr, formatter);
            LocalDate today = LocalDate.now();
            return inputDate.isAfter(today);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateDateField(String dateText, String fieldName) {
        if (dateText == null || dateText.isEmpty() || dateText.equals(WARRANTY_PLACEHOLDER)) {
            return false;
        }
        if (!dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showError(fieldName + " must be in MM/DD/YYYY format");
            return true;
        }
        return false;
    }

    private void addItem() {
        try {
            if (validateForm()) {
                Tool tool = createToolFromForm();
                inventoryManager.addItem(tool);
                loadItems();
                clearForm();
                showSuccess("Tool item added successfully!");
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
                    List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> toolsItems = inventoryManager.getItemsByCategory(Category.TOOLS);

                    if (selectedIndex < toolsItems.size()) {
                        Item originalItem = toolsItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, tool);
                            loadItems();
                            clearForm();
                            selectedIndex = -1;
                            showSuccess("Tool item updated successfully!");
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
        setSteelGradeInput(tool.getSteelGrade());
        setMaterialInput(tool.getMaterial());
        setDescriptionInput(tool.getDescription());
        setLocationInput(tool.getLocation());
        setMaintenanceNeeded(tool.getMaintenanceNeeded());
        setLMDInput(tool.getLastMaintenanceDate());
        setMaintenanceIntervalDays(tool.getMaintenanceIntervalDays());
    }
}