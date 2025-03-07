package my.company.my.yogibear;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Represents the main game panel where the game logic and rendering occur.
 * Handles game objects, user input, level progression, and collision detection.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {
    /**
     * Timer for the game loop, running at a fixed frame rate.
     */
    private Timer gameTimer;

    /**
     * The total elapsed time in game ticks.
     */
    private int elapsedTime = 0;

    /**
     * The player-controlled character (Yogi).
     */
    private Yogi yogi;

    /**
     * List of obstacles (mountains and trees) in the game.
     */
    private ArrayList<Obstacle> obstacles;

    /**
     * List of picnic baskets that Yogi can collect.
     */
    private ArrayList<PicnicBasket> baskets;

    /**
     * List of rangers that move around the game area.
     */
    private ArrayList<Ranger> rangers;

    /**
     * Current game level.
     */
    private int level = 8;

    /**
     * Remaining lives for the player.
     */
    private int lives = 10;

    /**
     * The player's score.
     */
    private int score = 0;
    
    
    // Images
    private Image yogiImage;
    private Image mountainImage;
    private Image treeImage;
    private Image basketImage;
    private Image backgroundImage;
    private Image rangerImage;

    /**
     * Constructs a new GamePanel, initializing the game state and starting the game loop.
     */
    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        obstacles = new ArrayList<>();
        baskets = new ArrayList<>();
        rangers = new ArrayList<>();

        initializeGame();
        loadImages(); 
        gameTimer = new Timer(1000 / 60, this); // 60 FPS
        gameTimer.start();
    }

    /**
     * Initializes the game state and loads the first level.
     */
    private void initializeGame() {
        yogi = new Yogi(50, 50, this);
        loadLevel(level);
    }
    
    private void loadImages() {
    try {
        // Use the absolute path, and ensure the image names are correct
        yogiImage = ImageIO.read(new File("C:/Users/abdul/Desktop/Programming Technology/Assignment 3/YogiBear/images/yogi.png"));
        mountainImage = ImageIO.read(new File("C:/Users/abdul/Desktop/Programming Technology/Assignment 3/YogiBear/images/mountain.png"));
        treeImage = ImageIO.read(new File("C:/Users/abdul/Desktop/Programming Technology/Assignment 3/YogiBear/images/tree.png"));
        basketImage = ImageIO.read(new File("C:/Users/abdul/Desktop/Programming Technology/Assignment 3/YogiBear/images/basket.png"));
        backgroundImage = ImageIO.read(new File("C:/Users/abdul/Desktop/Programming Technology/Assignment 3/YogiBear/images/background.png"));
        rangerImage = ImageIO.read(new File("C:/Users/abdul/Desktop/Programming Technology/Assignment 3/YogiBear/images/ranger.png"));

        System.out.println("Images loaded successfully!");
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error loading images.");
    }
}

    
    

        /**
     * Loads a specific game level by generating obstacles, baskets, and rangers.
     *
     * @param level the level number to load.
     */
    private void loadLevel(int level) {
        obstacles.clear();
        baskets.clear();
        rangers.clear();

        Random rand = new Random();

        // Define a safe zone around Yogi's starting position to avoid conflicts.
        int safeZoneX = 50;
        int safeZoneY = 50;
        int safeZoneWidth = 100;
        int safeZoneHeight = 100;

        // Minimum distance from the edges for baskets and rangers.
        int edgeBuffer = 30;

        // Generate obstacles ensuring no overlap with each other or the safe zone.
        for (int i = 0; i < 10; i++) {
            int obstacleX, obstacleY;
            boolean isValidPosition;
            do {
                isValidPosition = true;
                obstacleX = rand.nextInt(750);
                obstacleY = rand.nextInt(550);

                Rectangle newObstacleBounds = new Rectangle(obstacleX, obstacleY, 30, 30);

                // Check if the new obstacle overlaps the safe zone or Yogi's starting position.
                if (newObstacleBounds.intersects(new Rectangle(safeZoneX, safeZoneY, safeZoneWidth, safeZoneHeight))) {
                    isValidPosition = false;
                    continue;
                }

                // Check for overlap with existing obstacles
                for (Obstacle existingObstacle : obstacles) {
                    if (newObstacleBounds.intersects(existingObstacle.getBounds())) {
                        isValidPosition = false;
                        break;
                    }
                }
            } while (!isValidPosition);

            Obstacle obstacle = rand.nextBoolean() ? new Mountain(obstacleX, obstacleY) : new Tree(obstacleX, obstacleY);
            obstacles.add(obstacle);
        }

        // Generate picnic baskets ensuring no overlap with obstacles or the safe zone.
        for (int i = 0; i < 10; i++) {
            int basketX, basketY;
            boolean validPosition;
            do {
                validPosition = true;
                basketX = rand.nextInt(750 - edgeBuffer * 2) + edgeBuffer;
                basketY = rand.nextInt(550 - edgeBuffer * 2) + edgeBuffer;

                Rectangle basketBounds = new Rectangle(basketX, basketY, 30, 30);

                // Ensure baskets don't overlap with obstacles or the safe zone.
                if (new Rectangle(safeZoneX, safeZoneY, safeZoneWidth, safeZoneHeight).intersects(basketBounds)) {
                    validPosition = false;
                }
                for (Obstacle obstacle : obstacles) {
                    if (basketBounds.intersects(obstacle.getBounds())) {
                        validPosition = false;
                        break;
                    }
                }
            } while (!validPosition);

            baskets.add(new PicnicBasket(basketX, basketY));
        }

        // Generate rangers ensuring no overlap with obstacles or the safe zone, and not at Yogi's position.
        for (int i = 0; i < 1 + (level - 1); i++) {
            int rangerX, rangerY;
            boolean validPosition;
            do {
                validPosition = true;
                rangerX = rand.nextInt(750 - edgeBuffer * 2) + edgeBuffer;
                rangerY = rand.nextInt(550 - edgeBuffer * 2) + edgeBuffer;

                Rectangle rangerBounds = new Rectangle(rangerX, rangerY, 30, 30);

                // Ensure rangers don't spawn in the safe zone or overlap with obstacles or Yogi.
                if (new Rectangle(safeZoneX, safeZoneY, safeZoneWidth, safeZoneHeight).intersects(rangerBounds) ||
                    new Rectangle(safeZoneX, safeZoneY, safeZoneWidth, safeZoneHeight).contains(rangerX, rangerY)) {
                    validPosition = false;
                }
                for (Obstacle obstacle : obstacles) {
                    if (rangerBounds.intersects(obstacle.getBounds())) {
                        validPosition = false;
                        break;
                    }
                }
            } while (!validPosition);

            rangers.add(new Ranger(rangerX, rangerY, level));
        }

        // Respawn Yogi at a safe starting position.
        spawnYogi();
    }



    /**
     * Provides access to the list of obstacles.
     *
     * @return the list of obstacles.
     */
    public ArrayList<Obstacle> getObstacles() {
        return this.obstacles;
    }

    /**
     * Places Yogi in a valid starting position, ensuring no obstacles overlap.
     */
    private void spawnYogi() {
        int startX = 50; // Starting position X
        int startY = 50; // Starting position Y

        Rectangle yogiBounds = new Rectangle(startX, startY, 30, 30); // Assuming Yogi's size is 30x30
        boolean isClear = true;

        // Ensure the starting position is not obstructed by any obstacle
        for (Obstacle obstacle : obstacles) {
            if (yogiBounds.intersects(obstacle.getBounds())) {
                isClear = false;
                break;
            }
        }

        if (isClear) {
            yogi.setX(startX);
            yogi.setY(startY);
        } else {
            // Fallback: Find the nearest valid position
            findSafePositionNear(startX, startY);
        }
    }

    /**
     * Finds a nearby safe position for Yogi to spawn if the starting position is obstructed.
     */
    private void findSafePositionNear(int startX, int startY) {
        Random rand = new Random();
        boolean isValid;
        int newX, newY;

        do {
            newX = startX + rand.nextInt(50) - 25; // Search within a small radius
            newY = startY + rand.nextInt(50) - 25;

            Rectangle yogiBounds = new Rectangle(newX, newY, 30, 30);
            isValid = true;

            // Ensure no intersection with obstacles
            for (Obstacle obstacle : obstacles) {
                if (yogiBounds.intersects(obstacle.getBounds())) {
                    isValid = false;
                    break;
                }
            }
        } while (!isValid);

        yogi.setX(newX);
        yogi.setY(newY);
    }



    /**
     * Handles updates for each game tick, including movement, collisions, and level progression.
     *
     * @param e the action event triggered by the game timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        yogi.move();

        for (Ranger ranger : rangers) {
            ranger.move();
            if (ranger.isNear(yogi)) {
                lives--;
                if (lives > 0) {
                    yogi.respawn();
                } else {
                    handleGameOver();
                }
                return;
            }
        }

        synchronized (baskets) {
            baskets.removeIf(basket -> basket.isCollected(yogi));
        }

        if (baskets.isEmpty()) {
            proceedToNextLevel();
        }

        elapsedTime++;
        repaint();
    }

    /**
     * Proceeds to the next level or ends the game if the player wins.
     */
    private void proceedToNextLevel() {
        gameTimer.stop();
        score += 10;
        level++;

        if (level > 10) {
            String playerName = JOptionPane.showInputDialog(this, "You won the game! Final Score: " + score + "\nEnter your name:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                HighScoreManager.saveHighScore(playerName, score);
            }

            String highScores = HighScoreManager.getHighScores();
            JOptionPane.showMessageDialog(this, "Top 10 High Scores:\n" + highScores);

            restartGame();
        } else {
            loadLevel(level);
        }

        gameTimer.start();
    }

    /**
     * Handles game over logic, saving scores and restarting the game.
     */
    private void handleGameOver() {
        String playerName = JOptionPane.showInputDialog(this, "Game Over! Your score: " + score + "\nEnter your name:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            HighScoreManager.saveHighScore(playerName, score);
        }

        String highScores = HighScoreManager.getHighScores();
        JOptionPane.showMessageDialog(this, "Top 10 High Scores:\n" + highScores);
        restartGame();
    }

    /**
     * Restarts the game to the first level.
     */
    public void restartGame() {
        level = 1;
        lives = 3;
        score = 0;
        elapsedTime = 0;
        initializeGame();
        gameTimer.restart();
    }

    /**
     * Renders the game components on the screen.
     *
     * @param g the Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        // Draw Yogi
        yogi.draw(g, yogiImage);

        // Draw obstacles (mountains and trees)
        for (Obstacle obstacle : obstacles) {
            if (obstacle instanceof Mountain) {
                obstacle.draw(g, mountainImage);
            } else if (obstacle instanceof Tree) {
                obstacle.draw(g, treeImage);
            }
        }

        // Draw picnic baskets
        for (PicnicBasket basket : baskets) {
            basket.draw(g, basketImage);
        }

        // Draw rangers
        for (Ranger ranger : rangers) {
            ranger.draw(g, rangerImage);
        }


        // Draw game information (score, lives, level, time)
        g.setColor(Color.BLACK);
        g.drawString("Lives: " + lives, 10, 20);
        g.drawString("Score: " + score, 10, 40);
        g.drawString("Level: " + level, 10, 60);
        g.drawString("Time: " + elapsedTime / 60, 10, 80);
    }


    /**
     * Handles key press events to control Yogi's movement.
     *
     * @param e the KeyEvent triggered by a key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        yogi.keyPressed(e);
    }

    /**
     * Handles key release events to stop Yogi's movement.
     *
     * @param e the KeyEvent triggered by a key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        yogi.keyReleased(e);
    }

    /**
     * KeyTyped event (not used but required for implementation).
     *
     * @param e the KeyEvent triggered by a key typing action.
     */
    @Override
    public void keyTyped(KeyEvent e) {}
}
