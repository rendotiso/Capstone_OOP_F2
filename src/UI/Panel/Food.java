package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Food extends JFrame{

    private JTextField name_field;
    private JTextField quantity_field;
    private JTextField vendor_field;
    private JTextField price_field;
    private JTextField warranty_field;
    private JTextField expiredate_field;
    private JComboBox cannedgoods_combobox;
    private JTable table1;
    private JPanel rootPanel;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton REMOVEButton;
    private JComboBox perish_combobox;
    private JTextArea textArea1;
    private JPanel food_panel;
    private JLabel food_label;
    private JPanel panel;
    private JComboBox location_combobox;
    private JPanel description_panel;
    private JLabel description_label;
    private JLabel name_label;
    private JLabel location_label;
    private JLabel quantity_label;
    private JLabel vendor_label;
    private JLabel price_label;
    private JLabel warranty_label;
    private JLabel perish_label;
    private JLabel expireydate_label;
    private JLabel cannedgoods_label;
    private JPanel table_panel;
    private JPanel panelist;

    public Food(){
        createTable();
    }

    public JPanel getRootPanelFood(){
        return panelist;
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
