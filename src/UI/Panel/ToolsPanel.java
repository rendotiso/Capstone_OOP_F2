package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Tool;
import Model.Entities.Item;
import Model.Enums.Category;
import UI.Utilities.PanelMaintainableAppearance;
import UI.Utilities.PanelActionListeners;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ToolsPanel extends PanelMaintainableAppearance implements PanelActionListeners {
    private JTextField steelgrade_field, tooltype_field, material_field;
    private JLabel steelgrade_label, tooltip_label, material_label;
    private final InventoryManager inventoryManager;
    private static final String[] TOOLS_LOCATIONS = {
            "TOOLSHED", "WORKSHOP", "GARAGE", "UTILITY ROOM",
            "STORAGE SHED", "BASEMENT", "CRAFT ROOM"
    };

    public ToolsPanel() {
        inventoryManager = InventoryManager.getInstance();
        initToolsComponents();
        setupToolsLayout();
        setupToolsAppearance();
        setupMaintenanceListener();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
        setTitle("TOOLS");
    }

    private void initToolsComponents() {
        steelgrade_field = new JTextField(8);
        tooltype_field = new JTextField(8);
        material_field = new JTextField(8);

        steelgrade_label = new JLabel("STEEL GRADE:");
        tooltip_label = new JLabel("TOOL TYPE:");
        material_label = new JLabel("MATERIAL:");

        location_combobox.removeAllItems();
        for (String location : TOOLS_LOCATIONS) {
            location_combobox.addItem(location);
        }
    }

    private void setupToolsLayout() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = setupFormLayout(panel, formGbc);

        addFormRow(panel, formGbc, tooltip_label, tooltype_field, row++);
        addFormRow(panel, formGbc, material_label, material_field, row++);
        addFormRow(panel, formGbc, steelgrade_label, steelgrade_field, row++);

        row = setupMaintainableFormLayout(panel, formGbc, row);

        setupDescriptionPanel(row, formGbc);
    }

    private void setupToolsAppearance() {
        Color bg = new Color(0xF5F5F5);
        Color black = new Color(-16777216);

        steelgrade_field.setBackground(bg);
        tooltype_field.setBackground(bg);
        material_field.setBackground(bg);

        steelgrade_field.setForeground(black);
        tooltype_field.setForeground(black);
        material_field.setForeground(black);

        steelgrade_label.setForeground(black);
        tooltip_label.setForeground(black);
        material_label.setForeground(black);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        steelgrade_label.setFont(labelFont);
        tooltip_label.setFont(labelFont);
        material_label.setFont(labelFont);

        steelgrade_field.setFont(fieldFont);
        tooltype_field.setFont(fieldFont);
        material_field.setFont(fieldFont);
    }

    // GETTERS
    public String getSteelGradeInput() {
        return steelgrade_field.getText();
    }
    public String getToolTypeInput() {
        return tooltype_field.getText();
    }
    public String getMaterialInput() {
        return material_field.getText();
    }

    // SETTERS
    public void setSteelGradeInput(String steelGrade) {
        steelgrade_field.setText(steelGrade);
    }
    public void setToolTypeInput(String toolType) {
        tooltype_field.setText(toolType);
    }
    public void setMaterialInput(String material) {
        material_field.setText(material);
    }

    // METHODS
    @Override
    public void setupButtonListeners() {
        ADDButton.addActionListener(e -> addItem());
        UPDATEButton.addActionListener(e -> updateItem());
        REMOVEButton.addActionListener(e -> removeItem());
        CLEARButton.addActionListener(e -> clearForm());
        REFRESHButton.addActionListener(e -> refreshForm());
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
                getLastMaintenanceDateInput(),
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

        if (!validateDateField(getPurchaseDateInput(), "Purchase Date")) return false;
        return validateDateField(getLastMaintenanceDateInput(), "Last Maintenance Date");
    }

    private boolean validateDateField(String dateText, String fieldName) {
        if (!dateText.isEmpty() && !dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showError(fieldName + " must be in MM/DD/YYYY format");
            return false;
        }
        return true;
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
        setLastMaintenanceDateInput(tool.getLastMaintenanceDate());
        setMaintenanceIntervalDays(tool.getMaintenanceIntervalDays());
    }
}