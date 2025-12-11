package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Clothing;
import Model.Entities.Item;
import Model.Enums.Category;
import UI.Utilities.PanelAppearance;
import UI.Utilities.PanelActionListeners;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ClothingPanel extends PanelAppearance implements PanelActionListeners {
    private JTextField condition_field, fabrictype_field;
    private JLabel condition_label, fabrictype_label, size_label;
    private JComboBox<String> size_combobox;
    private final InventoryManager inventoryManager;

    public ClothingPanel() {
        inventoryManager = InventoryManager.getInstance();
        initClothingComponents();
        setupClothingLayout();
        setupClothingAppearance();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
        setTitle("CLOTHING");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> itemTable.adjustRowHeights());
            }
        });
    }

    private void initClothingComponents() {
        condition_field = new JTextField(8);
        fabrictype_field = new JTextField(8);

        size_combobox = new JComboBox<>(new String[]{
                "XS", "S", "M", "L", "XL", "XXL"
        });

        condition_label = new JLabel("CONDITION:");
        fabrictype_label = new JLabel("FABRIC TYPE:");
        size_label = new JLabel("SIZE:");
    }

    private void setupClothingLayout() {
        // Don't override the panel layout - it's already set in PanelAppearance
        // Instead, we need to add our clothing-specific fields to the existing layout

        // Get the constraints for adding to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 4, 3, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Add clothing fields after the base form fields
        // We'll assume there are 6 base fields (0-5), so start at row 6
        int startRow = 6;

        addFormRow(panel, gbc, condition_label, condition_field, startRow++);
        addFormRow(panel, gbc, fabrictype_label, fabrictype_field, startRow++);
        addFormRow(panel, gbc, size_label, size_combobox, startRow++);

        // Update the description panel position
        gbc.gridx = 0;
        gbc.gridy = startRow;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0.6;
        panel.add(description_panel, gbc);
    }

    private void setupClothingAppearance() {
        Color bg = new Color(0xF5F5F5);
        Color black = new Color(-16777216);

        condition_field.setBackground(bg);
        fabrictype_field.setBackground(bg);
        size_combobox.setBackground(bg);

        condition_field.setForeground(black);
        fabrictype_field.setForeground(black);
        size_combobox.setForeground(black);

        condition_label.setForeground(black);
        fabrictype_label.setForeground(black);
        size_label.setForeground(black);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        condition_label.setFont(labelFont);
        fabrictype_label.setFont(labelFont);
        size_label.setFont(labelFont);

        condition_field.setFont(fieldFont);
        fabrictype_field.setFont(fieldFont);
        size_combobox.setFont(fieldFont);

        condition_field.setPreferredSize(new Dimension(110, 26));
        fabrictype_field.setPreferredSize(new Dimension(110, 26));
        size_combobox.setPreferredSize(new Dimension(110, 26));
    }

    //GETTERS
    public String getConditionInput() {
        return condition_field.getText();
    }
    public String getFabricTypeInput() {
        return fabrictype_field.getText();
    }
    public String getSizeInput() {
        return (String) size_combobox.getSelectedItem();
    }

    // SETTERS
    public void setConditionInput(String condition) {
        condition_field.setText(condition);
    }
    public void setFabrictypeInput(String fabricType) {
        fabrictype_field.setText(fabricType);
    }
    public void setSizeInput(String size) {
        size_combobox.setSelectedItem(size);
    }

    //METHODS
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

        for (Item item : inventoryManager.getItemsByCategory(Category.CLOTHING)) {
            if (item instanceof Clothing clothing) {
                // FIXED: Match ItemTable column order
                itemTable.addRow(new Object[]{
                        clothing.getName(),
                        clothing.getQuantity(),
                        clothing.getLocation(),
                        clothing.getPurchaseDate(),
                        clothing.getPurchasePrice(),
                        clothing.descriptionDetails()
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
        condition_field.setText("");
        fabrictype_field.setText("");
        size_combobox.setSelectedIndex(0);
    }

    private double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return 0.0;
        }
        String cleaned = priceStr.replace("$", "").replace(",", "").trim();
        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Clothing createClothingFromForm() {
        return new Clothing(
                getNameInput(),
                getDescriptionInput(),
                getQuantityInput(),
                parsePrice(getPriceInput()),
                getPurchaseDateInput(),
                getVendorInput(),
                getLocationInput(),
                getConditionInput(),
                getFabricTypeInput(),
                getSizeInput()
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

        if (getConditionInput().trim().isEmpty()) {
            showError("Condition cannot be empty!");
            return false;
        }

        if (getFabricTypeInput().trim().isEmpty()) {
            showError("Fabric Type cannot be empty!");
            return false;
        }

        if (getSizeInput() == null || getSizeInput().trim().isEmpty()) {
            showError("Size cannot be empty!");
            return false;
        }

        return validateDateField(getPurchaseDateInput());
    }

    private boolean validateDateField(String dateText) {
        if (!dateText.isEmpty() && !dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showError("Purchase Date must be in MM/DD/YYYY format");
            return false;
        }
        return true;
    }

    // Event handlers
    private void addItem() {
        try {
            if (validateForm()) {
                Clothing clothing = createClothingFromForm();
                inventoryManager.addItem(clothing);
                loadItems();
                clearForm();
                showSuccess("Clothing item added successfully!");
            }
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

    private void populateFormFromSelectedRow(int selectedRow) {
        try {
            List<Item> clothingItems = inventoryManager.getItemsByCategory(Category.CLOTHING);

            if (selectedRow < clothingItems.size()) {
                Clothing clothing = (Clothing) clothingItems.get(selectedRow);
                populateForm(clothing);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Clothing clothing) {
        setNameInput(clothing.getName());
        setQuantityInput(clothing.getQuantity());
        setVendorInput(clothing.getVendor());
        setPriceInput(String.format("$%,.2f", clothing.getPurchasePrice())); // Format price
        setPurchaseDateInput(clothing.getPurchaseDate());
        setConditionInput(clothing.getCondition());
        setFabrictypeInput(clothing.getFabricType());
        setSizeInput(clothing.getSize());
        setDescriptionInput(clothing.getDescription());
        setLocationInput(clothing.getLocation());
    }
}