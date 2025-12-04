package UI;

import UI.Panel.*;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    // attributes
    private JButton homeButton, toolsButton, electronicsButton;
    private JButton miscellaneousButton, clothingButton, foodButton;
    private JPanel Panel, cardlayout;
    private JLabel organizerLabel;
    private CardLayout cardLayoutManager;

    public Dashboard() {
        setContentPane(Panel);
        setTitle("Home Inventory Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1435, 880);
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
        clothingButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "CLOTHING"));
        electronicsButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "ELECTRONICS"));
        foodButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "FOOD"));
        toolsButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "TOOLS"));
        miscellaneousButton.addActionListener(e -> cardLayoutManager.show(cardlayout, "MISCELLANEOUS"));
    }
}