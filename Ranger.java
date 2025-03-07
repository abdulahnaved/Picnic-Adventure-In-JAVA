package my.company.my.yogibear;

import java.awt.*;
import java.util.Random;

/**
 * Represents a Ranger, a moving obstacle that can collide with the player's sprite.
 */
public class Ranger {
    /**
     * The x-coordinate of the ranger's position.
     */
    private int x;

    /**
     * The y-coordinate of the ranger's position.
     */
    private int y;

    /**
     * The horizontal movement speed of the ranger.
     */
    private int dx;

    /**
     * The vertical movement speed of the ranger.
     */
    private int dy;

    /**
     * The size of the ranger's bounding box.
     */
    private static final int SIZE = 30;

    /**
     * Random object to determine the ranger's initial direction.
     */
    private Random rand = new Random();

    /**
     * The speed of the ranger, which increases with the game level.
     */
    private int speed;

    /**
     * Constructs a Ranger at a specific position and assigns movement and speed based on the game level.
     *
     * @param x     the initial x-coordinate of the ranger.
     * @param y     the initial y-coordinate of the ranger.
     * @param level the current level of the game, used to determine the ranger's speed.
     */
    public Ranger(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.speed = 3 + (level - 1); // Base speed is 3, increases with level.

        // Randomly decide movement direction (horizontal or vertical).
        if (rand.nextBoolean()) {
            dx = 0; // Move vertically.
            dy = rand.nextBoolean() ? speed : -speed; // Randomly choose up or down.
        } else {
            dy = 0; // Move horizontally.
            dx = rand.nextBoolean() ? speed : -speed; // Randomly choose left or right.
        }
    }

    /**
     * Updates the ranger's position and reverses direction if it goes out of bounds.
     */
    public void move() {
        x += dx;
        y += dy;

        // Reverse direction if the ranger goes out of bounds.
        if (x < 0 || x > 750) {
            dx = -dx;
        }
        if (y < 0 || y > 550) {
            dy = -dy;
        }
    }

    /**
     * Checks if the ranger is near the player's sprite (Yogi) by detecting collisions.
     *
     * @param yogi the player's sprite.
     * @return {@code true} if the ranger's bounds intersect with Yogi's bounds, {@code false} otherwise.
     */
    public boolean isNear(Yogi yogi) {
        return new Rectangle(x, y, SIZE, SIZE).intersects(yogi.getBounds());
    }

    /**
     * Draws the ranger as a red square on the game panel.
     *
     * @param g the Graphics object used for rendering.
     */
    public void draw(Graphics g, Image rangerImage) {
        g.drawImage(rangerImage, x, y, null);
}

    /**
     * Provides the bounding rectangle of the ranger.
     * Used for collision detection with the player's sprite.
     *
     * @return a Rectangle representing the bounds of the ranger.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
}
