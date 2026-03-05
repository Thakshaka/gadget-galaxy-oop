import ui.LoginGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the Gadget Galaxy application.
 */
public class Main {
    public static void main(String[] args) {
        // Apply Nimbus Look and Feel for a premium visual appearance
        setupModernLook();

        // Launch the Login Screen on the Event Dispatch Thread (Best Practice)
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }

    private static void setupModernLook() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Default to platform look if Nimbus fails
        }
    }
}
