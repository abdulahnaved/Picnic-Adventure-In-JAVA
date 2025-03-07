package my.company.my.yogibear;

import java.awt.*;

/**
 * Represents a tree obstacle in the game.
 * Trees are triangular-shaped obstacles that block the player's movement.
 */
public class Tree extends Obstacle {

    /**
     * Constructs a Tree obstacle with a specified position.
     *
     * @param x the x-coordinate of the tree's top-left corner.
     * @param y the y-coordinate of the tree's top-left corner.
     */
    public Tree(int x, int y) {
        super(x, y);
    }

    /**
     * Draws the tree obstacle (triangle) on the game panel.
     *
     * @param g the Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g, Image treeImage) {
        g.drawImage(treeImage, x, y, SIZE, SIZE, null);  // Use the tree image for rendering.
    }

}
