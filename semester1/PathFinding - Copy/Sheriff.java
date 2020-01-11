import java.util.LinkedList;
import java.awt.Color;

/**
 * This Tracker uses an A* Algorithm
 * which calculates a node's cost based on
 * how far away the node is from the goal
 * and how far the node is from the Tracker
 * position
 */
public class Sheriff extends Tracker {
    /**
     * Default constructor sets
     * color to orange and initializes
     * everything
     * @param _x spawn x
     * @param _y spawn y
     */
    public Sheriff(int _x, int _y) {
        x = _x;
        y = _y;
        color = Color.ORANGE.getRGB();
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
    public Sheriff(int _x, int _y, PixelGrid[] _sprites) {
        this(_x, _y);
        hasSprite = true;
        sprites = _sprites;
    }

    /**
     * This method calcualtes the cost
     * of a node by considering the
     * distance from the goal node and
     * the position of the Tracker
     */
    public void calculateCost(NodeGrid.Location node) {
        node.aCost = NodeGrid.distanceBetween(node, playerReference.node());
        node.bCost = 0;
        if (node.parent != null) node.bCost = node.parent.bCost + NodeGrid.distanceBetween(node, node.parent);
    }
}