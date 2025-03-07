package my.company.my.yogibear;

import java.awt.*;

/**
 * Represents a collectible picnic basket in the game.
 * The player must collect all baskets to complete a level.
 */
public class PicnicBasket {
    /**
     * The x-coordinate of the top-left corner of the basket's bounding box.
     */
    private int x;

    /**
     * The y-coordinate of the top-left corner of the basket's bounding box.
     */
    private int y;

    /**
     * The diameter (and width/height of the bounding box) of the basket.
     */
    private static final int SIZE = 30;

    /**
     * Constructs a PicnicBasket with a specified position.
     *
     * @param x the x-coordinate of the basket's top-left corner.
     * @param y the y-coordinate of the basket's top-left corner.
     */
    public PicnicBasket(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Provides the bounding rectangle of the picnic basket.
     * Used for collision detection with the player's sprite.
     *
     * @return a Rectangle representing the bounds of the basket.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    /**
     * Checks if the basket has been collected by the player's sprite.
     *
     * @param yogi the player's sprite represented as a {@link Yogi} object.
     * @return {@code true} if the basket intersects with the player's sprite, {@code false} otherwise.
     */
    public boolean isCollected(Yogi yogi) {
        return getBounds().intersects(yogi.getBounds());
    }

    /**
     * Draws the picnic basket using the provided image.
     *
     * @param g the Graphics object used for rendering.
     * @param basketImage the image of the picnic basket.
     */
    public void draw(Graphics g, Image basketImage) {
        g.drawImage(basketImage, x, y, 30, 30, null);
    }
}
