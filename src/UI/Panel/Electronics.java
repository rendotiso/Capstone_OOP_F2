package UI.Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Electronics extends JFrame {

    private JPanel Panel;
    private JPanel rootPanel;
    private JTextField name_field;
    private JTextField quantity_field;
    private JTextField vendor_field;
    private JTextField price_field;
    private JTextField warranty_field;
    private JTextField model_field;
    private JTextField brand_field;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton REMOVEButton;
    private JTable table1;
    private JComboBox location_combobox;
    private JTextField lastmaintenancedate_field;
    private JTextArea textArea1;
    private JPanel ELECT_PANEL;
    private JLabel ELECTRONICS_LABEL;
    private JPanel panel1;
    private JPanel description_panel;
    private JPanel table_panel;
    private JLabel name_label;
    private JLabel quantity_label;
    private JLabel location_label;
    private JLabel vendor_label;
    private JLabel price_label;
    private JLabel warranty_label;
    private JLabel model_label;
    private JLabel brand_label;
    private JLabel LMD_label;

    public Electronics() {
//        setContentPane(Panel);
//        setTitle("HomeScreen");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(600, 500);
//        setLocationRelativeTo(null);
//
        setters();
//        setVisible(true);
        listeners();
        createTable();
    }

    public JPanel getRootPanelElectronics(){
        return Panel;
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

    private void setters() {


    }

    private void listeners() {


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
