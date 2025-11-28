package UI.Panel;

import javax.swing.*;

public class Electronics extends JFrame {

    private JTable table1;
    private JTextArea testTextArea;
    private JPanel Panel;

    public Electronics() {
        setContentPane(Panel);
        setTitle("HomeScreen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        setters();
        setVisible(true);
        listeners();
    }

    private void setters() {


    }

    private void listeners() {


    }
}
