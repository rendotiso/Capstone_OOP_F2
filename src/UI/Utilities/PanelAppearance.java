package UI.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PanelAppearance extends JPanel {
    protected JPanel panelist, rootPanel, title_panel, panel, table_panel, description_panel, panelButton;
    protected JTextField name_field, vendor_field, price_field, purchaseDate_field;
    protected JTextArea description_textarea;
    protected JLabel title_label, name_label, quantity_label, location_label,
            vendor_label, price_label, description_label, purchaseDate_label;
    protected JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton, REFRESHButton;
    protected JComboBox<String> location_combobox;
    protected ItemTable itemTable;
    protected JScrollPane textAreaScroll;
    protected JSpinner quantity_spinner;
    protected int selectedIndex = -1;

    protected static final String DATE_PLACEHOLDER = "MM/DD/YYYY";

    public PanelAppearance() {
        initComponents();
        setupLayout();
        setupAppearance();
        setupPlaceholders();
    }



    protected void initComponents() {
        panelist = new JPanel();
        rootPanel = new JPanel();
        title_panel = new JPanel();
        panel = new JPanel();
        table_panel = new JPanel();
        description_panel = new JPanel();
        panelButton = new JPanel();

        name_field = new JTextField(8);
        vendor_field = new JTextField(8);
        price_field = new JTextField(8);
        purchaseDate_field = new JTextField(8);

        description_textarea = new JTextArea(3, 15);
        description_textarea.setLineWrap(true);
        description_textarea.setWrapStyleWord(true);

        quantity_spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        customizeSpinner();

        title_label = new JLabel();
        name_label = new JLabel("NAME:");
        quantity_label = new JLabel("QUANTITY:");
        location_label = new JLabel("LOCATION:");
        vendor_label = new JLabel("VENDOR:");
        price_label = new JLabel("PRICE:");
        purchaseDate_label = new JLabel("PURCHASE DATE:");
        description_label = new JLabel("DESCRIPTION/NOTE:");

        ADDButton = new JButton("ADD");
        CLEARButton = new JButton("CLEAR");
        UPDATEButton = new JButton("UPDATE");
        REMOVEButton = new JButton("REMOVE");
        REFRESHButton = new JButton("REFRESH");

        location_combobox = new JComboBox<>(new String[]{
                "STORAGE BOX", "WARDROBE", "UNDERBED STORAGE", "CLOSET",
                "DRAWER CLOSET", "HANGING RACK"
        });

        textAreaScroll = new JScrollPane(description_textarea);

        String[] columnNames = {"Name", "Qty", "Location", "PDate", "Price", "Details"};
        itemTable = new ItemTable(columnNames);
    }

    private void customizeSpinner() {
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(quantity_spinner, "#");
        quantity_spinner.setEditor(editor);

        JComponent editorComp = quantity_spinner.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }

    protected void setupLayout() {
        setLayout(new BorderLayout());
        panelist.setLayout(new BorderLayout());

        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        title_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        title_panel.add(title_label);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        rootPanel.add(title_panel, gbc);

        panel.setLayout(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 5, 5, 5);
        formGbc.anchor = GridBagConstraints.WEST;

        int row = setupFormLayout(panel, formGbc);

        setupDescriptionPanel(row, formGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(panel, gbc);

        table_panel.setLayout(new BorderLayout());
        table_panel.add(itemTable, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        rootPanel.add(table_panel, gbc);

        panelist.add(rootPanel, BorderLayout.CENTER);
        add(panelist, BorderLayout.CENTER);
    }

    protected int setupFormLayout(JPanel panel, GridBagConstraints formGbc) {
        int row = 0;

        addFormRow(panel, formGbc, name_label, name_field, row++);
        addFormRow(panel, formGbc, quantity_label, quantity_spinner, row++);
        addFormRow(panel, formGbc, location_label, location_combobox, row++);
        addFormRow(panel, formGbc, vendor_label, vendor_field, row++);
        addFormRow(panel, formGbc, price_label, price_field, row++);
        addFormRow(panel, formGbc, purchaseDate_label, purchaseDate_field, row++);

        return row;
    }

    protected void addFormRow(JPanel panel, GridBagConstraints formGbc, JLabel label, JComponent field, int row) {
        formGbc.gridx = 0;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        panel.add(label, formGbc);

        formGbc.gridx = 1;
        formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        field.setPreferredSize(new Dimension(80, 25));
        panel.add(field, formGbc);
    }

    protected void setupDescriptionPanel(int row, GridBagConstraints formGbc) {
        description_panel.setLayout(new GridBagLayout());
        GridBagConstraints descGbc = new GridBagConstraints();
        descGbc.insets = new Insets(5, 5, 5, 5);

        descGbc.gridx = 0; descGbc.gridy = 0;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.HORIZONTAL;
        description_panel.add(description_label, descGbc);

        descGbc.gridx = 0; descGbc.gridy = 1;
        descGbc.gridwidth = 1;
        descGbc.fill = GridBagConstraints.BOTH;
        descGbc.weighty = 1.0;
        textAreaScroll.setPreferredSize(new Dimension(200, 80));
        description_panel.add(textAreaScroll, descGbc);

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

        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.gridwidth = 2;
        formGbc.fill = GridBagConstraints.BOTH;
        formGbc.weighty = 1.0;
        panel.add(description_panel, formGbc);
    }

    protected void setupAppearance() {
        Color header = new Color(0x4682B4);
        Color black = new Color(-16777216);
        Color bg = new Color(0xF5F5F5);
        Color placeholderColor = new Color(100, 100, 100, 180);
        Color buttonColor = new Color(70, 130, 180);

        setPanelsOpaque();

        panelist.setBackground(bg);
        rootPanel.setBackground(bg);
        title_panel.setBackground(header);
        panel.setBackground(bg);
        panelButton.setBackground(bg);
        table_panel.setBackground(bg);
        description_panel.setBackground(bg);

        setComponentBackgrounds(bg);
        setComponentForegrounds(black, placeholderColor);
        title_label.setForeground(Color.WHITE);
        setLabelColors(black);
        setButtonColors(buttonColor);
        setFonts();
        customizeSpinnerAppearance(bg, black);
    }

    protected void setPanelsOpaque() {
        panelist.setOpaque(true);
        rootPanel.setOpaque(true);
        title_panel.setOpaque(true);
        panel.setOpaque(true);
        table_panel.setOpaque(true);
        description_panel.setOpaque(true);
        panelButton.setOpaque(true);
    }

    protected void setComponentBackgrounds(Color bg) {
        name_field.setBackground(bg);
        vendor_field.setBackground(bg);
        price_field.setBackground(bg);
        purchaseDate_field.setBackground(bg);
        description_textarea.setBackground(bg);
        location_combobox.setBackground(bg);
        quantity_spinner.setBackground(bg);
    }

    protected void setComponentForegrounds(Color black, Color placeholderColor) {
        name_field.setForeground(black);
        vendor_field.setForeground(black);
        price_field.setForeground(black);
        purchaseDate_field.setForeground(placeholderColor);
        description_textarea.setForeground(black);
        location_combobox.setForeground(black);
    }

    protected void setLabelColors(Color black) {
        name_label.setForeground(black);
        quantity_label.setForeground(black);
        location_label.setForeground(black);
        vendor_label.setForeground(black);
        price_label.setForeground(black);
        purchaseDate_label.setForeground(black);
        description_label.setForeground(black);
    }

    protected void setButtonColors(Color buttonColor) {
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

        makeButtonsOpaque();
        disableButtonFocus();
    }

    protected void makeButtonsOpaque() {
        ADDButton.setOpaque(true);
        CLEARButton.setOpaque(true);
        UPDATEButton.setOpaque(true);
        REMOVEButton.setOpaque(true);
        REFRESHButton.setOpaque(true);
    }

    protected void disableButtonFocus() {
        ADDButton.setFocusable(false);
        UPDATEButton.setFocusable(false);
        REMOVEButton.setFocusable(false);
        CLEARButton.setFocusable(false);
        REFRESHButton.setFocusable(false);
    }

    protected void setFonts() {
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
        Font placeholderFont = new Font("Segoe UI", Font.ITALIC, 13);

        title_label.setFont(titleFont);
        name_label.setFont(labelFont);
        quantity_label.setFont(labelFont);
        location_label.setFont(labelFont);
        vendor_label.setFont(labelFont);
        price_label.setFont(labelFont);
        purchaseDate_label.setFont(labelFont);
        description_label.setFont(labelFont);

        name_field.setFont(fieldFont);
        vendor_field.setFont(fieldFont);
        price_field.setFont(fieldFont);
        purchaseDate_field.setFont(placeholderFont);
        description_textarea.setFont(fieldFont);
        location_combobox.setFont(fieldFont);

        ADDButton.setFont(buttonFont);
        CLEARButton.setFont(buttonFont);
        UPDATEButton.setFont(buttonFont);
        REMOVEButton.setFont(buttonFont);
        REFRESHButton.setFont(buttonFont);
    }

    protected void customizeSpinnerAppearance(Color bg, Color black) {
        JComponent editorComp = quantity_spinner.getEditor();
        if (editorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editorComp).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }
        quantity_spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    protected void setupPlaceholders() {
        purchaseDate_field.setText(DATE_PLACEHOLDER);
        purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
        purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        purchaseDate_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (purchaseDate_field.getText().equals(DATE_PLACEHOLDER)) {
                    purchaseDate_field.setText("");
                    purchaseDate_field.setForeground(Color.BLACK);
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (purchaseDate_field.getText().isEmpty()) {
                    purchaseDate_field.setText(DATE_PLACEHOLDER);
                    purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
                    purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                }
            }
        });
    }

    // GETTERS
    public String getNameInput() {
        return name_field.getText();
    }
    public int getQuantityInput() {
        return (int) quantity_spinner.getValue();
    }
    public String getVendorInput() {
        return vendor_field.getText();
    }
    public String getPriceInput() {
        return price_field.getText();
    }
    public String getPurchaseDateInput() {
        String text = purchaseDate_field.getText();
        if (text.equals(DATE_PLACEHOLDER)) {
            return "";
        }
        return text;
    }

    public String getDescriptionInput() {
        return description_textarea.getText();
    }
    public String getLocationInput() {
        return (String) location_combobox.getSelectedItem();
    }

    // SETTERS
    public void setNameInput(String name) {
        name_field.setText(name);
    }
    public void setQuantityInput(int quantity) {
        quantity_spinner.setValue(quantity);
    }
    public void setVendorInput(String vendor) {
        vendor_field.setText(vendor);
    }
    public void setPriceInput(String price) {
        price_field.setText(price);
    }

    public void setPurchaseDateInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            purchaseDate_field.setText(DATE_PLACEHOLDER);
            purchaseDate_field.setForeground(new Color(100, 100, 100, 180));
            purchaseDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        } else {
            purchaseDate_field.setText(date);
            purchaseDate_field.setForeground(Color.BLACK);
            purchaseDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    public void setDescriptionInput(String description) {
        description_textarea.setText(description);
    }

    public void setLocationInput(String location) {
        location_combobox.setSelectedItem(location);
    }

    public void setTitle(String title) {
        title_label.setText(title);
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
    protected void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void clearForm() {
        name_field.setText("");
        vendor_field.setText("");
        price_field.setText("");
        description_textarea.setText("");
        quantity_spinner.setValue(1);
        setPurchaseDateInput("");
        if (itemTable != null) {
            itemTable.clearSelection();
        }
    }

    protected void setFieldToPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(new Color(100, 100, 100, 180));
        field.setFont(new Font("Segoe UI", Font.ITALIC, 13));
    }
}