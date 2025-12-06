package UI;

import UI.Panel.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    // attributes
    private JButton homeButton, toolsButton, electronicsButton,
            miscellaneousButton, clothingButton, foodButton,
            helpButton, exitButton;
    private JPanel Panel, cardlayout;
    private CardLayout cardLayoutManager;

    public Dashboard() {
        setContentPane(Panel);
        setTitle("Home Inventory Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1300, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        setupCardLayout();
        addPanels();
        setupListeners();
        setVisible(true);
    }

    private void setupCardLayout() {
        cardLayoutManager = new CardLayout();
        cardlayout.setLayout(cardLayoutManager);
    }

    private void addPanels() {
        AllItemsPanel viewAllPanel = new AllItemsPanel();
        Clothing clothingPanel = new Clothing();
        Electronics electronicsPanel = new Electronics();
        Food foodPanel = new Food();
        Tools toolsPanel = new Tools();
        Misc miscPanel = new Misc();

        cardlayout.add(viewAllPanel, "VIEW_ALL");
        cardlayout.add(clothingPanel, "CLOTHING");
        cardlayout.add(electronicsPanel, "ELECTRONICS");
        cardlayout.add(foodPanel, "FOOD");
        cardlayout.add(toolsPanel, "TOOLS");
        cardlayout.add(miscPanel, "MISCELLANEOUS");
        cardLayoutManager.show(cardlayout, "VIEW_ALL");
    }
    private void setupListeners() {
        homeButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "VIEW_ALL"));
        homeButton.setFocusable(false);
        clothingButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "CLOTHING"));
        clothingButton.setFocusable(false);
        electronicsButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "ELECTRONICS"));
        electronicsButton.setFocusable(false);
        foodButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "FOOD"));
        foodButton.setFocusable(false);
        toolsButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "TOOLS"));
        toolsButton.setFocusable(false);
        miscellaneousButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "MISCELLANEOUS"));
        miscellaneousButton.setFocusable(false);

        // bottom buttons
        helpButton.addActionListener(e -> showHelp());
        helpButton.setFocusable(false);
        exitButton.addActionListener(e->System.exit(0));
        exitButton.setFocusable(false);
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(this,
                "<html><body style='width: 300px;'>" +
                        "<h3>Home Inventory Manager Help</h3>" +
                        "<p><b>How to use:</b></p>" +
                        "<ul>" +
                        "<li>Select a category from the left menu</li>" +
                        "<li>Add new items using the form</li>" +
                        "<li>Click on items in the table to edit</li>" +
                        "<li>Use 'View All' to search all items</li>" +
                        "</ul>" +
                        "<p><b>Features:</b></p>" +
                        "<ul>" +
                        "<li>Track expiration dates for food</li>" +
                        "<li>Monitor maintenance schedules</li>" +
                        "<li>Calculate item values</li>" +
                        "<li>Auto-save to file</li>" +
                        "</ul>" +
                        "</body></html>",
                "Help Guide",
                JOptionPane.INFORMATION_MESSAGE);
    }
}