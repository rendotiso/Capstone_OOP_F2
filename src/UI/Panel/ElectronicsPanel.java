package UI.Panel;

import Model.Data.InventoryManager;
import Model.Entities.Electronic;
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

public class ElectronicsPanel extends PanelAppearance implements PanelActionListeners {
    private JTextField warranty_field, model_field, brand_field, lmd_field;
    private JLabel warranty_label, model_label, brand_label, lmd_label, maintenance_label;
    private JPanel maintenancePanel;
    private JRadioButton yesRadio, noRadio;
    private final InventoryManager inventoryManager;
    private static final String WARRANTY_PLACEHOLDER = "MM/DD/YYYY";
    private static final String[] ELECTRONICS_LOCATIONS = {
            "LAB ROOM", "SERVER ROOM", "RECORD ROOM",
            "CONTROL ROOM", "STUDIO", "WORKSHOP", "IT OFFICE"
    };

    public ElectronicsPanel() {
        inventoryManager = InventoryManager.getInstance();
        initElectronicsComponents();
        setupElectronicsLayout();
        setupElectronicsAppearance();
        setupElectronicsPlaceholders();
        setupButtonListeners();
        setupTableSelectionListener();
        loadItems();
        setTitle("ELECTRONICS");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> itemTable.adjustRowHeights());
            }
        });
    }

    private void initElectronicsComponents() {
        warranty_field = new JTextField(8);
        model_field = new JTextField(8);
        brand_field = new JTextField(8);
        lmd_field = new JTextField(8);

        warranty_label = new JLabel("WARRANTY DATE:");
        model_label = new JLabel("MODEL:");
        brand_label = new JLabel("BRAND:");
        lmd_label = new JLabel("LAST MAINTENANCE:");
        maintenance_label = new JLabel("NEEDS MAINTENANCE:");

        maintenancePanel = new JPanel();
        yesRadio = new JRadioButton("YES");
        noRadio = new JRadioButton("NO");
        ButtonGroup maintenanceGroup = new ButtonGroup();
        maintenanceGroup.add(yesRadio);
        maintenanceGroup.add(noRadio);
        noRadio.setSelected(true);

        location_combobox.removeAllItems();
        for (String location : ELECTRONICS_LOCATIONS) {
            location_combobox.addItem(location);
        }

        yesRadio.setFocusable(false);
        noRadio.setFocusable(false);
    }

    private void setupElectronicsLayout() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = setupFormLayout(panel, formGbc);

        addFormRow(panel, formGbc, warranty_label, warranty_field, row++);
        addFormRow(panel, formGbc, model_label, model_field, row++);
        addFormRow(panel, formGbc, brand_label, brand_field, row++);
        addFormRow(panel, formGbc, lmd_label, lmd_field, row++);
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

    private void setupElectronicsAppearance() {
        Color bg = new Color(0xF5F5F5);
        Color black = new Color(-16777216);

        warranty_field.setBackground(bg);
        model_field.setBackground(bg);
        brand_field.setBackground(bg);
        lmd_field.setBackground(bg);
        maintenancePanel.setBackground(bg);
        yesRadio.setBackground(bg);
        noRadio.setBackground(bg);

        warranty_field.setForeground(black);
        model_field.setForeground(black);
        brand_field.setForeground(black);
        lmd_field.setForeground(black);
        yesRadio.setForeground(black);
        noRadio.setForeground(black);

        warranty_label.setForeground(black);
        model_label.setForeground(black);
        brand_label.setForeground(black);
        lmd_label.setForeground(black);
        maintenance_label.setForeground(black);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font radioFont = new Font("Segoe UI", Font.BOLD, 12);

        warranty_label.setFont(labelFont);
        model_label.setFont(labelFont);
        brand_label.setFont(labelFont);
        lmd_label.setFont(labelFont);
        maintenance_label.setFont(labelFont);

        warranty_field.setFont(fieldFont);
        model_field.setFont(fieldFont);
        brand_field.setFont(fieldFont);
        lmd_field.setFont(fieldFont);
        yesRadio.setFont(radioFont);
        noRadio.setFont(radioFont);
    }

    private void setupElectronicsPlaceholders() {
        warranty_field.setText(WARRANTY_PLACEHOLDER);
        warranty_field.setForeground(new Color(100, 100, 100, 180));
        warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        lmd_field.setText(WARRANTY_PLACEHOLDER);
        lmd_field.setForeground(new Color(100, 100, 100, 180));
        lmd_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        warranty_field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (warranty_field.getText().equals(WARRANTY_PLACEHOLDER)) {
                    warranty_field.setText("");
                    warranty_field.setForeground(Color.BLACK);
                    warranty_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (warranty_field.getText().isEmpty()) {
                    warranty_field.setText(WARRANTY_PLACEHOLDER);
                    warranty_field.setForeground(new Color(100, 100, 100, 180));
                    warranty_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });

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
    public String getWarrantyInput() {
        String text = warranty_field.getText();
        if (text.equals(WARRANTY_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public String getModelInput() {
        return model_field.getText();
    }

    public String getBrandInput() {
        return brand_field.getText();
    }

    public String getLMDInput() {
        String text = lmd_field.getText();
        if (text.equals(WARRANTY_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public boolean getMaintenanceNeeded() {
        return yesRadio.isSelected();
    }

    //SETTERS
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

    public void setModelInput(String model) {
        model_field.setText(model);
    }

    public void setBrandInput(String brand) {
        brand_field.setText(brand);
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

        for (Item item : inventoryManager.getItemsByCategory(Category.ELECTRONICS)) {
            if (item instanceof Electronic electronic) {
                itemTable.addRow(new Object[]{
                        electronic.getName(),
                        electronic.getQuantity(),
                        electronic.getLocation(),
                        electronic.getPurchaseDate(),
                        electronic.getPurchasePrice(),
                        electronic.descriptionDetails()
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
        setWarrantyInput("");
        setModelInput("");
        setBrandInput("");
        setLMDInput("");
        setMaintenanceNeeded(false);
    }

    private double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return 0.0;
        }
        String cleaned = priceStr.replace("$", "").replace(",", "").trim();
        return Double.parseDouble(cleaned);
    }

    private Electronic createElectronicFromForm() {
        return new Electronic(
                getNameInput(),
                getDescriptionInput(),
                getQuantityInput(),
                parsePrice(getPriceInput()),
                getPurchaseDateInput(),
                getVendorInput(),
                getLocationInput(),
                getWarrantyInput(),
                getBrandInput(),
                getModelInput(),
                getMaintenanceNeeded(),
                getLMDInput()
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

        if (getBrandInput().trim().isEmpty()) {
            showError("Brand cannot be empty!");
            return false;
        }

        if (getModelInput().trim().isEmpty()) {
            showError("Model cannot be empty!");
            return false;
        }

        if (validateDateField(getWarrantyInput(), "Warranty Date")) return false;
        if (validateDateField(getPurchaseDateInput(), "Purchase Date")) return false;
        if (validateDateField(getLMDInput(), "Last Maintenance Date")) return false;

        if (isFutureDate(getLMDInput())) {
            showError("Last Maintenance Date cannot be a future date");
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
                Electronic electronic = createElectronicFromForm();
                inventoryManager.addItem(electronic);
                loadItems();
                clearForm();
                showSuccess("Electronic item added successfully!");
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
                    Electronic electronic = createElectronicFromForm();
                    List<Item> allItems = inventoryManager.getAllItems();
                    List<Item> electronicsItems = inventoryManager.getItemsByCategory(Category.ELECTRONICS);

                    if (selectedIndex < electronicsItems.size()) {
                        Item originalItem = electronicsItems.get(selectedIndex);
                        int actualIndex = allItems.indexOf(originalItem);
                        if (actualIndex != -1) {
                            inventoryManager.updateItem(actualIndex, electronic);
                            loadItems();
                            clearForm();
                            selectedIndex = -1;
                            showSuccess("Electronic item updated successfully!");
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
                        "Are you sure you want to remove this electronic item?",
                        "Confirm Removal", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    List<Item> electronicsItems = inventoryManager.getItemsByCategory(Category.ELECTRONICS);

                    if (selectedIndex < electronicsItems.size()) {
                        Item itemToRemove = electronicsItems.get(selectedIndex);
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
            List<Item> electronicsItems = inventoryManager.getItemsByCategory(Category.ELECTRONICS);

            if (selectedRow < electronicsItems.size()) {
                Electronic electronic = (Electronic) electronicsItems.get(selectedRow);
                populateForm(electronic);
            }
        } catch (Exception e) {
            showError("Error loading item data: " + e.getMessage());
        }
    }

    private void populateForm(Electronic electronic) {
        setNameInput(electronic.getName());
        setQuantityInput(electronic.getQuantity());
        setVendorInput(electronic.getVendor());
        setPriceInput(String.valueOf(electronic.getPurchasePrice()));
        setPurchaseDateInput(electronic.getPurchaseDate());
        setWarrantyInput(electronic.getWarrantyPeriod());
        setModelInput(electronic.getModel());
        setBrandInput(electronic.getBrand());
        setDescriptionInput(electronic.getDescription());
        setLocationInput(electronic.getLocation());
        setMaintenanceNeeded(electronic.getMaintenanceNeeded());
        setLMDInput(electronic.getLastMaintenanceDate());
    }
}