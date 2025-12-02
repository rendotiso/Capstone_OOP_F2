package UI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame{
    //attributes
    private JButton homeButton;
    private JButton toolsButton;
    private JButton electronicsButton;
    private JButton miscellaneousButton;
    private JButton clothingButton;
    private JButton foodButton;
    private JPanel Panel;
    private JLabel organizerLabel;
    private JPanel Panel2;

    //panels

    private JPanel btn1;
    private Clothing btn2;
    private Electronics btn3;
    private Food btn4;
    private Misc btn5;
    private Tools btn6;
    private JPanel currentPanel;

    //constructor
    public Dashboard(){
        setContentPane(Panel);
        setTitle("Organizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1510,900);
        setLocationRelativeTo(null);
        setResizable(false);
        panels();
        setters();
        listeners();
        setVisible(true);
    }

    //setters
    private void setters(){
        homeButton.setName("homeButton");
        toolsButton.setName("tollsButton");
        electronicsButton.setName("electronicsButton");
        miscellaneousButton.setName("miscellaneousButton");
        clothingButton.setName("clothingButton");
        foodButton.setName("foodButton");
        organizerLabel.setName("organizerLabel");
    }

    private void panels(){
        btn1 = new AllItemsPanel();
        btn2 = new Clothing();
        btn3 = new Electronics();
        btn4 = new Food();
        btn5 = new Misc();
        btn6 = new Tools();

//        showAllItemsPanel();
    }


    //Listeners
    private void listeners(){
        homeButton.addActionListener(new ActionListener() { @Override
            public void actionPerformed(ActionEvent e) {
            showAllItemsPanel();
            }
        });

    }

    private void showAllItemsPanel() {
        switchPanel(btn1);
        setButtonActive(homeButton);
    }

    private void switchPanel(JPanel newPanel) {
        if (currentPanel != null) {
            Panel.remove(currentPanel);
        }
        currentPanel = newPanel;
        Panel.add(currentPanel, BorderLayout.CENTER);
        Panel.revalidate();
        Panel.repaint();
    }

    private void setButtonActive(JButton activeButton) {
        // Reset all buttons to default appearance
        JButton[] buttons = {homeButton, toolsButton, electronicsButton, miscellaneousButton, clothingButton, foodButton};
        for (JButton button : buttons) {
            button.setBackground(null);
            button.setForeground(Color.BLACK);
            button.setFont(button.getFont().deriveFont(Font.PLAIN));
        }

        // Set active button appearance
        activeButton.setBackground(new Color(70, 130, 180));
        activeButton.setForeground(Color.WHITE);
        activeButton.setFont(activeButton.getFont().deriveFont(Font.BOLD));
    }

}
