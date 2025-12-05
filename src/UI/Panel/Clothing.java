package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Clothing {
    private JPanel panel1;
    private JPanel panel2;
    private JTextField name_field;
    private JTextField quantity_field;
    private JTextField vendor_field;
    private JTextField price_field;
    private JTextField condition_field;
    private JTextField fabrictype_field;
    private JTextField size_field;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton REMOVEButton;
    private JTable table1;
    private JComboBox location_combobox;
    private JTextArea textArea1;
    private JPanel Clothes_panel;
    private JPanel panel;
    private JPanel table_panel;
    private JLabel Clothes_label;
    private JLabel name_label;
    private JLabel quantity_label;
    private JLabel location_label;
    private JLabel vendor_label;
    private JLabel price_label;
    private JLabel condition_label;
    private JLabel fabrictype_label;
    private JLabel size_label;

    public Clothing(){
        createTable();
    }

    public JPanel getRootPanelClothing(){
        return panel1;
    }

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
