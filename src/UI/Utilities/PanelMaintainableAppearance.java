package UI.Utilities;

import javax.swing.*;
import java.awt.*;

public class PanelMaintainableAppearance extends PanelAppearance {
    protected JTextField lastMaintenanceDate_field;
    protected JLabel lastMaintenanceDate_label, maintenanceNeeded_label;
    protected JCheckBox maintenanceNeededCheckBox;
    protected JSpinner maintenanceIntervalDateDays;
    protected JLabel maintenanceIntervalDateDays_label;
    protected JPanel radiopanel;
    protected static final String LAST_MAINTENANCE_PLACEHOLDER = "MM/DD/YYYY";

    public PanelMaintainableAppearance() {
        super();
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        lastMaintenanceDate_field = new JTextField(8);
        lastMaintenanceDate_label = new JLabel("LAST MAINTENANCE DATE:");
        maintenanceNeeded_label = new JLabel("MAINTENANCE NEEDED?");
        maintenanceNeededCheckBox = new JCheckBox();

        maintenanceIntervalDateDays = new JSpinner(new SpinnerNumberModel(30, 1, 365, 1));
        JSpinner.NumberEditor intervalEditor = new JSpinner.NumberEditor(maintenanceIntervalDateDays, "#");
        maintenanceIntervalDateDays.setEditor(intervalEditor);

        JComponent intervalEditorComp = maintenanceIntervalDateDays.getEditor();
        if (intervalEditorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) intervalEditorComp).getTextField();
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }

        maintenanceIntervalDateDays_label = new JLabel("MAINTENANCE INTERVAL (DAYS):");
        radiopanel = new JPanel();

        maintenanceNeededCheckBox.setFocusable(false);
    }

    @Override
    protected void setupLayout() {
        super.setupLayout();
    }

    protected int setupMaintainableFormLayout(JPanel panel, GridBagConstraints formGbc, int startRow) {
        int row = startRow;

        addMaintenanceNeededRow(panel, formGbc, row++);
        addMaintenanceIntervalRow(panel, formGbc, row++);
        addLastMaintenanceDateRow(panel, formGbc, row++);

        return row;
    }

    protected void addMaintenanceNeededRow(JPanel panel, GridBagConstraints formGbc, int row) {
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(maintenanceNeeded_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;

        radiopanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radiopanel.add(maintenanceNeededCheckBox);
        panel.add(radiopanel, formGbc);
    }

    protected void addMaintenanceIntervalRow(JPanel panel, GridBagConstraints formGbc, int row) {
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(maintenanceIntervalDateDays_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;
        maintenanceIntervalDateDays.setPreferredSize(new Dimension(80, 25));
        panel.add(maintenanceIntervalDateDays, formGbc);
    }

    protected void addLastMaintenanceDateRow(JPanel panel, GridBagConstraints formGbc, int row) {
        formGbc.gridx = 0; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.NONE;
        formGbc.weightx = 0;
        formGbc.anchor = GridBagConstraints.WEST;
        panel.add(lastMaintenanceDate_label, formGbc);

        formGbc.gridx = 1; formGbc.gridy = row;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.weightx = 1.0;
        lastMaintenanceDate_field.setPreferredSize(new Dimension(80, 25));
        panel.add(lastMaintenanceDate_field, formGbc);
    }

    @Override
    protected void setupAppearance() {
        super.setupAppearance();

        Color bg = new Color(0xF5F5F5);
        Color black = new Color(-16777216);

        lastMaintenanceDate_field.setBackground(bg);
        maintenanceIntervalDateDays.setBackground(bg);
        radiopanel.setBackground(bg);
        maintenanceNeededCheckBox.setBackground(bg);

        lastMaintenanceDate_field.setForeground(black);

        lastMaintenanceDate_label.setForeground(black);
        maintenanceNeeded_label.setForeground(black);
        maintenanceIntervalDateDays_label.setForeground(black);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font checkboxFont = new Font("Segoe UI", Font.BOLD, 12);

        lastMaintenanceDate_label.setFont(labelFont);
        maintenanceNeeded_label.setFont(labelFont);
        maintenanceIntervalDateDays_label.setFont(labelFont);

        lastMaintenanceDate_field.setFont(fieldFont);
        maintenanceIntervalDateDays.setFont(fieldFont);
        maintenanceNeededCheckBox.setFont(checkboxFont);

        JComponent intervalEditorComp = maintenanceIntervalDateDays.getEditor();
        if (intervalEditorComp instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) intervalEditorComp).getTextField();
            textField.setBackground(bg);
            textField.setForeground(black);
            textField.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }

    @Override
    protected void setupPlaceholders() {
        super.setupPlaceholders();

        lastMaintenanceDate_field.setText(LAST_MAINTENANCE_PLACEHOLDER);
        lastMaintenanceDate_field.setForeground(new Color(100, 100, 100, 180));
        lastMaintenanceDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        setupMaintenanceDateFocusListener();
    }

    protected void setupMaintenanceDateFocusListener() {
        lastMaintenanceDate_field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (lastMaintenanceDate_field.getText().equals(LAST_MAINTENANCE_PLACEHOLDER)) {
                    lastMaintenanceDate_field.setText("");
                    lastMaintenanceDate_field.setForeground(Color.BLACK);
                    lastMaintenanceDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (lastMaintenanceDate_field.getText().isEmpty()) {
                    lastMaintenanceDate_field.setText(LAST_MAINTENANCE_PLACEHOLDER);
                    lastMaintenanceDate_field.setForeground(new Color(100, 100, 100, 180));
                    lastMaintenanceDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                }
            }
        });
    }

    // GETTERS
    public String getLastMaintenanceDateInput() {
        String text = lastMaintenanceDate_field.getText();
        if (text.equals(LAST_MAINTENANCE_PLACEHOLDER)) {
            return "";
        }
        return text;
    }
    public boolean getMaintenanceNeeded() {
        return maintenanceNeededCheckBox.isSelected();
    }
    public int getMaintenanceIntervalDays() {
        return (int) maintenanceIntervalDateDays.getValue();
    }

    // SETTERS
    public void setLastMaintenanceDateInput(String date) {
        if (date == null || date.trim().isEmpty()) {
            lastMaintenanceDate_field.setText(LAST_MAINTENANCE_PLACEHOLDER);
            lastMaintenanceDate_field.setForeground(new Color(100, 100, 100, 180));
            lastMaintenanceDate_field.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        } else {
            lastMaintenanceDate_field.setText(date);
            lastMaintenanceDate_field.setForeground(Color.BLACK);
            lastMaintenanceDate_field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }
    }
    public void setMaintenanceNeeded(boolean needsMaintenance) {
        maintenanceNeededCheckBox.setSelected(needsMaintenance);
        updateMaintenanceAppearance(needsMaintenance);
    }
    public void setMaintenanceIntervalDays(int days) {
        maintenanceIntervalDateDays.setValue(days);
    }

    //METHODS
    protected void updateMaintenanceAppearance(boolean needsMaintenance) {
        if (needsMaintenance) {
            lastMaintenanceDate_field.setBackground(new Color(0xFFE5E5));
            lastMaintenanceDate_label.setForeground(new Color(0xFF0000));
            lastMaintenanceDate_label.setText("LAST MAINTENANCE DATE: (URGENT!)");
        } else {
            lastMaintenanceDate_field.setBackground(new Color(0xF5F5F5));
            lastMaintenanceDate_label.setForeground(Color.BLACK);
            lastMaintenanceDate_label.setText("LAST MAINTENANCE DATE:");
        }
    }
    protected void setupMaintenanceListener() {
        maintenanceNeededCheckBox.addChangeListener(_ -> {
            boolean selected = maintenanceNeededCheckBox.isSelected();
            updateMaintenanceAppearance(selected);
        });
    }
    @Override
    public void clearForm() {
        super.clearForm();
        setMaintenanceNeeded(false);
        setLastMaintenanceDateInput("");
        setMaintenanceIntervalDays(30);
    }
}