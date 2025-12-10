package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Food;
import Model.Entities.Item;
import Model.Enums.Category;
import UI.Utilities.PanelAppearance;
import UI.Utilities.PanelActionListeners;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FoodPanel extends PanelAppearance implements PanelActionListeners {
    private JTextField expiryDate_field;
    private JLabel expiryDate_label, perish_label, canned_label;
    private JPanel perishPanel, cannedPanel;
    private JCheckBox perishCheckBox, cannedCheckBox;
    private final InventoryManager inventoryManager;
    private static final String EXPIRY_DATE_PLACEHOLDER = "MM/DD/YYYY";
    private static final String[] FOOD_LOCATIONS = {
            "REFRIGERATOR", "PANTRY", "FREEZER",
            "FOOD STORAGE RACK", "KITCHEN SHELVES", "COUNTERTOP CONTAINERS"
    };
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public FoodPanel() {
        inventoryManager = InventoryManager.getInstance();
        initFoodComponents();
        setupFoodLayout();
        setupFoodAppearance();
        setupExpiryPlaceholder();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
        setTitle("FOOD");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> itemTable.adjustRowHeights());
            }
        });
    }

    private void initFoodComponents() {
        expiryDate_field = new JTextField(8);
        expiryDate_label = new JLabel("EXPIRY DATE:");
        perish_label = new JLabel("PERISHABLE?");
        canned_label = new JLabel("CANNED GOOD?");

        perishPanel = new JPanel();
        perishCheckBox = new JCheckBox();

        cannedPanel = new JPanel();
        cannedCheckBox = new JCheckBox();

        location_combobox.removeAllItems();
        for (String location : FOOD_LOCATIONS) {
            location_combobox.addItem(location);
        }

        perishCheckBox.setFocusable(false);
        cannedCheckBox.setFocusable(false);
    }

    private void setupFoodLayout() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = setupFormLayout(panel, formGbc);

        addFormRow(panel, formGbc, expiryDate_label, expiryDate_field, row++);
        addCheckBoxRow(panel, formGbc, perish_label, perishPanel, perishCheckBox, row++);
        addCheckBoxRow(panel, formGbc, canned_label, cannedPanel, cannedCheckBox, row++);

        setupDescriptionPanel(row, formGbc);
    }

    private void addCheckBoxRow(JPanel panel, GridBagConstraints formGbc, JLabel label,
                                JPanel checkBoxPanel, JCheckBox checkBox, int row) {
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkBoxPanel.add(checkBox);
        panel.add(checkBoxPanel, formGbc);
    }

    private void setupFoodAppearance() {
        Color bg = new Color(0xF5F5F5);
        Color black = new Color(-16777216);

        expiryDate_field.setBackground(bg);
        perishPanel.setBackground(bg);
        cannedPanel.setBackground(bg);
        perishCheckBox.setBackground(bg);
        cannedCheckBox.setBackground(bg);

        expiryDate_field.setForeground(black);
        perishCheckBox.setForeground(black);
        cannedCheckBox.setForeground(black);

        expiryDate_label.setForeground(black);
        perish_label.setForeground(black);
        canned_label.setForeground(black);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font checkboxFont = new Font("Segoe UI", Font.BOLD, 12);

        expiryDate_label.setFont(labelFont);
        perish_label.setFont(labelFont);
        canned_label.setFont(labelFont);

        expiryDate_field.setFont(fieldFont);
        perishCheckBox.setFont(checkboxFont);
        cannedCheckBox.setFont(checkboxFont);
    }

    private void setupExpiryPlaceholder() {
        expiryDate_field.setText(EXPIRY_DATE_PLACEHOLDER);
        expiryDate_field.setForeground(new Color(100, 100, 100, 180));
        expiryDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        expiryDate_field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (expiryDate_field.getText().equals(EXPIRY_DATE_PLACEHOLDER)) {
                    expiryDate_field.setText("");
                    expiryDate_field.setForeground(Color.BLACK);
                    expiryDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (expiryDate_field.getText().isEmpty()) {
                    expiryDate_field.setText(EXPIRY_DATE_PLACEHOLDER);
                    expiryDate_field.setForeground(new Color(100, 100, 100, 180));
                    expiryDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });
    }

    // GETTERS
    public String getExpiryDateInput() {
        String text = expiryDate_field.getText();
        if (text.equals(EXPIRY_DATE_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public boolean isPerishable() {
        return perishCheckBox.isSelected();
    }

    public boolean isCannedGood() {
        return cannedCheckBox.isSelected();
    }

    // SETTERS
    public void setExpiryDateInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            expiryDate_field.setText(EXPIRY_DATE_PLACEHOLDER);
            expiryDate_field.setForeground(new Color(100, 100, 100, 180));
            expiryDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            expiryDate_field.setText(date);
            expiryDate_field.setForeground(Color.BLACK);
            expiryDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    public void setPerishable(boolean isPerishable) {
        perishCheckBox.setSelected(isPerishable);
    }

    public void setCannedGood(boolean isCannedGood) {
        cannedCheckBox.setSelected(isCannedGood);
    }

    // METHODS
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

        for (Item item : inventoryManager.getItemsByCategory(Category.FOOD)) {
            if (item instanceof Food food) {
                itemTable.addRow(new Object[]{
                        food.getName(),
                        food.getQuantity(),
                        food.getLocation(),
                        food.getPurchaseDate(),
                        food.getPurchasePrice(),
                        food.descriptionDetails()
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
        setExpiryDateInput("");
        setPerishable(false);
        setCannedGood(false);
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
                isPerishable()
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

        if (getExpiryDateInput().trim().isEmpty()) {
            showError("Expiry Date cannot be empty!");
            return false;
        }

        if (validateDateField(getPurchaseDateInput(), "Purchase Date")) return false;
        if (validateDateField(getExpiryDateInput(), "Expiry Date")) return false;

        if (isExpired(getExpiryDateInput())) {
            showError("Warning: This item has already expired!");
            int confirm = JOptionPane.showConfirmDialog(this,
                    "This item has already expired. Are you sure you want to add it?",
                    "Expired Item Warning", JOptionPane.YES_NO_OPTION);
            return confirm == JOptionPane.YES_OPTION;
        }

        return true;
    }

    private boolean validateDateField(String dateText, String fieldName) {
        if (dateText == null || dateText.trim().isEmpty()) {
            return false;
        }

        if (!dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showError(fieldName + " must be in MM/DD/YYYY format");
            return true;
        }

        try {
            LocalDate.parse(dateText, DATE_FORMATTER);
            return false;
        } catch (DateTimeParseException e) {
            showError(fieldName + " is not a valid date: " + dateText);
            return true;
        }
    }

    private boolean isExpired(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }

        try {
            LocalDate expiryDate = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalDate today = LocalDate.now();
            return expiryDate.isBefore(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Event handlers
    private void addItem() {
        try {
            if (validateForm()) {
                Food food = createFoodFromForm();
                inventoryManager.addItem(food);
                loadItems();
                clearForm();
                showSuccess("Food item added successfully!");
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
                    List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> foodItems = inventoryManager.getItemsByCategory(Category.FOOD);

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

    private void populateFormFromSelectedRow(int selectedRow) {
        try {
            List<Item> foodItems = inventoryManager.getItemsByCategory(Category.FOOD);

            if (selectedRow < foodItems.size()) {
                Food food = (Food) foodItems.get(selectedRow);
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
        setPurchaseDateInput(food.getPurchaseDate());
        setExpiryDateInput(food.getExpiryDate());
        setPerishable(food.getIsPerishable());
        setCannedGood(food.getIsCanned());
        setDescriptionInput(food.getDescription());
        setLocationInput(food.getLocation());
    }
}