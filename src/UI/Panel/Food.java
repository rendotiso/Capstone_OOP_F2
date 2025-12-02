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
    private JTextField textField8;
    private JComboBox comboBox1;
    private JTextArea helloTextArea;
    private JTable table1;
    private JPanel rootPanel;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton REMOVEButton;

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
