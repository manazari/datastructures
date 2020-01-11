import java.util.LinkedList;
import java.awt.Color;

/**
 * This Tracker uses a Greedy-Breadth First
 * Algorithm which will literally keep
 * moving straight towards the goal node
 * until hitting a closed node
 */
public class GreedyBum extends Tracker {
    /**
     * Default constructor sets
     * color to yellow and initializes
     * everything
     * @param _x spawn x
     * @param _y spawn y
     */
    public GreedyBum(int _x, int _y) {
        x = _x;
        y = _y;
        color = Color.YELLOW.getRGB();
        direction = Direction.STILL;
        path = new LinkedList<>();
        hasSprite = false;
        sprites = null;
    }

    /**
     * Sprite constructor for setting
     * the sprites after constructing 
     * using the default constructor
     * @param _x spawn x
     * @param _y spawn y
     * @param _sprites sprites of Tracker
     */
    public GreedyBum(int _x, int _y, PixelGrid[] _sprites) {
        this(_x, _y);
        hasSprite = true;
        sprites = _sprites;
    }

    /**
     * This method calcualtes the cost of
     * a node by considering only its
     * distance from the goal node
     */
    public void calculateCost(NodeGrid.Location node) {
        node.aCost = NodeGrid.distanceBetween(node, playerReference.node());
        node.bCost = 0;
    }
}