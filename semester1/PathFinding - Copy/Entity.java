import java.awt.event.KeyEvent;
import java.awt.Color;
/**
 * This class holds information about
 * an object that a Game can update
 * and paint to the frame periodically.
 * The default behavior of an entity is
 * that is can move according to its
 * direction and be painted. Every entity
 * will update in its own way so it needs
 * an abstract update class in order to
 * function
 */
public abstract class Entity {
    public int x;
    public int y;
    public Direction direction;
    public int color;
    public boolean hasSprite;
    public PixelGrid[] sprites;
    protected Game game;
    protected final int TRANSPARENCY_COLOR = new Color(184, 252, 184).getRGB();

    /**
     * This enum is just a verbose
     * way of storing information
     * about what direction the entity
     * moves in. It is unnecessary
     * and will prob be removed for later
     * revisions
     */
    protected enum Direction {
        UP(KeyEvent.VK_W),
        DOWN(KeyEvent.VK_S),
        LEFT(KeyEvent.VK_A),
        RIGHT(KeyEvent.VK_D),
        STILL(-1);

        private int keyCode;

        private Direction(int _keyCode) {
            keyCode = _keyCode;
        }

        public int getKeyCode() {
            return keyCode;
        }
    }

    /**
     * This method can be called by
     * a Game and is abstract as every
     * entity will update in a way
     * specific to what type of entity
     * it is
     */
    public abstract void update();

    /**
     * This method is a setter for the
     * game field
     * @param _game
     */
    public void assignToGame(Game _game) {
        game = _game;
    }

    /**
     * The default paint function for
     * entities will paint a single cell
     * at location or the sprite of the
     * entity with the top left corner
     * at the entity coordinate. Sprite
     * will change depending on the frame
     * count of the Game
     */
    public void paint() {
        if (hasSprite) {
            PixelGrid sprite = sprites[game.frame % sprites.length];
            for (int spriteX = 0; spriteX < sprite.WIDTH; spriteX++) {
                for (int spriteY = 0; spriteY < sprite.HEIGHT; spriteY++) {
                    int screenX = x + spriteX;
                    int screenY = y + spriteY;

                    if (sprite.color[spriteY][spriteX] == TRANSPARENCY_COLOR) continue;
                    game.window.fillPixel(screenX, screenY, sprite.color[spriteY][spriteX]);
                }
            }
        }
        else game.window.fillPixel(x, y, color);
    }

    /**
     * Gets the node from the Game's NodeGrid
     * which lies at the coordinate
     * of the entity
     * @return NodeGrid.Location node equivalent
     */
    public NodeGrid.Location node() {
        return game.map.nodes.location[y][x];
    }

    public boolean canMoveTo(int x, int y) {
        if (!hasSprite) {
            if (x < 0 || x >= game.map.nodes.WIDTH) return false;
            if (y < 0 || y >= game.map.nodes.HEIGHT) return false;
            if (!game.map.nodes.walkable[y][x]) return false;
        } else {
            if (x < 0 || x + sprites[0].WIDTH >= game.map.nodes.WIDTH) return false;
            if (y < 0 || y + sprites[0].HEIGHT >= game.map.nodes.HEIGHT) return false;
            if (!game.map.nodes.walkable[y][x]) return false;
            // for (int spriteX = 0; spriteX < sprites[0].WIDTH; spriteX++) {
            //     for (int spriteY = 0; spriteY < sprites[0].HEIGHT; spriteY++) {
            //         if (!game.map.nodes.walkable[y + spriteY][x + spriteX]) return false;
            //     }
            // }
        }
        return true;
    }

    /**
     * Moves in the direction of 
     * the direction field
     */
    public void move() {
        switch (direction) {
            case UP:
                if (canMoveTo(x, y - 1)) y--;
                break;

            case DOWN:
                if (canMoveTo(x, y + 1)) y++;
                break;

            case LEFT:
                if (canMoveTo(x - 1, y)) x--;
                break;

            case RIGHT:
                if (canMoveTo(x + 1, y)) x++;
                break;

            case STILL:
                // Do nothing, maybe boogy a lil
                break;
        }
    }
}