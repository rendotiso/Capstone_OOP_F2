package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Food {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JComboBox comboBox1;
    private JTable table1;
    private JPanel rootPanel;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton REMOVEButton;
    private JComboBox comboBox2;
    private JTextArea textArea1;

    public Food(){
        createTable();
    }

    public JPanel getRootPanel(){
        return rootPanel;
    }

    private void createTable(){
        Object[][] data = {
                {"Imong papa","Imong mama", "uwu", "uwu"},
                {"Akoang papa","Akoang mama", "uwu", "uwu"},
                {"Iyahang papa","Iyahang mama", "uwu", "uwu"}
            };
        table1.setModel(new DefaultTableModel(
                data,
                new String[]{"Title", "Year", "Rating", "Prices"}));
    }
}
