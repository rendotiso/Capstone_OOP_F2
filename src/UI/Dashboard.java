package UI;

import UI.Panel.*;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private JButton homeButton, toolsButton, electronicsButton,
            miscellaneousButton, clothingButton, foodButton,
            helpButton, exitButton;
    private JPanel Panel, cardlayout;
    private JLabel organizerLabel;
    private CardLayout cardLayoutManager;

    public Dashboard() {
        setContentPane(Panel);
        setTitle("Home Inventory Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1310, 820);
        setResizable(false);
        setLocationRelativeTo(null);

        setters();
        addPanels();
        setupListeners();
        setVisible(true);
    }

    private void setters() {
        cardLayoutManager = new CardLayout();
        cardlayout.setLayout(cardLayoutManager);

        homeButton.setName("homeButton");
        toolsButton.setName("toolsButton");
        electronicsButton.setName("electronicsButton");
        miscellaneousButton.setName("miscellaneousButton");
        clothingButton.setName("clothingButton");
        foodButton.setName("foodButton");
        Panel.setName("Panel");
        cardlayout.setName("cardlayout");
        organizerLabel.setName("organizerLabel");
    }

    private void addPanels() {
        AllItemsPanel viewAllPanel = new AllItemsPanel();
        ClothingPanel clothingPanel = new ClothingPanel();
        ElectronicsPanel electronicsPanel = new ElectronicsPanel();
        FoodPanel foodPanel = new FoodPanel();
        ToolsPanel toolsPanel = new ToolsPanel();
        MiscPanel miscPanel = new MiscPanel();

        cardlayout.add(viewAllPanel, "VIEW_ALL");
        cardlayout.add(clothingPanel, "CLOTHING");
        cardlayout.add(electronicsPanel, "ELECTRONICS");
        cardlayout.add(foodPanel, "FOOD");
        cardlayout.add(toolsPanel, "TOOLS");
        cardlayout.add(miscPanel, "MISCELLANEOUS");
        cardLayoutManager.show(cardlayout, "VIEW_ALL");
    }

    private void setupListeners() {
        homeButton.addActionListener(_ -> cardLayoutManager.show(cardlayout, "VIEW_ALL"));
        homeButton.setFocusable(false);
        clothingButton.addActionListener(_ -> cardLayoutManager.show(cardlayout, "CLOTHING"));
        clothingButton.setFocusable(false);
        electronicsButton.addActionListener(_ -> cardLayoutManager.show(cardlayout, "ELECTRONICS"));
        electronicsButton.setFocusable(false);
        foodButton.addActionListener(_ -> cardLayoutManager.show(cardlayout, "FOOD"));
        foodButton.setFocusable(false);
        toolsButton.addActionListener(_ -> cardLayoutManager.show(cardlayout, "TOOLS"));
        toolsButton.setFocusable(false);
        miscellaneousButton.addActionListener(_ -> cardLayoutManager.show(cardlayout, "MISCELLANEOUS"));
        miscellaneousButton.setFocusable(false);

        helpButton.addActionListener(_ -> showHelp());
        helpButton.setFocusable(false);
        exitButton.addActionListener(_ ->System.exit(0));
        exitButton.setFocusable(false);
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(this,
                "<html><body style='width: 300px;'>" +
                        "<p><b>How to use:</b></p>" +
                        "<ul>" +
                        "<li>Select a category from the left menu</li>" +
                        "<li>Add new items using the form</li>" +
                        "<li>Click on items in the table to edit</li>" +
                        "<li>Use 'View All' to search all items</li>" +
                        "<li>Use refresh button to update the tables from separate panels " +
                        "</ul>" +
                        "<p><b>Features:</b></p>" +
                        "<ul>" +
                        "<li>Track expiration dates for food</li>" +
                        "<li>Monitor maintenance schedules</li>" +
                        "<li>Calculate item values</li>" +
                        "<li>Auto-save to file</li>" +
                        "</ul>" +
                        "<p><b>Thank you for using this small application :) - Team Jewels<b></p>" +
                        "</body></html>",
                "Help Guide",
                JOptionPane.INFORMATION_MESSAGE);
    }
}