package UI;

import javax.swing.*;

public class Dashboard extends JFrame{
    private JButton homeButton;
    private JButton toolsButton;
    private JButton electronicsButton;
    private JButton miscellaneousButton;
    private JButton clothingButton;
    private JButton foodButton;
    private JPanel Panel;
    private JLabel organizerLabel;

    public Dashboard(){
        setContentPane(Panel);
        setTitle("Organizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1510,900);
        setLocationRelativeTo(null);
        setResizable(false);
        setters();
        setVisible(true);

    }

    private void setters(){
        homeButton.setName("homeButton");
        toolsButton.setName("tollsButton");
        electronicsButton.setName("electronicsButton");
        miscellaneousButton.setName("miscellaneousButton");
        clothingButton.setName("clothingButton");
        foodButton.setName("foodButton");
        organizerLabel.setName("organizerLabel");
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
