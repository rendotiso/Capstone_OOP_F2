import UI.Dashboard;
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
    }
}
