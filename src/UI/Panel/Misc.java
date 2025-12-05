package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Misc extends JFrame{
    private JPanel rootPanel;
    private JTextField name_field;
    private JTextField quantity_field;
    private JTextField vendor_field;
    private JTextField price_field;
    private JTextField warranty_field;
    private JTextField itemtype_field;
    private JTextField usage_field;
    private JComboBox condition_combobox;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton REMOVEButton;
    private JTable table1;
    private JTextArea textArea1;
    private JPanel miscellaneous_panel;
    private JLabel miscellaneous_label;
    private JPanel panel;
    private JComboBox location_combobox;
    private JPanel description_panel;
    private JLabel name_label;
    private JLabel quantity_label;
    private JLabel venodor_label;
    private JLabel price_label;
    private JLabel warranty_label;
    private JLabel itemtyep_label;
    private JLabel usage_label;
    private JLabel condition_label;
    private JLabel location_label;
    private JPanel table_panel;
    private JPanel panelist;

    public Misc(){
        createTable();
    }

    public JPanel getRootPanelMisc(){
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
