package my.company.my.yogibear;

import java.awt.*;

/**
 * Represents a mountain obstacle in the game.
 * Mountains are square-shaped obstacles that block the player's movement.
 */
public class Mountain extends Obstacle {

    /**
     * Constructs a Mountain obstacle with a specified position.
     *
     * @param x the x-coordinate of the mountain's top-left corner.
     * @param y the y-coordinate of the mountain's top-left corner.
     */
    public Mountain(int x, int y) {
        super(x, y);
    }

    /**
     * Draws the mountain obstacle (square) on the game panel.
     *
     * @param g the Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g, Image mountainImage) {
        g.drawImage(mountainImage, x, y, SIZE, SIZE, null);  // Use the mountain image for rendering.
    }
}
