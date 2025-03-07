package my.company.my.yogibear;

/**
 * Entry point for the Yogi Bear game application.
 * 
 * This class initializes and starts the game by creating the main game window
 * and setting it to be visible.
 */
public class YogiBear {

    /**
     * Main method that serves as the entry point for the application.
     * 
     * The main method uses the SwingUtilities.invokeLater method to ensure
     * that the GUI creation and updates are performed on the Event Dispatch
     * Thread (EDT), which is the recommended thread for Swing applications.
     * 
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameWindow gameWindow = new GameWindow(); // Create the main game window
            gameWindow.setVisible(true); // Display the game window
        });
    }
}
