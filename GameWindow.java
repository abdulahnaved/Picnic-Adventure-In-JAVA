package my.company.my.yogibear;

import javax.swing.*;

/**
 * Represents the main window of the Yogi Bear game.
 * This class extends {@link JFrame} to provide a graphical user interface for the game.
 * It initializes the game panel, menu bar, and various menu options.
 */
public class GameWindow extends JFrame {
    /**
     * The game panel where the game logic and rendering occur.
     */
    private GamePanel gamePanel;

    /**
     * Constructs the main game window, sets up its layout, and initializes components such as
     * the game panel and menu bar.
     */
    public GameWindow() {
        setTitle("Yogi Bear Game"); // Sets the title of the game window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensures the application closes when the window is closed.
        setSize(1500, 1500); // Sets the dimensions of the window.
        setLocationRelativeTo(null); // Centers the window on the screen.
        setResizable(false); // Prevents resizing the window to maintain the layout.

        // Initializes the game panel and adds it to the window.
        gamePanel = new GamePanel();
        add(gamePanel);

        // Creates and configures the menu bar.
        JMenuBar menuBar = new JMenuBar();

        // Creates the "Game" menu and its options.
        JMenu gameMenu = new JMenu("Game");

        // Adds a "Restart Game" menu item to the "Game" menu.
        JMenuItem restartItem = new JMenuItem("Restart Game");
        restartItem.addActionListener(e -> gamePanel.restartGame()); // Restarts the game when clicked.
        gameMenu.add(restartItem);

        // Adds a "High Scores" menu item to the "Game" menu.
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        highScoresItem.addActionListener(e -> showHighScores()); // Displays high scores when clicked.
        gameMenu.add(highScoresItem);

        // Adds an "Exit" menu item to the "Game" menu.
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); // Exits the application when clicked.
        gameMenu.add(exitItem);

        // Adds the "Game" menu to the menu bar and sets the menu bar for the window.
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Displays the top 10 high scores in a dialog box.
     * Retrieves the high scores using the {@link HighScoreManager} class.
     */
    private void showHighScores() {
        String highScores = HighScoreManager.getHighScores(); // Retrieves high scores.
        JOptionPane.showMessageDialog(
            this, 
            "Top 10 High Scores:\n" + highScores, 
            "High Scores", 
            JOptionPane.INFORMATION_MESSAGE
        ); // Displays high scores in a pop-up dialog box.
    }
}
