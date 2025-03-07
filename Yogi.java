package my.company.my.yogibear;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Represents the player-controlled character (Yogi) in the game.
 */
public class Yogi {
    /**
     * Current x-coordinate of Yogi.
     */
    private int x;

    /**
     * Current y-coordinate of Yogi.
     */
    private int y;

    /**
     * Horizontal movement delta.
     */
    private int dx;

    /**
     * Vertical movement delta.
     */
    private int dy;

    /**
     * Speed of movement in pixels per frame.
     */
    private static final int SPEED = 5;

    /**
     * Size of Yogi's bounding box (width and height).
     */
    private static final int SIZE = 30;

    /**
     * Reference to the game panel, used for collision detection and boundaries.
     */
    private GamePanel gamePanel;

    /**
     * Constructs the Yogi character at a specified starting position.
     *
     * @param startX     the starting x-coordinate.
     * @param startY     the starting y-coordinate.
     * @param gamePanel  reference to the game panel for boundary and obstacle access.
     */
    public Yogi(int startX, int startY, GamePanel gamePanel) {
        this.x = startX;
        this.y = startY;
        this.gamePanel = gamePanel;
    }

    /**
     * Updates Yogi's position based on movement deltas while handling collisions and boundaries.
     */
    public void move() {
        int newX = x + dx;
        int newY = y + dy;

        // Check horizontal boundaries.
        if (newX >= 0 && newX <= gamePanel.getWidth() - SIZE) {
            x = newX;
        }

        // Check vertical boundaries.
        if (newY >= 0 && newY <= gamePanel.getHeight() - SIZE) {
            y = newY;
        }

        // Check for collisions with obstacles and adjust position if necessary.
        if (isColliding(x, newY)) {
            y = newY - dy;
        }
        if (isColliding(newX, y)) {
            x = newX - dx;
        }
    }

    /**
     * Checks if the specified position would collide with an obstacle.
     *
     * @param newX the potential new x-coordinate.
     * @param newY the potential new y-coordinate.
     * @return {@code true} if the position intersects with an obstacle, {@code false} otherwise.
     */
    private boolean isColliding(int newX, int newY) {
        for (Obstacle obstacle : gamePanel.getObstacles()) {
            if (new Rectangle(newX, newY, SIZE, SIZE).intersects(obstacle.getBounds())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Draws the Yogi using the provided image.
     *
     * @param g the Graphics object used for drawing.
     * @param image the image to draw for Yogi.
     */
    public void draw(Graphics g, Image image) {
        g.drawImage(image, x, y, 30, 30, gamePanel);
    }

    /**
     * Responds to key presses to update movement deltas.
     *
     * @param e the KeyEvent triggered by a key press.
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> dy = -SPEED; // Move up
            case KeyEvent.VK_LEFT -> dx = -SPEED; // Move left
            case KeyEvent.VK_DOWN -> dy = SPEED; // Move down
            case KeyEvent.VK_RIGHT -> dx = SPEED; // Move right
        }
    }

    /**
     * Responds to key releases to stop movement in the respective direction.
     *
     * @param e the KeyEvent triggered by a key release.
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> dy = 0; // Stop vertical movement
            case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> dx = 0; // Stop horizontal movement
        }
    }

    /**
     * Returns the bounding rectangle of Yogi for collision detection.
     *
     * @return a Rectangle representing Yogi's bounds.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    /**
     * Resets Yogi's position to the starting coordinates.
     */
    public void respawn() {
        x = 50;
        y = 50;
    }

    /**
     * Updates the x-coordinate of Yogi.
     *
     * @param x the new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Updates the y-coordinate of Yogi.
     *
     * @param y the new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }
    
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    
}
