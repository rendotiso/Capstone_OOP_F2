package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;
import java.awt.*;
import java.awt.event.ActionListener;

public class Food extends JPanel{

        // ATTRIBUTES
        private JPanel panelist,  rootPanel, food_panel, panel, table_panel, description_panel;
        private JTextField name_field, quantity_field, vendor_field,
                price_field, warranty_field, expiredate_field;
        private JTextArea textArea1;
        private JLabel food_label, name_label, quantity_label,
                location_label, vendor_label, price_label, warranty_label, expireydate_label,
                perish_label, cannedgoods_label, description_label;
        private JButton ADDButton, CLEARButton, UPDATEButton, REMOVEButton;
        private JComboBox<String> location_combobox, perish_combobox, cannedgoods_combobox;
        private JTable table1;
        private JScrollPane scrollPane;

        public Food() {
            initComponents();
            setupLayout();
            setupAppearance();
            createTable();
        }

    //SETTERS FOR NAME MUST IMPLMENET

    private void initComponents() {
            // Initialize panels
            panelist = new JPanel();
            rootPanel = new JPanel();
            food_panel = new JPanel();
            panel = new JPanel();
            table_panel = new JPanel();
            description_panel = new JPanel();

            // Initialize form fields
            name_field = new JTextField(20);
            quantity_field = new JTextField(10);
            vendor_field = new JTextField(20);
            price_field = new JTextField(10);
            warranty_field = new JTextField(15);
            expiredate_field = new JTextField(15);
            textArea1 = new JTextArea(3, 30);

            // Initialize labels
            food_label = new JLabel("FOOD");
            name_label = new JLabel("NAME:");
            quantity_label = new JLabel("QUANTITY:");
            location_label = new JLabel("LOCATION:");
            vendor_label = new JLabel("VENDOR:");
            price_label = new JLabel("PRICE:");
            warranty_label = new JLabel("WARRANTY:");
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

            perish_combobox = new JComboBox<>(new String[]{"YES", "NO"});
            cannedgoods_combobox = new JComboBox<>(new String[]{"YES", "NO"});
            table1 = new JTable();
            scrollPane = new JScrollPane(table1);
//            textArea1.setLineWrap(true);
//            textArea1.setWrapStyleWord(true);
        }

        private void setupLayout() {
            // Main panel setup
            setLayout(new BorderLayout());
            panelist.setLayout(new BorderLayout());
            panelist.setPreferredSize(new Dimension(1000, 860));

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
            panel.add(name_label, formGbc);

            formGbc.gridx = 1; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.HORIZONTAL;
            formGbc.weightx = 1.0;
            name_field.setPreferredSize(new Dimension(150, 25));
            panel.add(name_field, formGbc);

            row++;

            // Row 1: Quantity
            formGbc.gridx = 0; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.NONE;
            formGbc.weightx = 0;
            panel.add(quantity_label, formGbc);

            formGbc.gridx = 1; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.HORIZONTAL;
            formGbc.weightx = 1.0;
            quantity_field.setPreferredSize(new Dimension(150, 25));
            panel.add(quantity_field, formGbc);

            row++;

            // Row 2: Location
            formGbc.gridx = 0; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.NONE;
            formGbc.weightx = 0;
            panel.add(location_label, formGbc);

            formGbc.gridx = 1; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.HORIZONTAL;
            formGbc.weightx = 1.0;
            location_combobox.setPreferredSize(new Dimension(150, 25));
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
            vendor_field.setPreferredSize(new Dimension(150, 25));
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
            price_field.setPreferredSize(new Dimension(150, 25));
            panel.add(price_field, formGbc);

            row++;

            // Row 5: Warranty
            formGbc.gridx = 0; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.NONE;
            formGbc.weightx = 0;
            panel.add(warranty_label, formGbc);

            formGbc.gridx = 1; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.HORIZONTAL;
            formGbc.weightx = 1.0;
            warranty_field.setPreferredSize(new Dimension(150, 25));
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
            expiredate_field.setPreferredSize(new Dimension(150, 25));
            panel.add(expiredate_field, formGbc);

            row++;

            // Row 7: Perish
            formGbc.gridx = 0; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.NONE;
            formGbc.weightx = 0;
            panel.add(perish_label, formGbc);

            formGbc.gridx = 1; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.HORIZONTAL;
            formGbc.weightx = 1.0;
            perish_combobox.setPreferredSize(new Dimension(150, 25));
            panel.add(perish_combobox, formGbc);

            row++;

            // Row 8: Canned Good
            formGbc.gridx = 0; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.NONE;
            formGbc.weightx = 0;
            panel.add(cannedgoods_label, formGbc);

            formGbc.gridx = 1; formGbc.gridy = row;
            formGbc.fill = GridBagConstraints.HORIZONTAL;
            formGbc.weightx = 1.0;
            cannedgoods_combobox.setPreferredSize(new Dimension(150, 25));
            panel.add(cannedgoods_combobox, formGbc);

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
            JScrollPane textAreaScroll = new JScrollPane(textArea1);
            textAreaScroll.setPreferredSize(new Dimension(510, 80));
            description_panel.add(textAreaScroll, descGbc);

            // Buttons panel
            descGbc.gridx = 0; descGbc.gridy = 2;
            descGbc.gridwidth = 1;
            descGbc.fill = GridBagConstraints.NONE;
            descGbc.weighty = 0;
            descGbc.anchor = GridBagConstraints.CENTER;

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.add(ADDButton);
            buttonPanel.add(CLEARButton);
            buttonPanel.add(UPDATEButton);
            buttonPanel.add(REMOVEButton);
            description_panel.add(buttonPanel, descGbc);

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
            rootPanel.add(table_panel, gbc);

            // Add root panel to panel1
            panelist.add(rootPanel, BorderLayout.CENTER);

            // Add panel1 to main panel
            add(panelist, BorderLayout.CENTER);
        }

