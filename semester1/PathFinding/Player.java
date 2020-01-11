import java.awt.Color;

/**
 * This class extends Entity
 * and defines its update function
 * by changing the direction to
 * whatever key is held and then
 * moving
 */
public class Player extends Entity {
    public boolean hasMoved;

    /**
     * Constructor without a sprite
     * will set the default color
     * of a player to CYAN
     * @param _x starting x coord
     * @param _y starting y coord
     */
    public Player(int _x, int _y) {
        x = _x;
        y = _y;
        color = Color.CYAN.getRGB();
        direction = Direction.STILL;
        hasMoved = true;
        hasSprite = false;
        sprites = null;
    }

    /**
     * Constructor with a sprite
     * will first create the default
     * constructor then set hasSprite
     * to true and set the sprites
     * to a PixelGrid array
     * @param _x
     * @param _y
     * @param _sprites
     */
    public Player(int _x, int _y, PixelGrid[] _sprites) {
        this(_x, _y);
        hasSprite = true;
        sprites = _sprites;
    }

    /**
     * Overrides the move method to
     * move twice to simulate a faster
     * speed.
     */
    @Override
    public void move() {
        super.move();
        super.move();
        if (direction == Direction.STILL) hasMoved = false;
        else hasMoved = true;
    }

    /**
     * Update function will move
     * after setting a new direction
     * based off of what keys are held
     */
    public void update() {
        if (Boolean.TRUE.equals(game.keysHeld.get(Direction.UP.getKeyCode()))) direction = Direction.UP;
        else if (Boolean.TRUE.equals(game.keysHeld.get(Direction.DOWN.getKeyCode()))) direction = Direction.DOWN;
        else if (Boolean.TRUE.equals(game.keysHeld.get(Direction.LEFT.getKeyCode()))) direction = Direction.LEFT;
        else if (Boolean.TRUE.equals(game.keysHeld.get(Direction.RIGHT.getKeyCode()))) direction = Direction.RIGHT;
        else direction = Direction.STILL;
        move();
    }
}