import Model.Entities.Electronic;
import UI.Dashboard;
import UI.Panel.Clothing;
import UI.Panel.Electronics;
import UI.Panel.Food;
import UI.Panel.Misc;
import UI.Panel.Tools;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // TRY CATCH FOR MAIN, will try the GUI Dashboard, and catch any errors to run application.
        SwingUtilities.invokeLater(() -> {
            try {
                new Dashboard();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error starting application: " + e.getMessage(),
                        "Startup Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //AYAW KUHAA ANG MOKUHA KUHAAN UG ITLOG
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createClothing();
            }
        });


    }

    //AYAW SD NI KUSION JD NA NAKO INYOHANG GITI
    private static void createClothing(){
        Clothing ui = new Clothing();
        JPanel root = ui.getRootPanelClothing();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static void createElectronics(){
        Electronics ui = new Electronics();
        JPanel root = ui.getRootPanelElectronics();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static void createFood(){
        Food ui = new Food();
        JPanel root = ui.getRootPanelFood();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static void createMisc(){
        Misc ui = new Misc();
        JPanel root = ui.getRootPanelMisc();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static void createTools(){
        Tools ui = new Tools();
        JPanel root = ui.getRootPanelTools();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
