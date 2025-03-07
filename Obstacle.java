package my.company.my.yogibear;

import java.awt.*;

/**
 * Represents a stationary obstacle in the game.
 * Obstacles block the player's movement and serve as challenges
 * in the game environment.
 */
public abstract class Obstacle {
    /**
     * The x-coordinate of the obstacle's top-left corner.
     */
    protected int x;

    /**
     * The y-coordinate of the obstacle's top-left corner.
     */
    protected int y;

    /**
     * The size (width and height) of the obstacle in pixels.
     * Since obstacles are square, both dimensions are equal.
     */
    protected static final int SIZE = 40;

    /**
     * Constructs an Obstacle with a specified position.
     *
     * @param x the x-coordinate of the obstacle's top-left corner.
     * @param y the y-coordinate of the obstacle's top-left corner.
     */
    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the obstacle using the appropriate image.
     *
     * @param g the Graphics object used for drawing.
     */
    public abstract void draw(Graphics g, Image image);


    /**
     * Provides the bounding rectangle of the obstacle.
     * This is used for collision detection with other game elements.
     *
     * @return a Rectangle representing the bounds of the obstacle.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
}
