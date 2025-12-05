package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Tools extends JFrame{
    private JPanel rootPanel;
    private JTextField name_field;
    private JTextField quantity_field;
    private JTextField vendor_field;
    private JTextField price_field;
    private JTextField warranty_field;
    private JTextField tool_field;
    private JTextField material_field;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton REMOVEButton;
    private JTable table1;
    private JTextArea textArea1;
    private JComboBox location_combobox;
    private JPanel tools_panel;
    private JLabel tools_label;
    private JPanel panel;
    private JPanel description_panel;
    private JLabel description_label;
    private JLabel name_label;
    private JLabel quantity_label;
    private JLabel location_label;
    private JLabel vendor_label;
    private JLabel price_label;
    private JLabel warranty_label;
    private JLabel tooltype_label;
    private JLabel material_label;
    private JLabel requiresmaintenance_label;
    private JPanel table_panel;
    private JTextField textField1;
    private JPanel panelist;

    public Tools(){
        createTable();
    }

    public JPanel getRootPanelTools(){
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
