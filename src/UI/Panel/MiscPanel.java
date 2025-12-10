package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Miscellaneous;
import Model.Entities.Item;
import Model.Enums.Category;
import UI.Utilities.PanelAppearance;
import UI.Utilities.PanelActionListeners;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MiscPanel extends PanelAppearance implements PanelActionListeners {
    private JTextField itemType_field, usage_field;
    private JLabel itemType_label, usage_label, condition_label;
    private JPanel conditionPanel;
    private JRadioButton intactRadio, damagedRadio;
    private final InventoryManager inventoryManager;
    private static final String[] MISC_LOCATIONS = {
            "STORAGE ROOM", "CABINET", "SHELF",
            "DRAWER", "HALLWAY CABINET", "CONTAINER RACK", "ATTIC", "STORAGE"
    };

    public MiscPanel() {
        inventoryManager = InventoryManager.getInstance();
        initMiscComponents();
        setupMiscLayout();
        setupMiscAppearance();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
        setTitle("MISCELLANEOUS");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> itemTable.adjustRowHeights());
            }
        });
    }

    private void initMiscComponents() {
        itemType_field = new JTextField(8);
        usage_field = new JTextField(8);

        itemType_label = new JLabel("ITEM TYPE:");
        usage_label = new JLabel("USAGE:");
        condition_label = new JLabel("CONDITION:");

        conditionPanel = new JPanel();
        intactRadio = new JRadioButton("INTACT");
        damagedRadio = new JRadioButton("DAMAGED");
        ButtonGroup conditionGroup = new ButtonGroup();
        conditionGroup.add(intactRadio);
        conditionGroup.add(damagedRadio);
        intactRadio.setSelected(true);

        location_combobox.removeAllItems();
        for (String location : MISC_LOCATIONS) {
            location_combobox.addItem(location);
        }

        intactRadio.setFocusable(false);
        damagedRadio.setFocusable(false);
    }

    private void setupMiscLayout() {
        // Set up misc-specific layout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = setupFormLayout(panel, formGbc);

        addFormRow(panel, formGbc, itemType_label, itemType_field, row++);
        addFormRow(panel, formGbc, usage_label, usage_field, row++);
        addRadioButtonRow(panel, formGbc, condition_label, conditionPanel, intactRadio, damagedRadio, row++);

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

    private void setupMiscAppearance() {
        Color bg = new Color(0xF5F5F5);
        Color black = new Color(-16777216);

        itemType_field.setBackground(bg);
        usage_field.setBackground(bg);
        conditionPanel.setBackground(bg);
        intactRadio.setBackground(bg);
        damagedRadio.setBackground(bg);

        itemType_field.setForeground(black);
        usage_field.setForeground(black);
        intactRadio.setForeground(black);
        damagedRadio.setForeground(black);

        itemType_label.setForeground(black);
        usage_label.setForeground(black);
        condition_label.setForeground(black);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font radioFont = new Font("Segoe UI", Font.BOLD, 12);

        itemType_label.setFont(labelFont);
        usage_label.setFont(labelFont);
        condition_label.setFont(labelFont);

        itemType_field.setFont(fieldFont);
        usage_field.setFont(fieldFont);
        intactRadio.setFont(radioFont);
        damagedRadio.setFont(radioFont);
    }

    //GETTERS
    public String getItemTypeInput() {
        return itemType_field.getText();
    }

    public String getUsageInput() {
        return usage_field.getText();
    }

    public String getConditionInput() {
        return damagedRadio.isSelected() ? "DAMAGED" : "INTACT";
    }

    //SETTERS
    public void setItemTypeInput(String itemType) {
        itemType_field.setText(itemType);
    }

    public void setUsageInput(String usage) {
        usage_field.setText(usage);
    }

    public void setCondition(String condition) {
        if ("DAMAGED".equalsIgnoreCase(condition)) {
            damagedRadio.setSelected(true);
        } else {
            intactRadio.setSelected(true);
        }
    }

    // Interface implementation
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

        for (Item item : inventoryManager.getItemsByCategory(Category.MISCELLANEOUS)) {
            if (item instanceof Miscellaneous misc) {
                itemTable.addRow(new Object[]{
                        misc.getName(),
                        misc.getQuantity(),
                        misc.getLocation(),
                        misc.getVendor(),
                        misc.getPurchasePrice(),
                        misc.descriptionDetails()
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
        itemType_field.setText("");
        usage_field.setText("");
        setCondition("INTACT");
    }

    private double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return 0.0;
        }
        String cleaned = priceStr.replace("$", "").replace(",", "").trim();
        return Double.parseDouble(cleaned);
    }

    private Miscellaneous createMiscFromForm() {
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
                getConditionInput()
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

        if (getItemTypeInput().trim().isEmpty()) {
            showError("Item Type cannot be empty!");
            return false;
        }

        if (getUsageInput().trim().isEmpty()) {
            showError("Usage cannot be empty!");
            return false;
        }

        return validateDateField(getPurchaseDateInput());
    }

    private boolean validateDateField(String dateText) {
        if (!dateText.isEmpty() && !dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            showError("Purchase Date" + " must be in MM/DD/YYYY format");
            return false;
        }
        return true;
    }

    private void addItem() {
        try {
            if (validateForm()) {
                Miscellaneous misc = createMiscFromForm();
                inventoryManager.addItem(misc);
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
                    Miscellaneous misc = createMiscFromForm();
                    List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> miscItems = inventoryManager.getItemsByCategory(Category.MISCELLANEOUS);

                    if (selectedIndex < miscItems.size()) {
                        Item originalItem = miscItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, misc);
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
                    List<Item> miscItems = inventoryManager.getItemsByCategory(Category.MISCELLANEOUS);

                    if (selectedIndex < miscItems.size()) {
                        Item itemToRemove = miscItems.get(selectedIndex);
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
            List<Item> miscItems = inventoryManager.getItemsByCategory(Category.MISCELLANEOUS);

            if (selectedRow < miscItems.size()) {
                Miscellaneous misc = (Miscellaneous) miscItems.get(selectedRow);
                populateForm(misc);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Miscellaneous misc) {
        setNameInput(misc.getName());
        setQuantityInput(misc.getQuantity());
        setVendorInput(misc.getVendor());
        setPriceInput(String.valueOf(misc.getPurchasePrice()));
        setPurchaseDateInput(misc.getPurchaseDate());
        setCondition(misc.getCondition());
        setUsageInput(misc.getUsage());
        setItemTypeInput(misc.getItemType());
        setDescriptionInput(misc.getDescription());
        setLocationInput(misc.getLocation());
    }
}