        private void setupAppearance() {
            // Set background colors (RGB values from .form)
            // -7618067 = Light blue-gray
            // -4660737 = Darker blue
            // -2102785 = Light blue
            // -16777216 = Black
            // -16448251 = White

            Color lightBlueGray = new Color(-7618067);
            Color darkBlue = new Color(-4660737);
            Color lightBlue = new Color(-2102785);
            Color black = new Color(-16777216);
            Color white = new Color(-16448251);

            panelist.setBackground(lightBlueGray);
            rootPanel.setBackground(lightBlueGray);
            food_panel.setBackground(darkBlue);
            panel.setBackground(darkBlue);
            table_panel.setBackground(lightBlueGray);
            description_panel.setBackground(darkBlue);

            // Set text field backgrounds
            name_field.setBackground(lightBlue);
            quantity_field.setBackground(lightBlue);
            vendor_field.setBackground(lightBlue);
            price_field.setBackground(lightBlue);
            warranty_field.setBackground(lightBlue);
            expiredate_field.setBackground(lightBlue);
            textArea1.setBackground(lightBlue);
            location_combobox.setBackground(lightBlue);
            perish_combobox.setBackground(lightBlue);
            cannedgoods_combobox.setBackground(lightBlue);

            // Set foreground colors
            name_field.setForeground(black);
            quantity_field.setForeground(black);
            vendor_field.setForeground(black);
            price_field.setForeground(black);
            warranty_field.setForeground(black);
            expiredate_field.setForeground(black);
            textArea1.setForeground(black);
            location_combobox.setForeground(white);
            perish_combobox.setForeground(black);
            cannedgoods_combobox.setForeground(black);

            // Set label colors
            food_label.setForeground(black);
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
            ADDButton.setBackground(lightBlueGray);
            CLEARButton.setBackground(lightBlueGray);
            UPDATEButton.setBackground(lightBlueGray);
            REMOVEButton.setBackground(lightBlueGray);
            ADDButton.setForeground(black);
            CLEARButton.setForeground(black);
            UPDATEButton.setForeground(black);
            REMOVEButton.setForeground(black);

            // Set fonts
            Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
            Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
            Font titleFont = new Font("Segoe UI", Font.BOLD, 18);

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
            quantity_field.setFont(fieldFont);
            vendor_field.setFont(fieldFont);
            price_field.setFont(fieldFont);
            warranty_field.setFont(fieldFont);
            expiredate_field.setFont(fieldFont);
            textArea1.setFont(fieldFont);
            location_combobox.setFont(fieldFont);
            perish_combobox.setFont(fieldFont);
            cannedgoods_combobox.setFont(fieldFont);

            // Set button fonts
            Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);
            ADDButton.setFont(buttonFont);
            CLEARButton.setFont(buttonFont);
            UPDATEButton.setFont(buttonFont);
            REMOVEButton.setFont(buttonFont);
        }

       // METHODS AND ACTION LISTENERSS

        public void clearForm() {
            name_field.setText("");
            quantity_field.setText("");
            vendor_field.setText("");
            price_field.setText("");
            warranty_field.setText("");
            expiredate_field.setText("");
            textArea1.setText("");
            location_combobox.setSelectedIndex(0);
            perish_combobox.setSelectedIndex(0);
            cannedgoods_combobox.setSelectedIndex(0);
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

        // Check if form has required fields
//        public boolean validateForm() {
//            if (getNameInput().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Name is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            if (getQuantityInput() <= 0) {
//                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!", "Validation Error", JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            if (getPriceInput() <= 0) {
//                JOptionPane.showMessageDialog(this, "Price must be greater than 0!", "Validation Error", JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            if (getExpiryDateInput().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Expiry Date is required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            return true;
//        }
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
    }
}
