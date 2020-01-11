import java.util.LinkedList;
import java.util.HashSet;

/**
 * Tracker extends Entity and its
 * role is to move along its path
 * which is a line of nodes in the
 * Game's NodeGrid which is found
 * by the Tracker's pathfinding
 * algorithm ever time it updates
 * and the player has moved
 */
public abstract class Tracker extends Entity {
    public LinkedList<NodeGrid.Location> path;
    protected Player playerReference;
    
    /**
     * Every type of tracker will follow
     * a basic blueprint pathfinding
     * algorithm and the customization
     * happens when it chooses which ndoe
     * to branch off on. This abstract
     * method decides the cost of each
     * node
     * @param node the node to calcualte the cost of
     */
    protected abstract void calculateCost(NodeGrid.Location node);
    
    /**
     * This pathfinding algorithm will
     * branch off the path of least cost until
     * there are no more open nodes to reach
     * or discover. Then it resets the grid
     */
    protected void executePathFindingAlgorithm() {
        if (playerReference == null) return;
        NodeGrid grid = game.map.nodes;

        grid.reset();
        HashSet<NodeGrid.Location> queue = new HashSet<>();
        queue.add(node());
        // PriorityQueue<NodeGrid.Location> queue = new PriorityQueue<>();
        // queue.add(node());
        
        while (!queue.isEmpty()) {     
            NodeGrid.Location bestNode = null;
            for (NodeGrid.Location node : queue)
                if (node.compareTo(bestNode) > 0) bestNode = node;

            if (playerReference.node() == bestNode) {
                path.clear();
                while (bestNode.parent != null) {
                    path.addFirst(bestNode);
                    bestNode = bestNode.parent;
                }
                break;
            }

            queue.remove(bestNode);
            grid.closed[bestNode.y][bestNode.x] = true;
            for (int i = 0; i < bestNode.neighbors.length; i++) {
                if (grid.closed[bestNode.neighbors[i].y][bestNode.neighbors[i].x]) continue;
                if (queue.contains(bestNode.neighbors[i])) continue;
                bestNode.neighbors[i].parent = bestNode;
                calculateCost(bestNode.neighbors[i]);
                queue.add(bestNode.neighbors[i]);
            }
        }
        grid.reset();
    }

    /**
     * The update function will only
     * find a new path to follow only if
     * the player has moved. Then it will
     * move either left and right
     * if it can
     */
    public void update() {
        if (playerReference.hasMoved) executePathFindingAlgorithm();
        if (path.size() == 0) return;

        NodeGrid.Location nextNode = path.getFirst();

        if (y > nextNode.y) {
            direction = Direction.UP;
            move();
        } else if (y < nextNode.y) {
            direction = Direction.DOWN;
            move();
        }

        if (x > nextNode.x) {
            direction = Direction.LEFT;
            move();
        } else if (x < nextNode.x) {
            direction = Direction.RIGHT;
            move();
        }

        if (x == nextNode.x && y == nextNode.y) path.removeFirst();
    }

    /**
     * An additional check to look
     * for is whether the Tracker will collide
     * with another Tracker if they both have
     * sprites
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        for (Tracker tracker : game.getTrackers()) {
            if (this == tracker) continue;
            if (tracker.hasSprite && this.hasSprite
                && x + sprites[0].WIDTH  > tracker.x && x < tracker.x + tracker.sprites[0].WIDTH
                && y + sprites[0].HEIGHT > tracker.y && y < tracker.y + tracker.sprites[0].HEIGHT) return false;
        }
        return super.canMoveTo(x, y);
    }

    /**
     * After assigning to the game
     * the Tracker will then grab the reference
     * to the player of the game and the execute
     * its pathfinding algorithm
     */
    @Override
    public void assignToGame(Game _game) {
        game = _game;
        playerReference = _game.player;
        executePathFindingAlgorithm();
    }
}